package com.example.carrentalsystem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carrentalsystem.Models.DriverDetails;
import com.example.carrentalsystem.Models.PackageDetails;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Drivers extends AppCompatActivity {

    ListView listView;
    List<DriverDetails> user;
    DatabaseReference ref;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drivers);

        listView = (ListView)findViewById(R.id.listview);

        user = new ArrayList<>();

        ref = FirebaseDatabase.getInstance().getReference("Drivers");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user.clear();

                for (DataSnapshot studentDatasnap : dataSnapshot.getChildren()) {

                    DriverDetails driverDetails = studentDatasnap.getValue(DriverDetails.class);
                    user.add(driverDetails);
                }

                MyAdapter adapter = new MyAdapter(Drivers.this, R.layout.custom_driver_details, (ArrayList<DriverDetails>) user);
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
        TextView COL5;
        Button button1;
        Button button2;
    }

    class MyAdapter extends ArrayAdapter<DriverDetails> {
        LayoutInflater inflater;
        Context myContext;
        List<DriverDetails> user;


        public MyAdapter(Context context, int resource, ArrayList<DriverDetails> objects) {
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
                view = inflater.inflate(R.layout.custom_driver_details, null);

                holder.COL1 = (TextView) view.findViewById(R.id.name);
                holder.COL2 = (TextView) view.findViewById(R.id.nic);
                holder.COL3 = (TextView) view.findViewById(R.id.licen);
                holder.COL4 = (TextView) view.findViewById(R.id.contact);
                holder.COL5 = (TextView) view.findViewById(R.id.email);
                holder.button1 = (Button) view.findViewById(R.id.edit);
                holder.button2 = (Button) view.findViewById(R.id.delete);


                view.setTag(holder);
            } else {

                holder = (ViewHolder) view.getTag();
            }

            holder.COL1.setText("Driver name:- "+user.get(position).getName());
            holder.COL2.setText("Driver NIC:- "+user.get(position).getNic());
            holder.COL3.setText("Driver Licen:- "+user.get(position).getLicen());
            holder.COL4.setText("Driver Contact:- "+user.get(position).getContact());
            holder.COL5.setText("Driver Email:- "+user.get(position).getEmail());
            System.out.println(holder);

            holder.button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    final AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                            .setTitle("Do you want to delete this Driver?")
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                    final String idd = user.get(position).getId();
                                    FirebaseDatabase.getInstance().getReference("Drivers").child(idd).removeValue();
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
                    View view1 = inflater.inflate(R.layout.custom_update_driver, null);
                    dialogBuilder.setView(view1);

                    @SuppressLint({"MissingInflatedId", "LocalSuppress"})
                    final EditText editText1 = (EditText) view1.findViewById(R.id.name);
                    final EditText editText2 = (EditText) view1.findViewById(R.id.nic);
                    final EditText editText3 = (EditText) view1.findViewById(R.id.licen);
                    final EditText editText4 = (EditText) view1.findViewById(R.id.contact);
                    final EditText editText5 = (EditText) view1.findViewById(R.id.email);
                    final Button buttonupdate = (Button) view1.findViewById(R.id.updatebtn);

                    final AlertDialog alertDialog = dialogBuilder.create();
                    alertDialog.show();

                    final String idd = user.get(position).getId();
                    final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Drivers").child(idd);
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String name = (String) snapshot.child("name").getValue();
                            String nic = (String) snapshot.child("nic").getValue();
                            String licen = (String) snapshot.child("licen").getValue();
                            String contact = (String) snapshot.child("contact").getValue();
                            String email = (String) snapshot.child("email").getValue();

                            editText1.setText(name);
                            editText2.setText(nic);
                            editText3.setText(licen);
                            editText4.setText(contact);
                            editText5.setText(email);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


                    buttonupdate.setOnClickListener(new View.OnClickListener() {
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

                                HashMap map = new HashMap();
                                map.put("name", name);
                                map.put("nic", nic);
                                map.put("licen", licen);
                                map.put("contact", contact);
                                map.put("email", email);
                                reference.updateChildren(map);

                                Toast.makeText(Drivers.this, "Updated successfully", Toast.LENGTH_SHORT).show();

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