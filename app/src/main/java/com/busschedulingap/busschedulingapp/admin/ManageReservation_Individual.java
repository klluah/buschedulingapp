package com.busschedulingap.busschedulingapp.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.busschedulingap.busschedulingapp.Placeholder;
import com.busschedulingap.busschedulingapp.bus.BusFunctions;
import com.busschedulingap.busschedulingapp.reservation.IndividualReservationAdapter;
import com.busschedulingap.busschedulingapp.reservation.IndividualReservationAdapter1;
import com.busschedulingap.busschedulingapp.reservation.Reservation;
import com.busschedulingap.busschedulingapp.R;
import com.busschedulingap.busschedulingapp.reservation.ResFunctions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ManageReservation_Individual extends AppCompatActivity {

    DrawerLayout drawerLayout;
    DatabaseReference dbref;
    BusFunctions busFunctions = new BusFunctions(); // Bus Function Object
    ResFunctions resFunction1 = new ResFunctions(1);
    ResFunctions resFunction2 = new ResFunctions(2);
    private ImageView backbtn;
    private TextView title, busname, platenumber;
    private String key, Sbusname, Splatenumber;


    SwipeRefreshLayout swipeRefreshLayout1, swipeRefreshLayout2;
    RecyclerView recyclerView1, recyclerView2;
    IndividualReservationAdapter adapter1;
    IndividualReservationAdapter1 adapter2;
    Query query1, query2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_reservation_individual);

        backbtn = findViewById(R.id.backbtn);
        title = findViewById(R.id.user_title);
        title.setText("Reservation");

        busname = findViewById(R.id.resbusname);
        platenumber = findViewById(R.id.resplatenumber);

        Placeholder.availseatsChanged = true; // Available Seats Infinite Loop Stopper

        // Edit Bus Info
        dbref = FirebaseDatabase.getInstance().getReference().child("Bus").child(Placeholder.currentdate); // Access Database

        // Assign selected item bus key to String
        //Bus bus_key = (Bus)getIntent().getSerializableExtra("Buskey");
        //Toast.makeText(ManageReservation_Individual.this, String.valueOf(bus_key.getKey()), Toast.LENGTH_SHORT).show();

        // Access the busname and plate number of selected bus item
        DatabaseReference selectedbusref = FirebaseDatabase.getInstance().getReference().child("Bus").child(Placeholder.currentdate).child(Placeholder.cbuskey);
        selectedbusref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Sbusname = snapshot.child("busname").getValue().toString();
                Placeholder.cplate_number = snapshot.child("platenumber").getValue().toString();
                busname.setText(Sbusname);
                platenumber.setText(Splatenumber);
                //Toast.makeText(ManageReservation_Individual.this, Sbusname, Toast.LENGTH_SHORT).show();
                //Toast.makeText(ManageReservation_Individual.this, Splatenumber, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { }
        });


        // Query reservation based on selected item
        query1 = FirebaseDatabase.getInstance().getReference(Reservation.class.getSimpleName())
                .child(Placeholder.currentdate).child("Pending").orderByChild("rplatenumber").equalTo(Splatenumber);

        // Query reservation based on selected item
        query2 = FirebaseDatabase.getInstance().getReference(Reservation.class.getSimpleName())
                .child(Placeholder.currentdate).child("Successful").orderByChild("rplatenumber").equalTo(Splatenumber);

        // Scroll View of Reservation
        swipeRefreshLayout1 = findViewById(R.id.indi_swip);
        recyclerView1 = findViewById(R.id.indi_rv);
        recyclerView1.setHasFixedSize(true);
        LinearLayoutManager manager1 = new LinearLayoutManager(this);
        recyclerView1.setLayoutManager(manager1);
        adapter1 = new IndividualReservationAdapter(this);
        recyclerView1.setAdapter(adapter1);
        loadData1();

        // Scroll View of Reservation
        swipeRefreshLayout2 = findViewById(R.id.indisuccess_swip);
        recyclerView2 = findViewById(R.id.indisuccess_rv);
        recyclerView2.setHasFixedSize(true);
        LinearLayoutManager manager2 = new LinearLayoutManager(this);
        recyclerView2.setLayoutManager(manager2);
        adapter2 = new IndividualReservationAdapter1(this);
        recyclerView2.setAdapter(adapter2);
        loadData2();

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ManageReservation_Individual.this, Reservation_Busname.class);
                startActivity(intent);
            }
        });
    }

    private void loadData1() {
        resFunction1.get().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Reservation> ress = new ArrayList<>();
                for (DataSnapshot data : snapshot.getChildren()) {
                    Reservation reservation = data.getValue(Reservation.class);
                    reservation.setKey(data.getKey());

                    ress.add(reservation);
                    key = data.getKey();
                }
                adapter1.setItems(ress);
                adapter1.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    private void loadData2() {
        resFunction2.get().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Reservation> ress = new ArrayList<>();
                for (DataSnapshot data : snapshot.getChildren()) {
                    Reservation reservation = data.getValue(Reservation.class);
                    reservation.setKey(data.getKey());

                    ress.add(reservation);
                    key = data.getKey();
                }
                adapter2.setItems(ress);
                adapter2.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }
}