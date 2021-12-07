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
import com.busschedulingap.busschedulingapp.bus.Bus;
import com.busschedulingap.busschedulingapp.bus.BusReservationAdapter;
import com.busschedulingap.busschedulingapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Reservation_Busname extends AppCompatActivity {

    DrawerLayout drawerLayout;
    private TextView admin_title, currentdate, resbusname;
    private ImageView backbtn;

    private String key;
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    BusReservationAdapter adapter;

    Query query1 = FirebaseDatabase.getInstance().getReference(Bus.class.getSimpleName())
            .child(Placeholder.currentdate).orderByChild("busname").equalTo("ALPS");
    Query query2 = FirebaseDatabase.getInstance().getReference(Bus.class.getSimpleName())
            .child(Placeholder.currentdate).orderByChild("busname").equalTo("JAPS");
    Query query3 = FirebaseDatabase.getInstance().getReference(Bus.class.getSimpleName())
            .child(Placeholder.currentdate).orderByChild("busname").equalTo("JAM");
    Query query4 = FirebaseDatabase.getInstance().getReference(Bus.class.getSimpleName())
            .child(Placeholder.currentdate).orderByChild("busname").equalTo("DLTBCO");
    Query query5 = FirebaseDatabase.getInstance().getReference(Bus.class.getSimpleName())
            .child(Placeholder.currentdate).orderByChild("busname").equalTo("DELAROSA");
    Query query6 = FirebaseDatabase.getInstance().getReference(Bus.class.getSimpleName())
            .child(Placeholder.currentdate).orderByChild("busname").equalTo("SUPREME");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation_busname);

        admin_title = findViewById(R.id.user_title);
        if(Placeholder.selectedbus.equalsIgnoreCase("ALPS")){
            admin_title.setText("Alps Buses");
        } else if(Placeholder.selectedbus.equalsIgnoreCase("JAPS")) {
            admin_title.setText("Japs Buses");
        } else if(Placeholder.selectedbus.equalsIgnoreCase("JAM")) {
            admin_title.setText("Jam Buses");
        } else if(Placeholder.selectedbus.equalsIgnoreCase("DLTBCO")) {
            admin_title.setText("Dltbco Buses");
        } else if(Placeholder.selectedbus.equalsIgnoreCase("DELAROSA")) {
            admin_title.setText("Delarosa Buses");
        } else if(Placeholder.selectedbus.equalsIgnoreCase("SUPREME")) {
            admin_title.setText("Supreme Buses");
        }

        currentdate = findViewById(R.id.rescurrentdate);
        currentdate.setText(Placeholder.currentdate);
        backbtn = findViewById(R.id.backbtn);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Reservation_Busname.this, ManageReservation.class);
                startActivity(intent);
            }
        });

        // ALPS
        swipeRefreshLayout = findViewById(R.id.resbus_swip);
        recyclerView = findViewById(R.id.resbus_rv);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager manager1 = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager1);
        adapter = new BusReservationAdapter(this);
        recyclerView.setAdapter(adapter);

        // Display data according to selected bus
        if(Placeholder.selectedbus.equalsIgnoreCase("ALPS")) { loadData1(); }
        if(Placeholder.selectedbus.equalsIgnoreCase("JAPS")) { loadData2(); }
        if(Placeholder.selectedbus.equalsIgnoreCase("JAM")) { loadData3(); }
        if(Placeholder.selectedbus.equalsIgnoreCase("DLTBCO")) { loadData4(); }
        if(Placeholder.selectedbus.equalsIgnoreCase("DELAROSA")) { loadData5(); }
        if(Placeholder.selectedbus.equalsIgnoreCase("SUPREME")) { loadData6(); }
    }

    private void loadData1() {
        query1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Bus> buss = new ArrayList<>(); // List of Items
                for (DataSnapshot data : snapshot.getChildren()) {
                    Bus bus = data.getValue(Bus.class);
                    bus.setKey(data.getKey());
                    buss.add(bus);
                    key = data.getKey();
                }
                adapter.setItems(buss);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    private void loadData2() {
        query2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Bus> buss = new ArrayList<>(); // List of Items
                for (DataSnapshot data : snapshot.getChildren()) {
                    Bus bus = data.getValue(Bus.class);
                    bus.setKey(data.getKey());
                    buss.add(bus);
                    key = data.getKey();
                }
                adapter.setItems(buss);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    private void loadData3() {
        query3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Bus> buss = new ArrayList<>(); // List of Items
                for (DataSnapshot data : snapshot.getChildren()) {
                    Bus bus = data.getValue(Bus.class);
                    bus.setKey(data.getKey());
                    buss.add(bus);
                    key = data.getKey();
                }
                adapter.setItems(buss);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    private void loadData4() {
        query4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Bus> buss = new ArrayList<>(); // List of Items
                for (DataSnapshot data : snapshot.getChildren()) {
                    Bus bus = data.getValue(Bus.class);
                    bus.setKey(data.getKey());
                    buss.add(bus);
                    key = data.getKey();
                }
                adapter.setItems(buss);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    private void loadData5() {
        query5.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Bus> buss = new ArrayList<>(); // List of Items
                for (DataSnapshot data : snapshot.getChildren()) {
                    Bus bus = data.getValue(Bus.class);
                    bus.setKey(data.getKey());
                    buss.add(bus);
                    key = data.getKey();
                }
                adapter.setItems(buss);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    private void loadData6() {
        query6.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Bus> buss = new ArrayList<>(); // List of Items
                for (DataSnapshot data : snapshot.getChildren()) {
                    Bus bus = data.getValue(Bus.class);
                    bus.setKey(data.getKey());
                    buss.add(bus);
                    key = data.getKey();
                }
                adapter.setItems(buss);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }
}