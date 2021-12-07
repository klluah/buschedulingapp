package com.busschedulingap.busschedulingapp.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.busschedulingap.busschedulingapp.MainActivity;
import com.busschedulingap.busschedulingapp.Placeholder;
import com.busschedulingap.busschedulingapp.R;
import com.busschedulingap.busschedulingapp.bus.Bus;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class PrintReport extends AppCompatActivity {

    DrawerLayout drawerLayout;
    private TextView admin_title, date, bus_alps, bus_japs, bus_jam, bus_dltbco, bus_delarosa, bus_supreme,
            bus_total, res_alps, res_japs, res_jam, res_dltbco, res_delarosa, res_supreme, res_total;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_print_report);
        drawerLayout = findViewById(R.id.drawer_layout);
        admin_title = findViewById(R.id.admin_title);
        admin_title.setText("Report");

        // SET TEXTVIEW
        date = findViewById(R.id.current_date);
        date.setText(Placeholder.currentdate);
        bus_alps = findViewById(R.id.bus_alps);
        bus_japs = findViewById(R.id.bus_japs);
        bus_jam = findViewById(R.id.bus_jam);
        bus_dltbco = findViewById(R.id.bus_dltbco);
        bus_delarosa = findViewById(R.id.bus_delarosa);
        bus_supreme = findViewById(R.id.bus_supreme);
        bus_total = findViewById(R.id.bus_total);
        res_alps = findViewById(R.id.res_alps);
        res_japs = findViewById(R.id.res_japs);
        res_jam = findViewById(R.id.res_jam);
        res_dltbco = findViewById(R.id.res_dltbco);
        res_delarosa = findViewById(R.id.res_delarosa);
        res_supreme = findViewById(R.id.res_supreme);
        res_total = findViewById(R.id.res_total);

        // REFERENCE
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference rq = FirebaseDatabase.getInstance().getReference("Reservation");
        DatabaseReference dbbus_total = db.getReference().child("Bus").child(Placeholder.currentdate);
        DatabaseReference dbres_total = db.getReference().child("Reservation")
                .child(Placeholder.currentdate).child("Successful");

        // QUERY BUS
        Query qbalpsref = FirebaseDatabase.getInstance().getReference(Bus.class.getSimpleName())
                .child(Placeholder.currentdate).orderByChild("busname").equalTo("ALPS");
        Query qbjapsref = FirebaseDatabase.getInstance().getReference(Bus.class.getSimpleName())
                .child(Placeholder.currentdate).orderByChild("busname").equalTo("JAPS");
        Query qbjamref = FirebaseDatabase.getInstance().getReference(Bus.class.getSimpleName())
                .child(Placeholder.currentdate).orderByChild("busname").equalTo("JAM");
        Query qbdltbcosref = FirebaseDatabase.getInstance().getReference(Bus.class.getSimpleName())
                .child(Placeholder.currentdate).orderByChild("busname").equalTo("DLTBCO");
        Query qbdelerosaref = FirebaseDatabase.getInstance().getReference(Bus.class.getSimpleName())
                .child(Placeholder.currentdate).orderByChild("busname").equalTo("DELAROSA");
        Query qbsupremeref = FirebaseDatabase.getInstance().getReference(Bus.class.getSimpleName())
                .child(Placeholder.currentdate).orderByChild("busname").equalTo("SUPREME");

        // QUERY RES
        Query qralpsref = FirebaseDatabase.getInstance().getReference("Reservation")
                .child(Placeholder.currentdate).child("Successful").orderByChild("rbusname").equalTo("ALPS");
        Query qrjapsref = FirebaseDatabase.getInstance().getReference("Reservation")
                .child(Placeholder.currentdate).child("Successful").orderByChild("rbusname").equalTo("JAPS");
        Query qrjamref = FirebaseDatabase.getInstance().getReference("Reservation")
                .child(Placeholder.currentdate).child("Successful").orderByChild("rbusname").equalTo("JAM");
        Query qrdltbcosref = FirebaseDatabase.getInstance().getReference("Reservation")
                .child(Placeholder.currentdate).child("Successful").orderByChild("rbusname").equalTo("DLTBCO");
        Query qrdelerosaref = FirebaseDatabase.getInstance().getReference("Reservation")
                .child(Placeholder.currentdate).child("Successful").orderByChild("rbusname").equalTo("DELAROSA");
        Query qrsupremeref = FirebaseDatabase.getInstance().getReference("Reservation")
                .child(Placeholder.currentdate).child("Successful").orderByChild("rbusname").equalTo("SUPREME");

        // BUS GET VALUE
        qbalpsref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    int size = (int) snapshot.getChildrenCount();
                    bus_alps.setText(String.valueOf(size));
                } else {
                    bus_alps.setText(String.valueOf(0));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
        qbjapsref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    int size = (int) snapshot.getChildrenCount();
                    bus_japs.setText(String.valueOf(size));
                } else {
                    bus_japs.setText(String.valueOf(0));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
        qbjamref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    int size = (int) snapshot.getChildrenCount();
                    bus_jam.setText(String.valueOf(size));
                } else {
                    bus_jam.setText(String.valueOf(0));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
        qbdltbcosref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    int size = (int) snapshot.getChildrenCount();
                    bus_dltbco.setText(String.valueOf(size));
                } else {
                    bus_dltbco.setText(String.valueOf(0));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
        qbdelerosaref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    int size = (int) snapshot.getChildrenCount();
                    bus_delarosa.setText(String.valueOf(size));
                } else {
                    bus_delarosa.setText(String.valueOf(0));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
        qbsupremeref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    int size = (int) snapshot.getChildrenCount();
                    bus_supreme.setText(String.valueOf(size));
                } else {
                    bus_supreme.setText(String.valueOf(0));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        dbbus_total.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    int size = (int) snapshot.getChildrenCount();
                    //Toast.makeText(PrintReport.this, String.valueOf(size), Toast.LENGTH_SHORT).show();
                    bus_total.setText(String.valueOf(size));
                } else {
                    bus_total.setText(String.valueOf(0));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        // RESERVATION GET VALUE
        qralpsref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    int size = (int) snapshot.getChildrenCount();
                    res_alps.setText(String.valueOf(size));
                } else {
                    res_alps.setText(String.valueOf(0));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
        qrjapsref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    int size = (int) snapshot.getChildrenCount();
                    res_japs.setText(String.valueOf(size));
                } else {
                    res_japs.setText(String.valueOf(0));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
        qrjamref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    int size = (int) snapshot.getChildrenCount();
                    res_jam.setText(String.valueOf(size));
                } else {
                    res_jam.setText(String.valueOf(0));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
        qrdltbcosref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    int size = (int) snapshot.getChildrenCount();
                    res_dltbco.setText(String.valueOf(size));
                } else {
                    res_dltbco.setText(String.valueOf(0));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
        qrdelerosaref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    int size = (int) snapshot.getChildrenCount();
                    res_delarosa.setText(String.valueOf(size));
                } else {
                    res_delarosa.setText(String.valueOf(0));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
        qrsupremeref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    int size = (int) snapshot.getChildrenCount();
                    res_supreme.setText(String.valueOf(size));
                    Toast.makeText(PrintReport.this, String.valueOf(size), Toast.LENGTH_SHORT).show();
                } else {
                    res_supreme.setText(String.valueOf(0));

                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        dbres_total.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    int size = (int) snapshot.getChildrenCount();
                    res_total.setText(String.valueOf(size));
                } else {
                    res_total.setText(String.valueOf(0));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

    }

    //  ---------------  NAVIGATION  ---------------  //
    public void ClickMenu(View view) { ManageSchedule.openDrawer(drawerLayout); } // Open Drawer
    public void ClickLogo(View view) { ManageSchedule.closeDrawer(drawerLayout); } // Open Drawer
    public void CLickSchedule(View view) { ManageSchedule.redirectActivity(this, ManageSchedule.class); } // Redirect to Manage Schedule
    public void CLickAvailableBus(View view) { ManageSchedule.redirectActivity(this, ManageBuses.class); } // Redirect to Manage Buses
    public void CLickReservation(View view) {ManageSchedule.redirectActivity(this, ManageReservation.class); } // Redirect to Manage Reservation
    public void CLickPrintReport(View view) { recreate(); } // Recreate Activity
    public void CLickLogout(View view) { ManageSchedule.redirectActivity(this, MainActivity.class); } // Logout
    @Override
    protected void onPause() {
        super.onPause();
        ManageSchedule.closeDrawer(drawerLayout);// Close Drawer
    }
}