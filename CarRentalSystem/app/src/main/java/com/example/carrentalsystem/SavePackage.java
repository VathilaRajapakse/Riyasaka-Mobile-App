package com.example.carrentalsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carrentalsystem.Models.PackageDetails;
import com.example.carrentalsystem.Models.VehiclesDetails;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class SavePackage extends AppCompatActivity {

    EditText editText1;
    EditText editText2;
    EditText editText3;
    EditText editText4;
    TextView textView1;
    TextView textView2;
    TextView textView3;
    Button button;
    DatabaseReference ref;
    ImageView imageView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_package);

        textView1 = findViewById(R.id.number);
        textView2 = findViewById(R.id.model);
        textView3 = findViewById(R.id.brand);
        editText1 = findViewById(R.id.name);
        editText2 = findViewById(R.id.destination);
        editText3 = findViewById(R.id.days);
        editText4 = findViewById(R.id.price);
        button = findViewById(R.id.addbtn);
        imageView = findViewById(R.id.imageView2);

        ref = FirebaseDatabase.getInstance().getReference("Packages");

        String number =getIntent().getStringExtra("number");
        String model =getIntent().getStringExtra("model");
        String brand =getIntent().getStringExtra("brand");
        String image =getIntent().getStringExtra("image");

        textView1.setText(number);
        textView2.setText(model);
        textView3.setText(brand);
        Picasso.get().load(image).into(imageView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = editText1.getText().toString();
                String destination = editText2.getText().toString();
                String days = editText3.getText().toString();
                String price = editText4.getText().toString();

                if(name.isEmpty()){
                    editText1.setError("Package name is required");
                }else if(destination.isEmpty()){
                    editText2.setError("Destination name is required");
                }else if(days.isEmpty()){
                    editText3.setError("Number of days is required");
                }else if(price.isEmpty()){
                    editText4.setError("Package price is required");
                }else {
                    // Getting image upload ID.
                    String key = ref.push().getKey();

                    System.out.println();

                    @SuppressWarnings("VisibleForTests")
                    PackageDetails packageDetails = new PackageDetails(key,image,number,model,brand,name,destination,days,price);

                    // Adding image upload id s child element into databaseReference.
                    ref.child(key).setValue(packageDetails);

                    // Showing toast message after done uploading.
                    Toast.makeText(getApplicationContext(), "Data Uploaded Successfully ", Toast.LENGTH_LONG).show();

                }

            }
        });
    }
}