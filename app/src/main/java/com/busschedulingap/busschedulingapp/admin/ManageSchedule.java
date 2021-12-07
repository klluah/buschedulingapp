package com.busschedulingap.busschedulingapp.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.busschedulingap.busschedulingapp.schedule.SchedAdapter;
import com.busschedulingap.busschedulingapp.MainActivity;
import com.busschedulingap.busschedulingapp.Placeholder;
import com.busschedulingap.busschedulingapp.R;
import com.busschedulingap.busschedulingapp.schedule.SchedFunctions;
import com.busschedulingap.busschedulingapp.schedule.Schedule;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class ManageSchedule extends AppCompatActivity {

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;

    private EditText busname, destination, time, fare;
    private Button addsched, save, cancel;

    SchedFunctions schedFunctions = new SchedFunctions();

    DrawerLayout drawerLayout;
    private static final int REQUEST_CODE_IMAGE = 101;
    private TextView admin_title;

    private String key, rdate;

    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    SchedAdapter adapter;
    SchedFunctions sched;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_schedule);

        // Assign Variable
        drawerLayout = findViewById(R.id.drawer_layout);
        admin_title = findViewById(R.id.admin_title);
        admin_title.setText("Manage Schedule");
        addsched = findViewById(R.id.addschedbtn);

        // Set Current date
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);
        Calendar c = Calendar.getInstance();
        String currentdate = sdf.format(c.getTime());

        SimpleDateFormat readDate = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);
        readDate.setTimeZone(TimeZone.getTimeZone("GMT")); // missing line
        Date dateee = null;
        try {
            dateee = readDate.parse(currentdate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat writeDate = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);
        writeDate.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        String s = writeDate.format(dateee);
        rdate = s;
        Placeholder.currentdate = rdate;

        addsched.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAddAvailableBusDialog();
            }
        });

        swipeRefreshLayout = findViewById(R.id.sched_swip);
        recyclerView = findViewById(R.id.sched_rv);
        recyclerView.addItemDecoration(new DividerItemDecoration(this, 0));
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        adapter = new SchedAdapter(this);

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

    public void createAddAvailableBusDialog() {
        dialogBuilder = new AlertDialog.Builder(this); // create dialog
        final View addschedulepopup = getLayoutInflater().inflate(R.layout.addschedulepopup, null);

        // Assign the Value of Edittext
        busname = (EditText) addschedulepopup.findViewById(R.id.schedbusname);
        destination = (EditText) addschedulepopup.findViewById(R.id.scheddestination);
        time = (EditText) addschedulepopup.findViewById(R.id.schedtime);
        fare = (EditText) addschedulepopup.findViewById(R.id.schedfare);
        save = (Button) addschedulepopup.findViewById(R.id.schedsavebtn);
        cancel = (Button) addschedulepopup.findViewById(R.id.schedcancelbtn);
        dialogBuilder.setView(addschedulepopup);

        dialog = dialogBuilder.create();
        dialog.show();

        save.setOnClickListener(new View.OnClickListener() { // SAVE BUS TO DATABASE
            @Override
            public void onClick(View view) {

                Schedule sched = new Schedule(busname.getText().toString().trim(), destination.getText().toString().trim(),
                        time.getText().toString().trim(), fare.getText().toString().trim());

                // for is empty to work
                String Sbusname = sched.sbusname, Sdestination = sched.sdestination,
                        Stime = sched.stime, Sfare = sched.sfare;

                if (Sbusname.isEmpty()) {
                    busname.setError("Bus name is required.");
                    busname.requestFocus();
                    return;
                }
                if (Sdestination.isEmpty()) {
                    destination.setError("Destination is required.");
                    destination.requestFocus();
                    return;
                }
                if (Stime.isEmpty()) {
                    time.setError("Time is required.");
                    time.requestFocus();
                    return;
                }
                if (Sfare.isEmpty()) {
                    fare.setError("Fare is required.");
                    fare.requestFocus();
                    return;
                }

                schedFunctions.add(sched).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(ManageSchedule.this, "Record is inserted", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ManageSchedule.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.dismiss();// Close dialog
                recreate();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // dismiss dialog
                dialog.dismiss();
            }
        });
    }

    // ---------------------- Navigation --------------------- //
    public void CLickSchedule(View view) { recreate(); } // Recreate Activity
    public void ClickMenu(View view) { openDrawer(drawerLayout); } // Open Drawer
    public static void openDrawer(@NonNull DrawerLayout drawerLayout) { drawerLayout.openDrawer(GravityCompat.START); } // Open Drawer Layout
    public void ClickLogo(View view) { closeDrawer(drawerLayout); } // Close Drawer
    public static void closeDrawer(@NonNull DrawerLayout drawerLayout) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) { drawerLayout.closeDrawer(GravityCompat.START); }
    }
    public void CLickAvailableBus(View view) { redirectActivity(this, ManageBuses.class); } // Redirect to Manage Buses
    public void CLickReservation(View view) { redirectActivity(this, ManageReservation.class); }// Redirect to Manage Reservation
    public void CLickPrintReport(View view) { redirectActivity(this, PrintReport.class); }// Redirect to Print Report
    public void CLickLogout(View view) { ManageSchedule.redirectActivity(this, MainActivity.class); }
    public static void redirectActivity(Activity activity, Class aClass) {
        Intent intent = new Intent(activity, aClass); // Initialize Intent
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // Set Flag
        activity.startActivity(intent); // Start Activity
    }
}