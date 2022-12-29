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
import com.example.carrentalsystem.Models.VehiclesDetails;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Packages extends AppCompatActivity {

    ListView listView;
    List<PackageDetails> user;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_packages);

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

                MyAdapter adapter = new MyAdapter(Packages.this, R.layout.custom_package_details, (ArrayList<PackageDetails>) user);
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
        Button button2;
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
                view = inflater.inflate(R.layout.custom_package_details, null);

                holder.COL1 = (TextView) view.findViewById(R.id.name);
                holder.COL2 = (TextView) view.findViewById(R.id.destination);
                holder.COL3 = (TextView) view.findViewById(R.id.days);
                holder.COL4 = (TextView) view.findViewById(R.id.price);
                holder.button1 = (Button) view.findViewById(R.id.edit);
                holder.button2 = (Button) view.findViewById(R.id.delete);
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

            holder.button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                            .setTitle("Do you want to delete this item?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    final String idd = user.get(position).getId();
                                    FirebaseDatabase.getInstance().getReference("Packages").child(idd).removeValue();
                                    //remove function not written
                                    Toast.makeText(myContext, "Item deleted successfully", Toast.LENGTH_SHORT).show();

                                }
                            })

                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    dialogInterface.cancel();
                                }
                            })
                            .show();
                }
            });

            holder.button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
                    View view1 = inflater.inflate(R.layout.custom_update_package_details, null);
                    dialogBuilder.setView(view1);

                    @SuppressLint({"MissingInflatedId", "LocalSuppress"})
                    final EditText editText1 = (EditText) view1.findViewById(R.id.name);
                    final EditText editText2 = (EditText) view1.findViewById(R.id.destination);
                    final EditText editText3 = (EditText) view1.findViewById(R.id.days);
                    final EditText editText4 = (EditText) view1.findViewById(R.id.price);
                    final Button buttonupdate = (Button) view1.findViewById(R.id.update);

                    final AlertDialog alertDialog = dialogBuilder.create();
                    alertDialog.show();

                    final String idd = user.get(position).getId();
                    final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Packages").child(idd);
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String name = (String) snapshot.child("name").getValue();
                            String destination = (String) snapshot.child("destination").getValue();
                            String days = (String) snapshot.child("days").getValue();
                            String price = (String) snapshot.child("price").getValue();

                            editText1.setText(name);
                            editText2.setText(destination);
                            editText3.setText(days);
                            editText4.setText(price);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                    buttonupdate.setOnClickListener(new View.OnClickListener() {
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
                                editText2.setError("Number of days is required");
                            }else if(price.isEmpty()){
                                editText2.setError("Package price is required");
                            }else {

                                HashMap map = new HashMap();
                                map.put("name", name);
                                map.put("destination", destination);
                                map.put("days", days);
                                map.put("price", price);
                                reference.updateChildren(map);

                                Toast.makeText(Packages.this, "Updated successfully", Toast.LENGTH_SHORT).show();

                                alertDialog.dismiss();
                            }
                        }
                    });
                }
            });


            return view;

        }
    }
}