package com.example.busschedulingapp;

import android.os.Bundle;
import android.view.Menu;

import com.example.busschedulingapp.ui.available_bus.AvailableBusFragment;
import com.example.busschedulingapp.ui.reservation.ReservationFragment;
import com.example.busschedulingapp.ui.schedule.ScheduleFragment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.busschedulingapp.databinding.ActivityDashboardBinding;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.OAuthProvider;

public class DashboardActivity extends AppCompatActivity {
    /*    Define Variables   */
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityDashboardBinding binding;
    private Button dashboardtoschedule;
    private Button schedulebtn;
    private Button availablebusbtn;
    private Button reservationbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*       Call Functions      */

        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarDashboard.toolbar);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_dashboard, R.id.nav_profile, R.id.nav_schedule,
                R.id.nav_available_bus, R.id.nav_reservation, R.id.nav_logout)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_dashboard);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


        dashboardtoschedule = (Button) findViewById(R.id.dashboardtoschedule);
        dashboardtoschedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openSchedule();
            }
        });

        availablebusbtn = (Button) findViewById(R.id.dashboardtoavailablebus);
        availablebusbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openAvailable();
            }
        });

        reservationbtn = (Button) findViewById(R.id.dashboardtoreservation);
        reservationbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openReservation();
            }
        });

        //FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
    }

    public void openSchedule() {
        Intent intent = new Intent(this, ScheduleFragment.class);
        startActivity(intent);
    }

    private void openAvailable() {
        Intent intent = new Intent(this, AvailableBusFragment.class);
        startActivity(intent);
    }

    private void openReservation() {
        Intent intent = new Intent(this, ReservationFragment.class);
        startActivity(intent);
    }

    /*         Function Definition           */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_dashboard);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}