package com.example.carrentalsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carrentalsystem.Models.PackageDetails;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BookVehicle extends AppCompatActivity {

    ListView listView;
    List<PackageDetails> user;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_vehicle);

        listView = (ListView)findViewById(R.id.listview);

        user = new ArrayList<>();

        ref = FirebaseDatabase.getInstance().getReference("Packages");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user.clear();

                for (DataSnapshot studentDatasnap : dataSnapshot.getChildren()) {

                    PackageDetails packageDetails = studentDatasnap.getValue(PackageDetails.class);
                    user.add(packageDetails);
                }

                MyAdapter adapter = new MyAdapter(BookVehicle.this, R.layout.custom_book_vehicle_details, (ArrayList<PackageDetails>) user);
                listView.setAdapter(adapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    static class ViewHolder {

        TextView COL1;
        TextView COL2;
        TextView COL3;
        TextView COL4;
        Button button1;
        ImageView imageView;
    }

    class MyAdapter extends ArrayAdapter<PackageDetails> {
        LayoutInflater inflater;
        Context myContext;
        List<PackageDetails> user;


        public MyAdapter(Context context, int resource, ArrayList<PackageDetails> objects) {
            super(context, resource, objects);
            myContext = context;
            user = objects;
            inflater = LayoutInflater.from(context);
            int y;
            String barcode;
        }

        @SuppressLint("SetTextI18n")
        @Override
        public View getView(int position, View view, ViewGroup parent) {
            final ViewHolder holder;
            if (view == null) {
                holder = new ViewHolder();
                view = inflater.inflate(R.layout.custom_book_vehicle_details, null);

                holder.COL1 = (TextView) view.findViewById(R.id.name);
                holder.COL2 = (TextView) view.findViewById(R.id.destination);
                holder.COL3 = (TextView) view.findViewById(R.id.days);
                holder.COL4 = (TextView) view.findViewById(R.id.price);
                holder.button1 = (Button) view.findViewById(R.id.book);
                holder.imageView = (ImageView) view.findViewById(R.id.imageView2);


                view.setTag(holder);
            } else {

                holder = (ViewHolder) view.getTag();
            }

            holder.COL1.setText("Package name:- "+user.get(position).getName());
            holder.COL2.setText("Package destination:- "+user.get(position).getDestination());
            holder.COL3.setText("Package days:- "+user.get(position).getDays());
            holder.COL4.setText("Package price:- "+user.get(position).getPrice());
            Picasso.get().load(user.get(position).getImage()).into(holder.imageView);
            System.out.println(holder);


            holder.button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i = new Intent(BookVehicle.this,SaveBooking.class);
                    i.putExtra("id",user.get(position).getId());
                    i.putExtra("number",user.get(position).getNumber());
                    i.putExtra("image",user.get(position).getImage());
                    i.putExtra("price",user.get(position).getPrice());
                    startActivity(i);
                }
            });


            return view;

        }
    }
}