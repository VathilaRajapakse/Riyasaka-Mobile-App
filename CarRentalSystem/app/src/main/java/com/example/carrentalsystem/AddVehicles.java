package com.example.carrentalsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.carrentalsystem.Models.VehiclesDetails;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class AddVehicles extends AppCompatActivity {

    EditText editText1;
    EditText editText2;
    EditText editText3;
    EditText editText4;
    EditText editText5;
    EditText editText6;
    EditText editText7;
    ImageView imageView;
    Button button;
    DatabaseReference ref;
    String Storage_Path = "All_Image_Uploads/";
    Uri FilePathUri;
    StorageReference storageReference;
    int Image_Request_Code = 7;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicles);

        imageView = findViewById(R.id.imageView);
        editText1 = findViewById(R.id.number);
        editText2 = findViewById(R.id.model);
        editText3 = findViewById(R.id.price);
        editText4 = findViewById(R.id.brand);
        editText5 = findViewById(R.id.passenger);
        editText6 = findViewById(R.id.category);
        editText7 = findViewById(R.id.driver);
        button = findViewById(R.id.addbtn);

        ref = FirebaseDatabase.getInstance().getReference("Vehicles");
        storageReference = FirebaseStorage.getInstance().getReference();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Creating intent.
                Intent intent = new Intent();

                // Setting intent type as image to select image from phone storage.
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Please Select Image"), Image_Request_Code);


            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UploadImageFileToFirebaseStorage();

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Image_Request_Code && resultCode == RESULT_OK && data != null && data.getData() != null) {

            FilePathUri = data.getData();

            try {

                // Getting selected image into Bitmap.
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), FilePathUri);

                // Setting up bitmap selected image into ImageView.
                imageView.setImageBitmap(bitmap);


            }
            catch (IOException e) {

                e.printStackTrace();
            }
        }
    }

    // Creating Method to get the selected image file Extension from File Path URI.
    public String GetFileExtension(Uri uri) {

        ContentResolver contentResolver = getContentResolver();

        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();

        // Returning the file Extension.
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri)) ;

    }

    // Creating UploadImageFileToFirebaseStorage method to upload image on storage.
    public void UploadImageFileToFirebaseStorage() {

        // Checking whether FilePathUri Is empty or not.
        if (FilePathUri != null) {

            // Creating second StorageReference.
            StorageReference storageReference2nd = storageReference.child(Storage_Path + System.currentTimeMillis() + "." + GetFileExtension(FilePathUri));

            // Adding addOnSuccessListener to second StorageReference.
            storageReference2nd.putFile(FilePathUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            taskSnapshot.getStorage().getDownloadUrl().addOnCompleteListener(
                                    new OnCompleteListener<Uri>() {

                                        @Override
                                        public void onComplete(@NonNull Task<Uri> task) {
                                            String fileLink = task.getResult().toString();
                                            //next work with URL

                                            System.out.println(fileLink);

                                            final String number = editText1.getText().toString();
                                            final String model = editText2.getText().toString();
                                            final String price = editText3.getText().toString();
                                            final String brand = editText4.getText().toString();
                                            final String passenger = editText5.getText().toString();
                                            final String category = editText6.getText().toString();
                                            final String driver = editText7.getText().toString();

                                            if (number.isEmpty()) {
                                                editText1.setError("Vehicle number is required");
                                            }else if (model.isEmpty()) {
                                                editText2.setError("Vehicle model is required");
                                            }else if (price.isEmpty()) {
                                                editText3.setError("Vehicle Price Number is required");
                                            }else if (brand.isEmpty()) {
                                                editText4.setError("Vehicle Brand is required");
                                            }else if (passenger.isEmpty()) {
                                                editText5.setError("Passenger is required");
                                            }else if (category.isEmpty()) {
                                                editText6.setError("Vehicle Category is required");
                                            }else if (driver.isEmpty()) {
                                                editText7.setError("Vehicle Driver is required");
                                            }else {

                                                // Getting image upload ID.
                                                String key = ref.push().getKey();

                                                System.out.println();

                                                @SuppressWarnings("VisibleForTests")
                                                VehiclesDetails vehicles = new VehiclesDetails(key, fileLink, number, model, price, brand, passenger, category, driver);

                                                // Adding image upload id s child element into databaseReference.
                                                ref.child(key).setValue(vehicles);

                                                // Showing toast message after done uploading.
                                                Toast.makeText(getApplicationContext(), "Data Uploaded Successfully ", Toast.LENGTH_LONG).show();

                                            }

                                        }
                                    });


                        }
                    })
                    // If something goes wrong .
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception exception) {

                            // Showing exception erro message.
                            Toast.makeText(AddVehicles.this, exception.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    })

                    // On progress change upload time.
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {

                        }
                    });
        }
        else {

            Toast.makeText(AddVehicles.this, "Failed", Toast.LENGTH_LONG).show();

        }
    }
}