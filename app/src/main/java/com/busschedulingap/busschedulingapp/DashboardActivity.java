package com.busschedulingap.busschedulingapp;

import android.os.Bundle;
import android.view.Menu;

import com.busschedulingap.busschedulingapp.R;
import com.busschedulingap.busschedulingapp.ui.available_bus.AvailableBusFragment;
import com.busschedulingap.busschedulingapp.ui.reservation.ReservationFragment;
import com.google.android.material.navigation.NavigationView;
;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.busschedulingap.busschedulingapp.databinding.ActivityDashboardBinding;

public class DashboardActivity extends AppCompatActivity {
    /*    Define Variables   */
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityDashboardBinding binding;
    private Button dashboardtoschedule;
    private Button schedulebtn;
    private Button availablebusbtn;
    private Button reservationbtn;
    private TextView admin_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*       Call Functions      */

        binding = ActivityDashboardBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        admin_title = findViewById(R.id.admin_title);

        setSupportActionBar(binding.appBarDashboard.toolbar);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
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