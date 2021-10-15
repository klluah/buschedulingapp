package com.example.busschedulingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ManageSchedule extends AppCompatActivity {

    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_schedule);

        // Assign Variable
        drawerLayout = findViewById(R.id.drawer_layout);

    }

    public void ClickMenu(View view) {
        // Open Drawer
        openDrawer(drawerLayout);
    }

    public static void openDrawer(@NonNull DrawerLayout drawerLayout) {
        // Open Drawer Layout
        drawerLayout.openDrawer(GravityCompat.START);
    }

    public void ClickLogo(View view) {
        // Close Drawer
        closeDrawer(drawerLayout);
    }

    public static void closeDrawer(@NonNull DrawerLayout drawerLayout) {
        // Close Drawer Layout
        // Check Condition
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            // When Drawer is Open
            // Close Drawer
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }

    public void CLickSchedule(View view) {
        // Recreate Activity
        recreate();
    }

    public void CLickAvailableBus(View view) {
        // Redirect to Manage Buses
        redirectActivity(this, ManageBuses.class);
    }

    public void CLickBusInfo(View view) {
        // Redirect to Manage Bus Information
        redirectActivity(this, ManageBusInformation.class);
    }

    public void CLickReservation(View view) {
        // Redirect to Manage Reservation
        redirectActivity(this, ManageReservation.class);
    }

    public void CLickPrintReport(View view) {
        // Redirect to Print Report
        redirectActivity(this, PrintReport.class);
    }

    public void CLickLogout(View view) {
        // Exit App
        logout(this);
    }


    public static void logout(Activity activity) {
        // Initialize alert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        // Set title
        builder.setTitle("Logout");
        // Set message
        builder.setMessage("Are you sure you want to logout?");
        // Positive Yes Button
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Finish Activity
                activity.finishAffinity();
                // Exit App
                System.exit(0);
            }
        });
        // Negative No Button
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // Dismiss Dialog
                dialogInterface.dismiss();
            }
        });
        // Show Dialog
        builder.show();
    }


    public static void redirectActivity(Activity activity, Class aClass) {
        // Initialize Intent
        Intent intent = new Intent(activity, aClass);
        // Set Flag
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        // Start Activity
        activity.startActivity(intent);

    }

    @Override
    protected void onPause() {
        super.onPause();
        // Close Drawer
        closeDrawer(drawerLayout);
    }
}