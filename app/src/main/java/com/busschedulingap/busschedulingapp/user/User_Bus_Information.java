package com.busschedulingap.busschedulingapp.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.busschedulingap.busschedulingapp.Placeholder;
import com.busschedulingap.busschedulingapp.bus.Bus;
import com.busschedulingap.busschedulingapp.reservation.Reservation;
import com.busschedulingap.busschedulingapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
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

public class User_Bus_Information extends AppCompatActivity {

    private ImageView backbtn;
    private ImageView busimg;
    private Button reserveseatbtn, confirm_reservation, cancelbtn;
    private TextView user_title;
    private EditText rname, rnumber, rbusname, rdestination, rdriver, ravailseat,
            rresid, rdeptime, rplatenumber, rfare;
    private String rdate;
    private String Sbusname, Savailableseats, Sdestination, Sarrivaltime,
            Sdeparturetime, Sdriver, Sfare, Splatenumber;
    private TextView busname, availableseats, destination, arrivaltime,
            departuretime, driver, fare, platenumber;
    DatabaseReference dbref;

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private String Uid, Resid, currentavailseats;
    public int residholder;
    DatabaseReference busesRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_bus_information);

        backbtn = findViewById(R.id.backbtn);
        user_title = findViewById(R.id.user_title);
        user_title.setText("Bus Information");
        busimg = findViewById(R.id.busimagebusinfouser);

        // Assign TextView to variable
        busname = findViewById(R.id.busnamebusinfouser);
        availableseats = findViewById(R.id.availseatsbusinfouser);
        destination = findViewById(R.id.destinationbusinfouser);
        arrivaltime = findViewById(R.id.arrtimebusinfouser);
        departuretime = findViewById(R.id.deptimebusinfouser);
        driver = findViewById(R.id.driverbusinfouser);
        fare = findViewById(R.id.farebusinfouser);
        platenumber = findViewById(R.id.platenumberbusinfouser);
        reserveseatbtn = findViewById(R.id.reserveseatbtn);
        confirm_reservation = findViewById(R.id.confirm_reservation);
        cancelbtn = findViewById(R.id.cancel_reservation);

        // Set Current date
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
        rdate = s;
        Placeholder.currentdate = rdate;

        Placeholder.availseatsChanged = true;

        reserveseatbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Display Make Reservation Pop Up
                createReserveSeatDialog();
            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(User_Bus_Information.this, User_AvailableBus.class);
                startActivity(intent);
            }
        });

        dbref = FirebaseDatabase.getInstance().getReference().child("Bus").child(Placeholder.currentdate); // Access Database
        //String BusKey = getIntent().getStringExtra("BusKey");
        Bus bus_key = (Bus)getIntent().getSerializableExtra("BusKey");
        Placeholder.cbuskey = bus_key.getKey();
        //Toast.makeText(User_Bus_Information.this, Placeholder.cbuskey, Toast.LENGTH_LONG).show();

        dbref.child(bus_key.getKey()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    // Get data from Database
                    Sbusname = snapshot.child("busname").getValue().toString();
                    Savailableseats = snapshot.child("availableseats").getValue().toString();
                    Sdestination = snapshot.child("destination").getValue().toString();
                    Sarrivaltime = snapshot.child("arrivaltime").getValue().toString();
                    Sdeparturetime = snapshot.child("departuretime").getValue().toString();
                    Sdriver = snapshot.child("driver").getValue().toString();
                    Sfare = snapshot.child("fare").getValue().toString();
                    Splatenumber = snapshot.child("platenumber").getValue().toString();

                    // Set data to the TextView
                    busname.setText(Sbusname);
                    availableseats.setText(Savailableseats);
                    destination.setText(Sdestination);
                    arrivaltime.setText(Sarrivaltime);
                    departuretime.setText(Sdeparturetime);
                    driver.setText(Sdriver);
                    fare.setText(Sfare);
                    platenumber.setText(Splatenumber);

                    if (Sbusname.equalsIgnoreCase("ALPS")) { busimg.setImageResource(R.drawable.alps); }
                    else if (Sbusname.equalsIgnoreCase("DELAROSA")) { busimg.setImageResource(R.drawable.delarosa); }
                    else if (Sbusname.equalsIgnoreCase("DLTBCO")) { busimg.setImageResource(R.drawable.dltbco); }
                    else if (Sbusname.equalsIgnoreCase("JAM")) { busimg.setImageResource(R.drawable.jam); }
                    else if (Sbusname.equalsIgnoreCase("JAPS")) { busimg.setImageResource(R.drawable.japs); }
                    else if (Sbusname.equalsIgnoreCase("SUPREME")) { busimg.setImageResource(R.drawable.supreme); }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    private void createReserveSeatDialog() {
        dialogBuilder = new AlertDialog.Builder(this); // create dialog
        final View reserve_seatpopup = getLayoutInflater().inflate(R.layout.activity_user_reserve_seat, null);

        // Assign the Value of EditText
        rname = (EditText) reserve_seatpopup.findViewById(R.id.reservename);
        rnumber = (EditText) reserve_seatpopup.findViewById(R.id.reservemobilenumber);
        rbusname = (EditText) reserve_seatpopup.findViewById(R.id.reservebusname);
        rdestination = (EditText) reserve_seatpopup.findViewById(R.id.reservedestination);
        rdriver = (EditText) reserve_seatpopup.findViewById(R.id.reservedriver);
        ravailseat = (EditText) reserve_seatpopup.findViewById(R.id.reserveseatnumber);
        rresid = (EditText) reserve_seatpopup.findViewById(R.id.reserveid);
        rdeptime = (EditText) reserve_seatpopup.findViewById(R.id.reservedeptime);
        rplatenumber = (EditText) reserve_seatpopup.findViewById(R.id.reserveplatenumber);
        rfare  = (EditText) reserve_seatpopup.findViewById(R.id.reservefare);
        confirm_reservation = (Button) reserve_seatpopup.findViewById(R.id.confirm_reservation);
        cancelbtn = (Button) reserve_seatpopup.findViewById(R.id.cancel_reservation);

        // Get info from user, then display it to textview
        Uid = String.valueOf(FirebaseAuth.getInstance().getCurrentUser().getUid()); //get current user uid
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("Users").child(Uid);
        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                rname.setText(snapshot.child("fullname").getValue(String.class));
                rnumber.setText(snapshot.child("mobilenumber").getValue(String.class));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        // Get current bus info, then display it to textview
        busesRef = FirebaseDatabase.getInstance().getReference().child("Bus").child(Placeholder.currentdate).child(Placeholder.cbuskey);
        busesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                rbusname.setText(snapshot.child("busname").getValue(String.class));
                rdestination.setText(snapshot.child("destination").getValue(String.class));
                rdriver.setText(snapshot.child("driver").getValue(String.class));
                ravailseat.setText(snapshot.child("availableseats").getValue(String.class));
                rresid.setText(snapshot.child("resid").getValue(String.class));
                rdeptime.setText(snapshot.child("departuretime").getValue(String.class));
                rplatenumber.setText(snapshot.child("platenumber").getValue(String.class));
                rfare.setText(snapshot.child("fare").getValue(String.class));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        // TODO Reservation ID Logic
        int residholder = Placeholder.resid + 1;

        // get latest reservation id then set it to variable and textview
        DatabaseReference readcresid = FirebaseDatabase.getInstance().getReference();
        readcresid.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Integer cresid = snapshot.child("currentresid").getValue(int.class); // update current reservation id
                Placeholder.resid = cresid;
                rresid.setText(String.valueOf(snapshot.child("currentresid").getValue(int.class)));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        dialogBuilder.setView(reserve_seatpopup);
        dialog = dialogBuilder.create();
        dialog.show();

        confirm_reservation.setOnClickListener(new View.OnClickListener() { // SAVE BUS TO DATABASE
            @Override
            public void onClick(View view) {
                String rstatus = "Pending";
                Reservation reservation = new Reservation(rstatus, rdate, rname.getText().toString().trim(), rnumber.getText().toString().trim(),
                        rbusname.getText().toString().trim(), rdestination.getText().toString().trim(), rdriver.getText().toString().trim(),
                        ravailseat.getText().toString().trim(), rresid.getText().toString().trim(), rdeptime.getText().toString().trim(),
                        rplatenumber.getText().toString().trim(), rfare.getText().toString().trim());

                String Srname = reservation.rname, Srnumber = reservation.rnumber;
                if (Srname.isEmpty()) {
                    rname.setError("Full name is required.");
                    rname.requestFocus();
                    return;
                }
                if (Srnumber.isEmpty()) {
                    rnumber.setError("Email is required.");
                    rnumber.requestFocus();
                    return;
                }

                Placeholder.availseatsChanged = true; // Available Seats Infinite Loop Stopper

                // Save the Reservation Data to Database
                DatabaseReference resref = FirebaseDatabase.getInstance().getReference("Reservation")
                        .child(rdate).child("Pending").push();
                Placeholder.reskey = resref.getKey(); // Reservation Key

                resref.setValue(reservation).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

                            // Save the new res id to db
                            Placeholder.resid = Placeholder.resid + 1;
                            DatabaseReference cresid = FirebaseDatabase.getInstance().getReference().child("currentresid");
                            cresid.setValue(Placeholder.resid);

                            // Save the Reservation to Current User Database with the same ID
                            DatabaseReference rusersRef = FirebaseDatabase.getInstance().getReference().child("Users").child(Uid)
                                    .child("MyReservation").child(Placeholder.reskey);
                            rusersRef.setValue(reservation);

                            dialog.dismiss();
                        } else { }
                    }
                });

                // Open Reservation Confirmation
                Intent intent = new Intent(User_Bus_Information.this, Payment.class);
                startActivity(intent);
            }
        });

        cancelbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
}