package com.busschedulingap.busschedulingapp.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.busschedulingap.busschedulingapp.MainActivity;
import com.busschedulingap.busschedulingapp.bus.Bus;
import com.busschedulingap.busschedulingapp.bus.BusFunctions;
import com.busschedulingap.busschedulingapp.Placeholder;
import com.busschedulingap.busschedulingapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ManageBusInformation extends AppCompatActivity {

    DrawerLayout drawerLayout;
    private AlertDialog.Builder dialogBuilderEdit;
    private AlertDialog dialog;
    private TextView admin_title;
    private ImageView busimg;
    private String Sbusname, Savailableseats, Sdestination, Sarrivaltime,
            Sdeparturetime, Sdriver, Sfare, Splatenumber;
    private TextView busname, availableseats, destination, arrivaltime,
            departuretime, driver, fare, platenumber;
    private Spinner spinner;
    private String selectedbusname;
    private Button editbusinfo, save, cancel;
    DatabaseReference dbref;
    BusFunctions busFunctions = new BusFunctions(); // Bus Function Object


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_bus_information);
        admin_title = findViewById(R.id.admin_title);
        admin_title.setText("Manage Bus Information");
        drawerLayout = findViewById(R.id.drawer_layout);

        // Assign TextView to variable
        busname = findViewById(R.id.busnamebusinfo);
        availableseats = findViewById(R.id.availseatsbusinfo);
        destination = findViewById(R.id.destinationbusinfo);
        arrivaltime = findViewById(R.id.arrtimebusinfo);
        departuretime = findViewById(R.id.deptimebusinfo);
        driver = findViewById(R.id.driverbusinfo);
        fare = findViewById(R.id.farebusinfo);
        platenumber = findViewById(R.id.platenumberbusinfo);
        //editbusinfo = findViewById(R.id.nextbtn);

        dialogBuilderEdit = new AlertDialog.Builder(this); // create dialog
        final View addavailablebuspopup = getLayoutInflater().inflate(R.layout.addbuspopup, null);
        dialogBuilderEdit.setView(addavailablebuspopup);

        drawerLayout = findViewById(R.id.drawer_layout);
        save = (Button) addavailablebuspopup.findViewById(R.id.savebtn);
        cancel = (Button) addavailablebuspopup.findViewById(R.id.cancelbtn);

        // Edit Bus Info
        dbref = FirebaseDatabase.getInstance().getReference().child("Bus").child(Placeholder.currentdate); // Access Database
        //String BusKey = getIntent().getStringExtra("BusKey");
        Bus bus_key = (Bus)getIntent().getSerializableExtra("BusKey");

        dbref.child(bus_key.getKey()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    // Get data from Database
                    Sbusname = snapshot.child("busname").getValue().toString();
                    Savailableseats = snapshot.child("availableseats").getValue().toString();
                    Sdestination = snapshot.child("destination").getValue().toString();
                    Sarrivaltime = snapshot.child("arrivaltime").getValue().toString();
                    Sdeparturetime = snapshot.child("departuretime").getValue().toString();
                    Sdriver = snapshot.child("driver").getValue().toString();
                    Sfare = snapshot.child("fare").getValue().toString();
                    Splatenumber = snapshot.child("platenumber").getValue().toString();

                    // Set data to the TextView
                    busname.setText(Sbusname);
                    availableseats.setText(Savailableseats);
                    destination.setText(Sdestination);
                    arrivaltime.setText(Sarrivaltime);
                    departuretime.setText(Sdeparturetime);
                    driver.setText(Sdriver);
                    fare.setText(Sfare);
                    platenumber.setText(Splatenumber);

                    busimg = findViewById(R.id.busimagebusinfo);
                    if (Sbusname.equalsIgnoreCase("ALPS")) { busimg.setImageResource(R.drawable.alps); }
                    else if (Sbusname.equalsIgnoreCase("DELAROSA")) { busimg.setImageResource(R.drawable.delarosa); }
                    else if (Sbusname.equalsIgnoreCase("DLTBCO")) { busimg.setImageResource(R.drawable.dltbco); }
                    else if (Sbusname.equalsIgnoreCase("JAM")) { busimg.setImageResource(R.drawable.jam); }
                    else if (Sbusname.equalsIgnoreCase("JAPS")) { busimg.setImageResource(R.drawable.japs); }
                    else if (Sbusname.equalsIgnoreCase("SUPREME")) { busimg.setImageResource(R.drawable.supreme); }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        // Edit Bus Information
        Bus bus_edit = (Bus)getIntent().getSerializableExtra("edit");

        final EditText edit_availseats = addavailablebuspopup.findViewById(R.id.addavailalbeseats);
        final EditText edit_destination = addavailablebuspopup.findViewById(R.id.adddestination);
        final EditText edit_arrtime = addavailablebuspopup.findViewById(R.id.addarrivaltime);
        final EditText edit_deptime = addavailablebuspopup.findViewById(R.id.adddeparturetime);
        final EditText edit_driver = addavailablebuspopup.findViewById(R.id.adddrivername);
        final EditText edit_fare = addavailablebuspopup.findViewById(R.id.addfare);
        final EditText edit_platenumber = addavailablebuspopup.findViewById(R.id.addplatenumber);

        // Retrieve Data from the DB
        if (bus_edit != null) {
            //save.setText("UPDATE");
            edit_availseats.setText(bus_edit.getAvailableseats());
            edit_destination.setText(bus_edit.getDestination());
            edit_arrtime.setText(bus_edit.getArrivaltime());
            edit_deptime.setText(bus_edit.getDeparturetime());
            edit_driver.setText(bus_edit.getDriver());
            edit_fare.setText(bus_edit.getFare());
            edit_platenumber.setText(bus_edit.getPlatenumber());
        }

        /*editbusinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = dialogBuilderEdit.create();
                dialog.show();

                // ------------------- Dropdown ------------------- //
                busimg = findViewById(R.id.busimagebusinfo);
                if (Sbusname.equalsIgnoreCase("ALPS")) { busimg.setImageResource(R.drawable.alps); }
                else if (Sbusname.equalsIgnoreCase("DELAROSA")) { busimg.setImageResource(R.drawable.delarosa); }
                else if (Sbusname.equalsIgnoreCase("DLTBCO")) { busimg.setImageResource(R.drawable.dltbco); }
                else if (Sbusname.equalsIgnoreCase("JAM")) { busimg.setImageResource(R.drawable.jam); }
                else if (Sbusname.equalsIgnoreCase("JAPS")) { busimg.setImageResource(R.drawable.japs); }
                else if (Sbusname.equalsIgnoreCase("SUPREME")) { busimg.setImageResource(R.drawable.supreme); }

                // Dropdown
                spinner = addavailablebuspopup.findViewById(R.id.busname12);
                String[] items = getResources().getStringArray(R.array.bus_name);
                ArrayAdapter arrayAdapter = new ArrayAdapter(ManageBusInformation.this, R.layout.support_simple_spinner_dropdown_item, items);
                spinner.setAdapter(arrayAdapter);

                spinner.setSelection(arrayAdapter.getPosition(bus_edit.getBusname()));

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        // Set Selected Dropdown to Variable
                        selectedbusname = parent.getItemAtPosition(position).toString();

                        // Set Image based on Selected Dropdown Item
                        busimg = (ImageView) addavailablebuspopup.findViewById(R.id.busimage);
                        if (selectedbusname.equalsIgnoreCase("ALPS")) { busimg.setImageResource(R.drawable.alps); }
                        else if (selectedbusname.equalsIgnoreCase("DELAROSA")) { busimg.setImageResource(R.drawable.delarosa); }
                        else if (selectedbusname.equalsIgnoreCase("DLTBCO")) { busimg.setImageResource(R.drawable.dltbco); }
                        else if (selectedbusname.equalsIgnoreCase("JAM")) { busimg.setImageResource(R.drawable.jam); }
                        else if (selectedbusname.equalsIgnoreCase("JAPS")) { busimg.setImageResource(R.drawable.japs); }
                        else if (selectedbusname.equalsIgnoreCase("SUPREME")) { busimg.setImageResource(R.drawable.supreme); }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {}
                });

                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Bus bus = new Bus(busname.getText().toString(), availableseats.getText().toString(),
                                destination.getText().toString(), arrivaltime.getText().toString(),
                                departuretime.getText().toString(), driver.getText().toString(),
                                fare.getText().toString(), platenumber.getText().toString());

                        if (bus_edit == null) { // IF THE BUS IS NULL
                            busFunctions.add(bus).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {
                                    Toast.makeText(ManageBusInformation.this, "Record is inserted", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(ManageBusInformation.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            HashMap<String, Object> hashMap = new HashMap<>(); // Initialize HashMap

                            // Edit Child
                            //hashMap.put("imageurl", imageUri.getPath().toString());
                            hashMap.put("busname", selectedbusname);
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
                                    Toast.makeText(ManageBusInformation.this, "Record is updated", Toast.LENGTH_SHORT).show();
                                    finish(); // Close the activity after update
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(ManageBusInformation.this, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        dialog.dismiss(); // Close dialog
                        Placeholder.setEditClick(false);
//                  recreate();
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
        });*/
    }

    public void ClickMenu(View view) { ManageSchedule.openDrawer(drawerLayout); } // Open Drawer
    public void ClickLogo(View view) { ManageSchedule.closeDrawer(drawerLayout); } // Open Drawer
    public void CLickSchedule(View view) { ManageSchedule.redirectActivity(this, ManageSchedule.class); } // Redirect to Manage Schedule
    public void CLickAvailableBus(View view) { ManageSchedule.redirectActivity(this, ManageBuses.class); } // Redirect to Manage Buses
    public void CLickReservation(View view) { ManageSchedule.redirectActivity(this, ManageReservation.class); } // Redirect to Manage Reservation
    public void CLickPrintReport(View view) { ManageSchedule.redirectActivity(this, PrintReport.class); } // Redirect to Print Report
    public void CLickLogout(View view) { ManageSchedule.redirectActivity(this, MainActivity.class); }

    @Override
    protected void onPause() {
        super.onPause();
        ManageSchedule.closeDrawer(drawerLayout); // Close Drawer
    }
}