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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.carrentalsystem.Models.BookingDetails;
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

public class RequestedBookings extends AppCompatActivity {

    ListView listView;
    List<BookingDetails> user;
    DatabaseReference ref;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requested_bookings);

        listView = (ListView)findViewById(R.id.listview);

        user = new ArrayList<>();

        ref = FirebaseDatabase.getInstance().getReference("Bookings");

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user.clear();

                for (DataSnapshot studentDatasnap : dataSnapshot.getChildren()) {

                    BookingDetails bookingDetails = studentDatasnap.getValue(BookingDetails.class);
                    user.add(bookingDetails);
                }

                MyAdapter adapter = new MyAdapter(RequestedBookings.this, R.layout.custom_booking_details, (ArrayList<BookingDetails>) user);
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

    class MyAdapter extends ArrayAdapter<BookingDetails> {
        LayoutInflater inflater;
        Context myContext;
        List<BookingDetails> user;


        public MyAdapter(Context context, int resource, ArrayList<BookingDetails> objects) {
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
                view = inflater.inflate(R.layout.custom_booking_details, null);

                holder.COL1 = (TextView) view.findViewById(R.id.number);
                holder.COL2 = (TextView) view.findViewById(R.id.price);
                holder.COL3 = (TextView) view.findViewById(R.id.rentdate);
                holder.COL4 = (TextView) view.findViewById(R.id.handover);
                holder.button1 = (Button) view.findViewById(R.id.edit);
                holder.button2 = (Button) view.findViewById(R.id.delete);
                holder.imageView = (ImageView) view.findViewById(R.id.imageView2);


                view.setTag(holder);
            } else {

                holder = (ViewHolder) view.getTag();
            }

            holder.COL1.setText("Vehicle number:- "+user.get(position).getNumber());
            holder.COL2.setText("Price:- "+user.get(position).getPrice());
            holder.COL3.setText("Rent date:- "+user.get(position).getRentdate());
            holder.COL4.setText("Hand Over Date:- "+user.get(position).getHandover());
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
                                    FirebaseDatabase.getInstance().getReference("Bookings").child(idd).removeValue();
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
                @SuppressLint("MissingInflatedId")
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getContext());
                    View view1 = inflater.inflate(R.layout.custom_update_booking_details, null);
                    dialogBuilder.setView(view1);

                    @SuppressLint({"MissingInflatedId", "LocalSuppress"})

                    EditText editText1;
                    EditText editText2;
                    EditText editText3;
                    EditText editText4;
                    EditText editText5;
                    TextView textView1;
                    TextView textView2;
                    Button button;
                    ImageView imageView;

                    textView1 = view1.findViewById(R.id.number);
                    textView2 = view1.findViewById(R.id.price);
                    editText1 = view1.findViewById(R.id.rentaldays);
                    editText2 = view1.findViewById(R.id.rentdate);
                    editText3 = view1.findViewById(R.id.handover);
                    editText4 = view1.findViewById(R.id.customername);
                    editText5 = view1.findViewById(R.id.customercontact);
                    button = view1.findViewById(R.id.addbtn);
                    imageView = view1.findViewById(R.id.imageView2);

                    final AlertDialog alertDialog = dialogBuilder.create();
                    alertDialog.show();

                    final String idd = user.get(position).getId();
                    final DatabaseReference reference = FirebaseDatabase.getInstance().getReference("Bookings").child(idd);
                    reference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            String rentaldays = (String) snapshot.child("rentaldate").getValue();
                            String rentdate = (String) snapshot.child("rentdate").getValue();
                            String handover = (String) snapshot.child("handover").getValue();
                            String customername = (String) snapshot.child("customername").getValue();
                            String customercontact = (String) snapshot.child("customercontact").getValue();
                            String number = (String) snapshot.child("number").getValue();
                            String price = (String) snapshot.child("price").getValue();

                            editText1.setText(rentaldays);
                            editText2.setText(rentdate);
                            editText3.setText(handover);
                            editText4.setText(customername);
                            editText5.setText(customercontact);
                            textView1.setText(number);
                            textView2.setText(price);

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });


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

                                HashMap map = new HashMap();
                                map.put("rentaldays", rentaldays);
                                map.put("rentdate", rentdate);
                                map.put("handover", handover);
                                map.put("customername", customername);
                                map.put("customercontact", customercontact);
                                reference.updateChildren(map);

                                Toast.makeText(RequestedBookings.this, "Updated successfully", Toast.LENGTH_SHORT).show();

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