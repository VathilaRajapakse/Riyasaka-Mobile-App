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

import com.example.carrentalsystem.Models.BookingDetails;
import com.example.carrentalsystem.Models.PackageDetails;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class SaveBooking extends AppCompatActivity {

    EditText editText1;
    EditText editText2;
    EditText editText3;
    EditText editText4;
    EditText editText5;
    TextView textView1;
    TextView textView2;
    Button button;
    DatabaseReference ref;
    ImageView imageView;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_booking);

        textView1 = findViewById(R.id.number);
        textView2 = findViewById(R.id.price);
        editText1 = findViewById(R.id.rentaldays);
        editText2 = findViewById(R.id.rentdate);
        editText3 = findViewById(R.id.handover);
        editText4 = findViewById(R.id.customername);
        editText5 = findViewById(R.id.customercontact);
        button = findViewById(R.id.addbtn);
        imageView = findViewById(R.id.imageView2);

        ref = FirebaseDatabase.getInstance().getReference("Bookings");

        String number =getIntent().getStringExtra("number");
        String price =getIntent().getStringExtra("price");
        String image =getIntent().getStringExtra("image");

        textView1.setText(number);
        textView2.setText(price);
        Picasso.get().load(image).into(imageView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String rentaldays = editText1.getText().toString();
                String rentdate = editText2.getText().toString();
                String handover = editText3.getText().toString();
                String customername = editText4.getText().toString();
                String customercontact = editText5.getText().toString();

                if(rentaldays.isEmpty()){
                    editText1.setError("Rental days is required");
                }else if(rentdate.isEmpty()){
                    editText2.setError("Rent date is required");
                }else if(handover.isEmpty()){
                    editText3.setError("Hand over date is required");
                }else if(customername.isEmpty()){
                    editText4.setError("Customer name is required");
                }else if(customercontact.isEmpty()){
                    editText5.setError("Customer contact is required");
                }else {
                    // Getting image upload ID.
                    String key = ref.push().getKey();

                    System.out.println();

                    @SuppressWarnings("VisibleForTests")
                    BookingDetails bookingDetails = new BookingDetails(key,image,number,price,rentaldays,rentdate,handover,customername,customercontact);

                    // Adding image upload id s child element into databaseReference.
                    ref.child(key).setValue(bookingDetails);

                    // Showing toast message after done uploading.
                    Toast.makeText(getApplicationContext(), "Data Uploaded Successfully ", Toast.LENGTH_LONG).show();

                }

            }
        });
    }
}