package com.example.busschedulingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.View;

public class ManageBusInformation extends AppCompatActivity {

    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_bus_information);

        drawerLayout = findViewById(R.id.drawer_layout);
    }

    public void ClickMenu(View view) {
        // Open Drawer
        ManageSchedule.openDrawer(drawerLayout);
    }

    public void ClickLogo(View view) {
        // Open Drawer
        ManageSchedule.closeDrawer(drawerLayout);
    }

    public void CLickSchedule(View view) {
        // Redirect to Manage Schedule
        ManageSchedule.redirectActivity(this, ManageSchedule.class);
    }

    public void CLickAvailableBus(View view) {
        // Redirect to Manage Buses
        ManageSchedule.redirectActivity(this, ManageBuses.class);
    }

    public void CLickBusInfo(View view) {
        // Recreate Activity
        recreate();
    }

    public void CLickReservation(View view) {
        // Redirect to Manage Reservation
        ManageSchedule.redirectActivity(this, ManageReservation.class);
    }

    public void CLickPrintReport(View view) {
        // Redirect to Print Report
        ManageSchedule.redirectActivity(this, PrintReport.class);
    }

    public void CLickLogout(View view) {
        // Exit App
        ManageSchedule.logout(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // Close Drawer
        ManageSchedule.closeDrawer(drawerLayout);
    }
}