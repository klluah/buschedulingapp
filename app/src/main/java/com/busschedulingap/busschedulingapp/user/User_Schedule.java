package com.busschedulingap.busschedulingapp.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.busschedulingap.busschedulingapp.schedule.ScheduleAdapter_User;
import com.busschedulingap.busschedulingapp.MainActivity;
import com.busschedulingap.busschedulingapp.R;
import com.busschedulingap.busschedulingapp.schedule.SchedFunctions;
import com.busschedulingap.busschedulingapp.schedule.Schedule;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class User_Schedule extends AppCompatActivity {

    DrawerLayout drawerLayout;
    private TextView title, name, email;
    private ImageView scheduleimg;
    private Button uploadsched, schedsavebtn;
    private String imgurl;

    DatabaseReference DataRef;
    DatabaseReference UrlDataRef;
    StorageReference StorageRef;

    private String key;
    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    ScheduleAdapter_User adapter;
    SchedFunctions sched;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_schedule);

        title = findViewById(R.id.user_title);
        title.setText("Schedule");
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

        swipeRefreshLayout = findViewById(R.id.sched_swip);
        recyclerView = findViewById(R.id.sched_rv);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, 0));
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        adapter = new ScheduleAdapter_User(this);

        RecyclerView recycview =findViewById(R.id.sched_rv);
        recycview.addItemDecoration(new DividerItemDecoration(this, 0));

        recyclerView.setAdapter(adapter);
        sched = new SchedFunctions();
        loadData();
    }

    private void loadData() {
        sched.get().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Schedule> schedules = new ArrayList<>(); // List of Items
                for (DataSnapshot data : snapshot.getChildren()) {
                    Schedule schedule = data.getValue(Schedule.class);
                    schedule.setKey(data.getKey());
                    schedules.add(schedule);
                    key = data.getKey();
                }
                adapter.setItems(schedules);
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
    public void CLickUserSchedule(View view) { recreate(); }
    public void CLickUserAvailableBus(View view) { User_Dashboard.redirectActivity(this, User_AvailableBus.class); }
    public void CLickUserReservation(View view) { User_Dashboard.redirectActivity(this, User_Reservation.class); }
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