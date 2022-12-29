package com.example.carrentalsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class UpdateVehicleDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_vehicle_details);

        EditText editText1 = (EditText) findViewById(R.id.unumber);
        EditText editText2 = (EditText) findViewById(R.id.umodel);
        EditText editText3 = (EditText) findViewById(R.id.uprice);
        EditText editText4 = (EditText) findViewById(R.id.ubrand);
        EditText editText5 = (EditText) findViewById(R.id.upassenger);
        EditText editText6 = (EditText) findViewById(R.id.ucategory);
        EditText editText7 = (EditText) findViewById(R.id.udriver);
        Button buttonupdate = (Button) findViewById(R.id.updatebtn);
        ImageView imageView = (ImageView) findViewById(R.id.uimageView3);

        String idd =getIntent().getStringExtra("id");

        System.out.println(idd);
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Vehicles").child(idd);
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String image = (String) snapshot.child("image").getValue();
                String VehicleNo = (String) snapshot.child("vehicleNo").getValue();
                String Model = (String) snapshot.child("model").getValue();
                String Price = (String) snapshot.child("price").getValue();
                String Brand = (String) snapshot.child("brand").getValue();
                String Passenger = (String) snapshot.child("passenger").getValue();
                String Category = (String) snapshot.child("category").getValue();
                String Driver = (String) snapshot.child("driver").getValue();

                editText1.setText(VehicleNo);
                editText2.setText(Model);
                editText3.setText(Price);
                editText4.setText(Brand);
                editText5.setText(Passenger);
                editText6.setText(Category);
                editText7.setText(Driver);
                Picasso.get().load(image).into(imageView);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        buttonupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

                    HashMap map = new HashMap();
                    map.put("vehicleNo", number);
                    map.put("model", model);
                    map.put("price", price);
                    map.put("brand", brand);
                    map.put("passenger", passenger);
                    map.put("category", category);
                    map.put("driver", driver);
                    reference.updateChildren(map);

                    Toast.makeText(UpdateVehicleDetails.this, "Updated successfully", Toast.LENGTH_SHORT).show();

                }
            }
        });

    }
}