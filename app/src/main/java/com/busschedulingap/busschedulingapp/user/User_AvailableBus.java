package com.busschedulingap.busschedulingapp.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.busschedulingap.busschedulingapp.MainActivity;
import com.busschedulingap.busschedulingapp.bus.Bus;
import com.busschedulingap.busschedulingapp.bus.BusFunctions;
import com.busschedulingap.busschedulingapp.R;
import com.busschedulingap.busschedulingapp.bus.RVAdapter_User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class User_AvailableBus extends AppCompatActivity {

    DrawerLayout drawerLayout;
    private TextView title, text_option, name, email;
    Button go;

    private String key;
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    RVAdapter_User adapter;
    BusFunctions bus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_available_bus);

        title = findViewById(R.id.user_title);
        title.setText("Available Buses");
        drawerLayout = findViewById(R.id.user_drawer_layout);

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

        final View availablebusitem = getLayoutInflater().inflate(R.layout.availablebusitem, null);
        text_option = availablebusitem.findViewById(R.id.text_option);

        swipeRefreshLayout = findViewById(R.id.swip);
        recyclerView = findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        adapter = new RVAdapter_User(this);

        recyclerView.setAdapter(adapter);
        bus = new BusFunctions();
        loadData();
    }

    private void loadData() {
        bus.get().addValueEventListener(new ValueEventListener() {
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

    public void ClickMenu(View view) { openDrawer(drawerLayout); }

    public static void openDrawer(@NonNull DrawerLayout drawerLayout) { drawerLayout.openDrawer(GravityCompat.START); }

    public void ClickLogo(View view) { closeDrawer(drawerLayout); }

    public static void closeDrawer(@NonNull DrawerLayout drawerLayout) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public void CLickUserDashboard(View view) { User_Dashboard.redirectActivity(this, User_Dashboard.class); }

    public void CLickUserSchedule(View view) { User_Dashboard.redirectActivity(this, User_Schedule.class); }

    public void CLickUserAvailableBus(View view) { recreate(); }

    public void CLickUserReservation(View view) { User_Dashboard.redirectActivity(this, User_Reservation.class); }

    public void CLickUserProfile(View view) { User_Dashboard.redirectActivity(this, User_Profile.class); }

    public void CLickLogout(View view) { redirectActivity(this, MainActivity.class); }

    //public void CLickLogout(View view) { logout(this); }

    private void logout(User_AvailableBus user_dashboard) {
        AlertDialog.Builder builder = new AlertDialog.Builder(user_dashboard);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure you want to logout?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                user_dashboard.finishAffinity();
                redirectActivity(User_AvailableBus.this, MainActivity.class);
            }
        });
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();

    }

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