package com.example.carrentalsystem;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.carrentalsystem.Models.DriverDetails;
import com.example.carrentalsystem.Models.PackageDetails;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddDrivers extends AppCompatActivity {

    EditText editText1;
    EditText editText2;
    EditText editText3;
    EditText editText4;
    EditText editText5;
    Button button;
    DatabaseReference ref;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_drivers);

        editText1 = findViewById(R.id.name);
        editText2 = findViewById(R.id.nic);
        editText3 = findViewById(R.id.licen);
        editText4 = findViewById(R.id.contact);
        editText5 = findViewById(R.id.email);
        button = findViewById(R.id.addbtn);

        ref = FirebaseDatabase.getInstance().getReference("Drivers");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = editText1.getText().toString();
                String nic = editText2.getText().toString();
                String licen = editText3.getText().toString();
                String contact = editText4.getText().toString();
                String email = editText5.getText().toString();

                if(name.isEmpty()){
                    editText1.setError("Driver name is required");
                }else if(nic.isEmpty()){
                    editText2.setError("Driver NIC Number is required");
                }else if(licen.isEmpty()){
                    editText3.setError("Driver licen number is required");
                }else if(contact.isEmpty()){
                    editText4.setError("Contact number is required");
                }else if(email.isEmpty()){
                    editText5.setError("email is required");
                }else {
                    // Getting image upload ID.
                    String key = ref.push().getKey();

                    System.out.println();

                    @SuppressWarnings("VisibleForTests")
                    DriverDetails driverDetails = new DriverDetails(key,name,nic,licen,contact,email);

                    // Adding image upload id s child element into databaseReference.
                    ref.child(key).setValue(driverDetails);

                    // Showing toast message after done uploading.
                    Toast.makeText(getApplicationContext(), "Data Uploaded Successfully ", Toast.LENGTH_LONG).show();

                }
            }
        });
    }
}