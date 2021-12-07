package com.busschedulingap.busschedulingapp.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.busschedulingap.busschedulingapp.MainActivity;
import com.busschedulingap.busschedulingapp.Placeholder;
import com.busschedulingap.busschedulingapp.R;
public class ManageReservation extends AppCompatActivity {

    DrawerLayout drawerLayout;
    private TextView admin_title;
    private Button alps, japs, jam, dltbco, delarosa, supreme;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_reservaiton);

        drawerLayout = findViewById(R.id.drawer_layout);
        admin_title = findViewById(R.id.admin_title);
        admin_title.setText("Manage Resevation");

        // Buttons
        alps = findViewById(R.id.alps_btn);
        japs = findViewById(R.id.japs_btn);
        jam = findViewById(R.id.jam_btn);
        dltbco = findViewById(R.id.dltbco_btn);
        delarosa = findViewById(R.id.delarosa_btn);
        supreme = findViewById(R.id.supreme_btn);

        alps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Placeholder.selectedbus = "ALPS";
                Intent intent = new Intent(ManageReservation.this, Reservation_Busname.class);
                startActivity(intent);
            }
        });
        japs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Placeholder.selectedbus = "JAPS";
                Intent intent = new Intent(ManageReservation.this, Reservation_Busname.class);
                startActivity(intent);
            }
        });
        jam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Placeholder.selectedbus = "JAM";
                Intent intent = new Intent(ManageReservation.this, Reservation_Busname.class);
                startActivity(intent);
            }
        });
        dltbco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Placeholder.selectedbus = "DLTBCO";
                Intent intent = new Intent(ManageReservation.this, Reservation_Busname.class);
                startActivity(intent);
            }
        });
        delarosa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Placeholder.selectedbus = "DELAROSA";
                Intent intent = new Intent(ManageReservation.this, Reservation_Busname.class);
                startActivity(intent);
            }
        });
        supreme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Placeholder.selectedbus = "SUPREME";
                Intent intent = new Intent(ManageReservation.this, Reservation_Busname.class);
                startActivity(intent);
            }
        });
    }

    //  ---------------  NAVIGATION  ---------------  //
    public void ClickMenu(View view) { ManageSchedule.openDrawer(drawerLayout); } // Open Drawer
    public void ClickLogo(View view) { ManageSchedule.closeDrawer(drawerLayout); } // Open Drawer
    public void CLickSchedule(View view) { ManageSchedule.redirectActivity(this, ManageSchedule.class); } // Redirect to Manage Schedule
    public void CLickAvailableBus(View view) { ManageSchedule.redirectActivity(this, ManageBuses.class); } // Redirect to Manage Buses
    public void CLickReservation(View view) { recreate(); } // Redirect to Manage Reservation
    public void CLickPrintReport(View view) { ManageSchedule.redirectActivity(this, PrintReport.class); } // Recreate Activity
    public void CLickLogout(View view) { ManageSchedule.redirectActivity(this, MainActivity.class); } // Logout
    @Override
    protected void onPause() {
        super.onPause();
        ManageSchedule.closeDrawer(drawerLayout);// Close Drawer
    }
}