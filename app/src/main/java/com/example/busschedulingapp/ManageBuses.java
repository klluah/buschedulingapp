package com.example.busschedulingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;


public class ManageBuses extends AppCompatActivity {

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private EditText busname, availableseats, destination, arrivaltime,
            departuretime, driver, fare, platenumber;
    private Button addbus, deletebus, save, cancel;
    private ImageView viewbusinfo;
    private String key;

    DrawerLayout drawerLayout;
    BusFunctions busFunctions = new BusFunctions(); // Bus Function Object

    SwipeRefreshLayout swipeRefreshLayout;
    RecyclerView recyclerView;
    RVAdapter adapter;
    BusFunctions bus;
    boolean isLoading = false;

    Placeholder pholder = new Placeholder();
    boolean editClicked = pholder.editClicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_buses);

        drawerLayout = findViewById(R.id.drawer_layout);
        addbus = (Button) findViewById(R.id.addbusadmin);
        deletebus = (Button) findViewById(R.id.deletebusadmin);
        save = (Button) findViewById(R.id.savebtn);
        cancel = (Button) findViewById(R.id.cancelbtn);

        // FOR POP UP
        dialogBuilder = new AlertDialog.Builder(this); // create dialog
        final View addavailablebuspopup = getLayoutInflater().inflate(R.layout.addbuspopup, null);

        // Assign the Value of Edittext
        // TODO: assign Image View
        busname = (EditText) addavailablebuspopup.findViewById(R.id.addbusname);
        availableseats = (EditText) addavailablebuspopup.findViewById(R.id.addavailalbeseats);
        destination = (EditText) addavailablebuspopup.findViewById(R.id.adddestination);
        arrivaltime = (EditText) addavailablebuspopup.findViewById(R.id.addarrivaltime);
        departuretime = (EditText) addavailablebuspopup.findViewById(R.id.adddeparturetime);
        driver = (EditText) addavailablebuspopup.findViewById(R.id.adddrivername);
        fare = (EditText) addavailablebuspopup.findViewById(R.id.addfare);
        platenumber = (EditText) addavailablebuspopup.findViewById(R.id.addplatenumber);
        save = (Button) addavailablebuspopup.findViewById(R.id.savebtn);
        cancel = (Button) addavailablebuspopup.findViewById(R.id.cancelbtn);
        dialogBuilder.setView(addavailablebuspopup);

        // Scroll View of Buses
        swipeRefreshLayout = findViewById(R.id.swip);
        recyclerView = findViewById(R.id.rv);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(manager);
        adapter = new RVAdapter(this);
        recyclerView.setAdapter(adapter);
        bus = new BusFunctions();
        loadData();

        Bus bus_edit = (Bus)getIntent().getSerializableExtra("EDIT");

        final EditText edit_busname = addavailablebuspopup.findViewById(R.id.addbusname);
        final EditText edit_availseats = addavailablebuspopup.findViewById(R.id.addavailalbeseats);
        final EditText edit_destination = addavailablebuspopup.findViewById(R.id.adddestination);
        final EditText edit_arrtime = addavailablebuspopup.findViewById(R.id.addarrivaltime);
        final  EditText edit_deptime = addavailablebuspopup.findViewById(R.id.adddeparturetime);
        final EditText edit_driver = addavailablebuspopup.findViewById(R.id.adddrivername);
        final EditText edit_fare = addavailablebuspopup.findViewById(R.id.addfare);
        final EditText edit_platenumber = addavailablebuspopup.findViewById(R.id.addplatenumber);

        // Retrieve Data from the DB
        if (bus_edit != null) {
            //save.setText("UPDATE");
            edit_busname.setText(bus_edit.getBusname());
            edit_availseats.setText(bus_edit.getAvailableseats());
            edit_destination.setText(bus_edit.getDestination());
            edit_arrtime.setText(bus_edit.getArrivaltime());
            edit_deptime.setText(bus_edit.getDeparturetime());
            edit_driver.setText(bus_edit.getDriver());
            edit_fare.setText(bus_edit.getFare());
            edit_platenumber.setText(bus_edit.getPlatenumber());
        }

        //String status = RVAdapter.stat;
        //Log.d("data value", "test");
        if (Placeholder.getEditClick()) {
            dialog = dialogBuilder.create();
            dialog.show();

            save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Bus bus = new Bus(busname.getText().toString(), availableseats.getText().toString(),
                            destination.getText().toString(), arrivaltime.getText().toString(),
                            departuretime.getText().toString(), driver.getText().toString(),
                            fare.getText().toString(), platenumber.getText().toString());

                    if (bus_edit == null) { // IF THE BUS  IS NULL
                        busFunctions.add(bus).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(ManageBuses.this, "Record is inserted", Toast.LENGTH_SHORT).show();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(ManageBuses.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        HashMap<String, Object> hashMap = new HashMap<>(); // Initialize HashMap

                        // Edit Child
                        hashMap.put("busname", busname.getText().toString());
                        hashMap.put("availableseats", availableseats.getText().toString());
                        hashMap.put("destination", destination.getText().toString());
                        hashMap.put("arrivaltime", arrivaltime.getText().toString());
                        hashMap.put("departuretime", departuretime.getText().toString());
                        hashMap.put("driver", driver.getText().toString());
                        hashMap.put("fare", fare.getText().toString());
                        hashMap.put("platenumber", platenumber.getText().toString());

                        busFunctions.update(bus_edit.getKey(), hashMap).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(ManageBuses.this, "Record is updated", Toast.LENGTH_SHORT).show();
                                finish(); // Close the activity after update
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(ManageBuses.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    dialog.dismiss(); // Close dialog
                    Placeholder.setEditClick(false);
                }
            });

            cancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // dismiss dialog
                    dialog.dismiss();
                    Placeholder.setEditClick(false);
                }
            });
        }

        // Display Add Bus Dialog
        addbus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAddAvailableBusDialog();
            }
        });
        Placeholder.setEditClick(false);
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
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    // ____________________________________ Navigation Start ____________________________________ //
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
        // Recreate Activity
        recreate();
    }

    public void CLickBusInfo(View view) {
        // Redirect to Manage Buses
        ManageSchedule.redirectActivity(this, ManageBusInformation.class);
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
    // ____________________________________ Navigation End ____________________________________ //

    //  Create Add Available Bus Dialog
    public void createEditBusDialog() {

    }

    //  Create Add Available Bus Dialog
    public void createAddAvailableBusDialog() {
        dialogBuilder = new AlertDialog.Builder(this); // create dialog
        final View addavailablebuspopup = getLayoutInflater().inflate(R.layout.addbuspopup, null);


        // Assign the Value of Edittext
        // TODO: assign Image View
        busname = (EditText) addavailablebuspopup.findViewById(R.id.addbusname);
        availableseats = (EditText) addavailablebuspopup.findViewById(R.id.addavailalbeseats);
        destination = (EditText) addavailablebuspopup.findViewById(R.id.adddestination);
        arrivaltime = (EditText) addavailablebuspopup.findViewById(R.id.addarrivaltime);
        departuretime = (EditText) addavailablebuspopup.findViewById(R.id.adddeparturetime);
        driver = (EditText) addavailablebuspopup.findViewById(R.id.adddrivername);
        fare = (EditText) addavailablebuspopup.findViewById(R.id.addfare);
        platenumber = (EditText) addavailablebuspopup.findViewById(R.id.addplatenumber);
        save = (Button) addavailablebuspopup.findViewById(R.id.savebtn);
        cancel = (Button) addavailablebuspopup.findViewById(R.id.cancelbtn);
        dialogBuilder.setView(addavailablebuspopup);

        dialog = dialogBuilder.create();
        dialog.show();

        save.setOnClickListener(new View.OnClickListener() { // SAVE BUS TO DATABASE
            @Override
            public void onClick(View view) {
                Bus bus = new Bus(busname.getText().toString().trim(), availableseats.getText().toString().trim(),
                        destination.getText().toString().trim(), arrivaltime.getText().toString().trim(),
                        departuretime.getText().toString().trim(), driver.getText().toString().trim(),
                        fare.getText().toString().trim(), platenumber.getText().toString().trim());

                // for is empty to work
                String Sbusname = bus.busname, Savailableseats = bus.availableseats, Sdestination = bus.destination,
                        Sarrivaltime = bus.arrivaltime, Sdeparturetime = bus.departuretime, Sdriver = bus.driver,
                        Sfare = bus.fare, Splatenumber = bus.platenumber;

                if (Sbusname.isEmpty()) {
                    busname.setError("Bus name is required.");
                    busname.requestFocus();
                    return;
                }
                if (Savailableseats.isEmpty()) {
                    availableseats.setError("Available seats is required.");
                    availableseats.requestFocus();
                    return;
                }
                if (Sdestination.isEmpty()) {
                    destination.setError("Destination is required.");
                    availableseats.requestFocus();
                    return;
                }
                if (Sarrivaltime.isEmpty()) {
                    arrivaltime.setError("Arrival time is required.");
                    arrivaltime.requestFocus();
                    return;
                }
                if (Sdeparturetime.isEmpty()) {
                    departuretime.setError("Departure time is required.");
                    departuretime.requestFocus();
                    return;
                }
                if (Sdriver.isEmpty()) {
                    driver.setError("Driver name is required.");
                    driver.requestFocus();
                    return;
                }
                if (Sfare.isEmpty()) {
                    fare.setError("Fare is required.");
                    fare.requestFocus();
                    return;
                }
                if (Splatenumber.isEmpty()) {
                    platenumber.setError("Plate number is required.");
                    platenumber.requestFocus();
                    return;
                }

                busFunctions.add(bus).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(ManageBuses.this, "Record is inserted", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ManageBuses.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.dismiss();// Close dialog
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
}