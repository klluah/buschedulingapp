package com.busschedulingap.busschedulingapp.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.busschedulingap.busschedulingapp.bus.Bus;
import com.busschedulingap.busschedulingapp.bus.BusFunctions;
import com.busschedulingap.busschedulingapp.MainActivity;
import com.busschedulingap.busschedulingapp.Placeholder;
import com.busschedulingap.busschedulingapp.R;
import com.busschedulingap.busschedulingapp.bus.RVAdapter;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.HashMap;


public class ManageBuses extends AppCompatActivity {

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog.Builder dialogBuilderEdit;
    private AlertDialog dialog;
    private static final int REQUEST_CODE_IMAGE = 101;
    private TextView uploadimg, admin_title, text_option;
    private EditText busname, availableseats, destination, arrivaltime,
            departuretime, driver, fare, platenumber;
    private Button addbus, save, cancel;
    private ImageView busimg, itemimg;
    private String key, rdate;
    private Spinner spinner;
    private String selectedbusname;

    Uri imageUri;
    boolean isImageAdded = false;

    DrawerLayout drawerLayout;
    BusFunctions busFunctions = new BusFunctions(); // Bus Function Object

    DatabaseReference DataRef;
    DatabaseReference DataRefChild;
    StorageReference StorageRef;

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
        save = (Button) findViewById(R.id.savebtn);
        cancel = (Button) findViewById(R.id.cancelbtn);

        final View itemview = getLayoutInflater().inflate(R.layout.availablebusitem, null);
        itemimg = itemview.findViewById(R.id.itemimg);

        // DataRef = FirebaseDatabase.getInstance().getReference().child("Bus");
        DataRef = FirebaseDatabase.getInstance().getReference("Bus");
        //DataRefChild = DataRef.child("ImageUrl");
        StorageRef = FirebaseStorage.getInstance().getReference().child("BusImage");

        // FOR POP UP
        admin_title = findViewById(R.id.admin_title);
        admin_title.setText("Manage Buses");
        dialogBuilderEdit = new AlertDialog.Builder(this); // create dialog
        final View addavailablebuspopup = getLayoutInflater().inflate(R.layout.addbuspopup, null);

        // Assign the Value of Edittext
        busimg = (ImageView) addavailablebuspopup.findViewById(R.id.busimage);
        uploadimg = (TextView) addavailablebuspopup.findViewById(R.id.uploadimage);

        availableseats = (EditText) addavailablebuspopup.findViewById(R.id.addavailalbeseats);
        destination = (EditText) addavailablebuspopup.findViewById(R.id.adddestination);
        arrivaltime = (EditText) addavailablebuspopup.findViewById(R.id.addarrivaltime);
        departuretime = (EditText) addavailablebuspopup.findViewById(R.id.adddeparturetime);
        driver = (EditText) addavailablebuspopup.findViewById(R.id.adddrivername);
        fare = (EditText) addavailablebuspopup.findViewById(R.id.addfare);
        platenumber = (EditText) addavailablebuspopup.findViewById(R.id.addplatenumber);
        save = (Button) addavailablebuspopup.findViewById(R.id.savebtn);
        cancel = (Button) addavailablebuspopup.findViewById(R.id.cancelbtn);

        dialogBuilderEdit.setView(addavailablebuspopup);

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

        if (Placeholder.getEditClick()) {
            dialog = dialogBuilderEdit.create();
            dialog.show();

            // ------------------- Dropdown ------------------- //
            spinner = addavailablebuspopup.findViewById(R.id.busname12);
            String[] items = getResources().getStringArray(R.array.bus_name);
            ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, items);
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
                    Bus bus = new Bus(selectedbusname, availableseats.getText().toString(),
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
                    OpenManageBuses();
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
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    // ____________________________________ Navigation Start ____________________________________ //
    public void OpenManageBuses() {
        Intent intent = new Intent(this, ManageBuses.class);
        startActivity(intent);
    }

    public void ClickMenu(View view) { ManageSchedule.openDrawer(drawerLayout); } // Open Drawer
    public void ClickLogo(View view) { ManageSchedule.closeDrawer(drawerLayout); } // Open Drawer
    public void CLickSchedule(View view) { ManageSchedule.redirectActivity(this, ManageSchedule.class); } // Redirect to Manage Schedule
    public void CLickAvailableBus(View view) {  recreate(); } // Redirect to Manage Buses
    public void CLickReservation(View view) { ManageSchedule.redirectActivity(this, ManageReservation.class); } // Redirect to Manage Reservation
    public void CLickPrintReport(View view) { ManageSchedule.redirectActivity(this, PrintReport.class); } // Recreate Activity
    public void CLickLogout(View view) { ManageSchedule.redirectActivity(this, MainActivity.class); } // Logout
    @Override
    protected void onPause() {
        super.onPause();
        ManageSchedule.closeDrawer(drawerLayout);// Close Drawer
    }
    // ____________________________________ Navigation End ____________________________________ //

    //  Create Add Available Bus Dialog
    public void createAddAvailableBusDialog() {
        dialogBuilder = new AlertDialog.Builder(this); // create dialog
        final View addavailablebuspopup = getLayoutInflater().inflate(R.layout.addbuspopup, null);

        // Dropdown
        spinner = addavailablebuspopup.findViewById(R.id.busname12);
        String[] items = getResources().getStringArray(R.array.bus_name);
        ArrayAdapter arrayAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item, items);
        spinner.setAdapter(arrayAdapter);

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

        // Assign the Value of Edittext
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

                Bus bus = new Bus(selectedbusname, availableseats.getText().toString().trim(),
                        destination.getText().toString().trim(), arrivaltime.getText().toString().trim(),
                        departuretime.getText().toString().trim(), driver.getText().toString().trim(),
                        fare.getText().toString().trim(), platenumber.getText().toString().trim());

                // for is empty to work
                String Savailableseats = bus.availableseats, Sdestination = bus.destination,
                        Sarrivaltime = bus.arrivaltime, Sdeparturetime = bus.departuretime, Sdriver = bus.driver,
                        Sfare = bus.fare, Splatenumber = bus.platenumber;

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

        busimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Selecting Image from Gallery
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(intent, REQUEST_CODE_IMAGE);
            }
        });
    }
}