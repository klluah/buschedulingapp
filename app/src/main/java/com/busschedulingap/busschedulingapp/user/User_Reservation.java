package com.busschedulingap.busschedulingapp.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.busschedulingap.busschedulingapp.MainActivity;
import com.busschedulingap.busschedulingapp.reservation.Reservation;
import com.busschedulingap.busschedulingapp.R;
import com.busschedulingap.busschedulingapp.reservation.RESAdapter;
import com.busschedulingap.busschedulingapp.reservation.RESAdapter1;
import com.busschedulingap.busschedulingapp.reservation.ResFunctions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class User_Reservation extends AppCompatActivity {

    DrawerLayout drawerLayout;
    private TextView title, name, email;
    private String key;
    DatabaseReference db;

    SwipeRefreshLayout swipeRefreshLayout1, swipeRefreshLayout2;
    RecyclerView recyclerView1, recyclerView2;
    RESAdapter adapter1;
    RESAdapter1 adapter2;
    ResFunctions reservation1 = new ResFunctions(1);
    ResFunctions reservation2 = new ResFunctions(2);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_reservation);

        title = findViewById(R.id.user_title);
        title.setText("Reservation");
        drawerLayout = findViewById(R.id.user_drawer_layout);

        // Scroll View of Pending Reservation
        swipeRefreshLayout1 = findViewById(R.id.swip_reservation);
        recyclerView1 = findViewById(R.id.res_rv);
        recyclerView1.setHasFixedSize(true);
        LinearLayoutManager manager1 = new LinearLayoutManager(this);
        recyclerView1.setLayoutManager(manager1);
        adapter1 = new RESAdapter(this);
        recyclerView1.setAdapter(adapter1);
        loadData1();

        // Scroll View of Successful Reservation
        swipeRefreshLayout2 = findViewById(R.id.swip_reservations);
        recyclerView2 = findViewById(R.id.res_rvs);
        recyclerView2.setHasFixedSize(true);
        LinearLayoutManager manager2 = new LinearLayoutManager(this);
        recyclerView2.setLayoutManager(manager2);
        adapter2 = new RESAdapter1(this);
        recyclerView2.setAdapter(adapter2);
        loadData2();

        String Uid = String.valueOf(FirebaseAuth.getInstance().getCurrentUser().getUid());
        name = (TextView) findViewById(R.id.name_drawer);
        email = (TextView) findViewById(R.id.email_drawer);

        // Access Current User Data and Set it to TextView
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("Users").child(Uid);
        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                name.setText(snapshot.child("fullname").getValue(String.class));
                email.setText(snapshot.child("email").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    private void loadData1() {
        reservation1.get().addValueEventListener(new ValueEventListener() {
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
        reservation2.get().addValueEventListener(new ValueEventListener() {
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

    public void ClickMenu(View view) { openDrawer(drawerLayout); }
    public static void openDrawer(@NonNull DrawerLayout drawerLayout) { drawerLayout.openDrawer(GravityCompat.START); }
    public void ClickLogo(View view) { closeDrawer(drawerLayout); }
    public static void closeDrawer(@NonNull DrawerLayout drawerLayout) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) { drawerLayout.closeDrawer(GravityCompat.START); }
    }

    public void CLickUserDashboard(View view) { User_Dashboard.redirectActivity(this, User_Dashboard.class); }
    public void CLickUserSchedule(View view) { User_Dashboard.redirectActivity(this, User_Schedule.class); }
    public void CLickUserAvailableBus(View view) { User_Dashboard.redirectActivity(this, User_AvailableBus.class); }
    public void CLickUserReservation(View view) { recreate(); }
    public void CLickUserProfile(View view) { User_Dashboard.redirectActivity(this, User_Profile.class); }
    public void CLickLogout(View view) { redirectActivity(this, MainActivity.class); }
    public static void redirectActivity(Activity activity, Class aClass) {
        Intent intent = new Intent(activity, aClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        closeDrawer(drawerLayout);// Close Drawer
    }
}