package com.busschedulingap.busschedulingapp.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.busschedulingap.busschedulingapp.MainActivity;
import com.busschedulingap.busschedulingapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class User_Profile extends AppCompatActivity {

    DrawerLayout drawerLayout;
    private TextView title, name, number, email, birthdate, drawer_name, drawer_email;
    private Button edit_profile, popup_save;
    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;
    private String Uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        title = findViewById(R.id.user_title);
        title.setText("Profile");
        drawerLayout = findViewById(R.id.user_drawer_layout);

        Uid = String.valueOf(FirebaseAuth.getInstance().getCurrentUser().getUid()); //get current user uid
        name = (TextView) findViewById(R.id.profile_name);
        email = (TextView) findViewById(R.id.profile_email);
        number = (TextView) findViewById(R.id.profile_mobilenumber);
        birthdate = (TextView) findViewById(R.id.profile_birthdate);

        String Uid = String.valueOf(FirebaseAuth.getInstance().getCurrentUser().getUid());
        drawer_name = (TextView) findViewById(R.id.name_drawer);
        drawer_email = (TextView) findViewById(R.id.email_drawer);

        // Access Current User Data and Set it to TextView
        DatabaseReference usersRefF = FirebaseDatabase.getInstance().getReference().child("Users").child(Uid);
        usersRefF.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                drawer_name.setText(snapshot.child("fullname").getValue(String.class));
                drawer_email.setText(snapshot.child("email").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        final View adduserinfopopup = getLayoutInflater().inflate(R.layout.userinfopopup, null);

        // Access Current User Data and Set it to TextView
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("Users").child(Uid);
        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                name.setText(snapshot.child("fullname").getValue(String.class));
                email.setText(snapshot.child("email").getValue(String.class));
                number.setText(snapshot.child("mobilenumber").getValue(String.class));
                birthdate.setText(snapshot.child("birthdate").getValue(String.class));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        edit_profile = findViewById(R.id.edit_profile);
        edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createUserInfoDialog();
            }
        });
    }

    public void ClickMenu(View view) { openDrawer(drawerLayout); }
    public static void openDrawer(@NonNull DrawerLayout drawerLayout) { drawerLayout.openDrawer(GravityCompat.START); }
    public void ClickLogo(View view) { closeDrawer(drawerLayout); }
    public static void closeDrawer(@NonNull DrawerLayout drawerLayout) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }
    public void CLickUserDashboard(View view) { User_Dashboard.redirectActivity(this, User_Dashboard.class); }
    public void CLickUserSchedule(View view) { User_Dashboard.redirectActivity(this, User_Schedule.class); }
    public void CLickUserAvailableBus(View view) { User_Dashboard.redirectActivity(this, User_AvailableBus.class); }
    public void CLickUserReservation(View view) { User_Dashboard.redirectActivity(this, User_Reservation.class); }
    public void CLickUserProfile(View view) { recreate(); }
    public void CLickLogout(View view) { redirectActivity(this, MainActivity.class); }

    public static void redirectActivity(Activity activity, Class aClass) {
        Intent intent = new Intent(activity, aClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        closeDrawer(drawerLayout);// Close Drawer
    }

    private void createUserInfoDialog() {
        dialogBuilder = new AlertDialog.Builder(this); // create dialog
        final View edit_userinfopopup = getLayoutInflater().inflate(R.layout.edit_userinfo, null);

        EditText name, email, number, birthdate, password, confirm_password;
        // Assign the Value of EditText
        name = (EditText) edit_userinfopopup.findViewById(R.id.popup_name);
        email = (EditText) edit_userinfopopup.findViewById(R.id.popup_email);
        number = (EditText) edit_userinfopopup.findViewById(R.id.popup_number);
        birthdate = (EditText) edit_userinfopopup.findViewById(R.id.popup_birthdate);
        popup_save = (Button) edit_userinfopopup.findViewById(R.id.popup_save);

        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("Users").child(Uid);
        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                name.setText(snapshot.child("fullname").getValue(String.class));
                email.setText(snapshot.child("email").getValue(String.class));
                number.setText(snapshot.child("mobilenumber").getValue(String.class));
                birthdate.setText(snapshot.child("birthdate").getValue(String.class));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        dialogBuilder.setView(edit_userinfopopup);
        dialog = dialogBuilder.create();
        dialog.show();

        popup_save.setOnClickListener(new View.OnClickListener() { // SAVE BUS TO DATABASE
            @Override
            public void onClick(View view) {

                User user = new User(name.getText().toString().trim(), number.getText().toString().trim(),
                        email.getText().toString().trim(), birthdate.getText().toString().trim());

                // for is empty to work
                String Sname = user.fullname, Semail = user.email, Snumber = user.mobilenumber,
                        Sbirthdate = user.birthdate;

                if (Sname.isEmpty()) {
                    name.setError("Full name is required.");
                    name.requestFocus();
                    return;
                }
                if (Semail.isEmpty()) {
                    email.setError("Email is required.");
                    email.requestFocus();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher(Semail).matches()) { // EMAIL VALIDATOR
                    email.setError("Please provide valid email");
                    email.requestFocus();
                    return;
                }
                if (Snumber.isEmpty()) {
                    number.setError("Mobile number is required.");
                    number.requestFocus();
                    return;
                }
                if (Sbirthdate.isEmpty()) {
                    birthdate.setError("Birthdate is required.");
                    birthdate.requestFocus();
                    return;
                }

                FirebaseDatabase.getInstance().getReference("Users")
                        .child(FirebaseAuth.getInstance().getCurrentUser().getUid()) // RETURN THE ID OF REGISTERED USER
                        .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) { // IF USER IS REGISTERED, INFORM USER
                            Toast.makeText(User_Profile.this, "Update Successful.", Toast.LENGTH_LONG).show(); // USER MESSAGE
                            dialog.dismiss();
                        } else { // IF USER REGISTRATION FAILED
                            Toast.makeText(User_Profile.this, "Failed to register, try again.", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }
}