package com.busschedulingap.busschedulingapp.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.busschedulingap.busschedulingapp.Placeholder;
import com.busschedulingap.busschedulingapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class User_Reservation_Confirmation extends AppCompatActivity {

    private Button confirmbtn;
    private TextView date, name, busname, departuretime, platenumber, fare, destination, resid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_reservation_confirmation);

        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);
        Calendar c = Calendar.getInstance();
        String currentdate = sdf.format(c.getTime());

        SimpleDateFormat readDate = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);
        readDate.setTimeZone(TimeZone.getTimeZone("GMT")); // missing line
        Date dateee = null;
        try {
            dateee = readDate.parse(currentdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat writeDate = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);
        writeDate.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        String s = writeDate.format(dateee);

        confirmbtn = findViewById(R.id.nextbtn);
        date = findViewById(R.id.confirmdate);
        name = findViewById(R.id.confirmname);
        busname = findViewById(R.id.confirmnbus);
        departuretime = findViewById(R.id.confirmdeptime);
        platenumber = findViewById(R.id.confirmplatenumber);
        fare = findViewById(R.id.confirmfare);
        destination = findViewById(R.id.confirmndestination);
        resid = findViewById(R.id.confirmresid);

        // Get current reservation info, then display it to textview
        DatabaseReference busesRef = FirebaseDatabase.getInstance().getReference().child("Reservation").child(Placeholder.currentdate).child("Pending").child(Placeholder.reskey);
        busesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                date.setText(s);
                name.setText(snapshot.child("rname").getValue(String.class));
                busname.setText(snapshot.child("rbusname").getValue(String.class));
                departuretime.setText(snapshot.child("rdeptime").getValue(String.class));
                platenumber.setText(snapshot.child("rplatenumber").getValue(String.class));
                fare.setText(snapshot.child("rfare").getValue(String.class));
                destination.setText(snapshot.child("rdestination").getValue(String.class));
                resid.setText(snapshot.child("rresid").getValue(String.class));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        confirmbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(User_Reservation_Confirmation.this, User_Reservation.class);
                Toast.makeText(User_Reservation_Confirmation.this, "Reservation Successful.", Toast.LENGTH_LONG).show(); // USER MESSAGE
                startActivity(intent); // Open Reservation
            }
        });
    }
}
