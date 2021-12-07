package com.busschedulingap.busschedulingapp.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.busschedulingap.busschedulingapp.Placeholder;
import com.busschedulingap.busschedulingapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Payment extends AppCompatActivity {

    private Button nextbtn;
    private ImageView qr_img;
    private String fare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_payment);

        nextbtn = findViewById(R.id.nextbtn);
        qr_img = findViewById(R.id.qr_image);

        // Get current reservation info, then display it to textview
        DatabaseReference busesRef = FirebaseDatabase.getInstance().getReference().child("Reservation")
                .child(Placeholder.currentdate).child("Pending").child(Placeholder.reskey);
        busesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                fare = snapshot.child("rfare").getValue(String.class);

                // Set Image for QR code
                if (fare.equalsIgnoreCase("200php")) {
                    qr_img.setImageResource(R.drawable.twohundred); }
                else if (fare.equalsIgnoreCase("250php")) {
                    qr_img.setImageResource(R.drawable.twofifthy); }
                else if (fare.equalsIgnoreCase("150php")) {
                    qr_img.setImageResource(R.drawable.onefifthy); }
                else if (fare.equalsIgnoreCase("95php")) {
                    qr_img.setImageResource(R.drawable.ninetyfive); }
                else if (fare.equalsIgnoreCase("154php")) {
                    qr_img.setImageResource(R.drawable.onefivefour); }
                else if (fare.equalsIgnoreCase("182php")) {
                    qr_img.setImageResource(R.drawable.oneeighttwo); }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Payment.this, User_Reservation_Confirmation.class);
                startActivity(intent); // Open Reservation
            }
        });
    }
}