package com.busschedulingap.busschedulingapp.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.busschedulingap.busschedulingapp.MainActivity;
import com.busschedulingap.busschedulingapp.Placeholder;
import com.busschedulingap.busschedulingapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class User_Dashboard extends AppCompatActivity {

    DrawerLayout drawerLayout;
    private TextView title, name, email;
    private Button toschedule, toreservation, toavailablebus;
    private ImageView fb, google;
    private String rdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);

        title = findViewById(R.id.user_title);
        title.setText("Dashboard");
        drawerLayout = findViewById(R.id.user_drawer_layout);

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
        //Toast.makeText(User_Dashboard.this, s, Toast.LENGTH_LONG).show();
        rdate = s;
        Placeholder.currentdate = rdate;

        String Uid = String.valueOf(FirebaseAuth.getInstance().getCurrentUser().getUid());
        name = (TextView) findViewById(R.id.name_drawer);
        email = (TextView) findViewById(R.id.email_drawer);

        // Access Current User Data and Set it to TextView
        DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference().child("Users").child(Uid);
        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                name.setText(snapshot.child("fullname").getValue(String.class));
                email.setText(snapshot.child("email").getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        toschedule = findViewById(R.id.dashboardtoschedule);
        toreservation = findViewById(R.id.dashboardtoreservation);
        toavailablebus = findViewById(R.id.dashboardtoavailablebus);
        fb = findViewById(R.id.imageButtonFacebook);
        google = findViewById(R.id.imageButtonGoogle);

        toschedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(User_Dashboard.this, User_Schedule.class);
                startActivity(intent);
            }
        });

        toreservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(User_Dashboard.this, User_Reservation.class);
                startActivity(intent);
            }
        });

        toavailablebus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(User_Dashboard.this, User_AvailableBus.class);
                startActivity(intent);
            }
        });

        fb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://www.facebook.com/BatangasCityGrandTerminal";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String url = "https://mail.google.com/mail/u/0/#inbox?compose=CllgCHrfTDvVWvSXtCcwWPwJsNwSbKWltHvfQxcQlHPpDmxxBsRDzcvPvVFLgVNpCNpttbmLTtg";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
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

    public void CLickUserDashboard(View view) { recreate(); }
    public void CLickUserSchedule(View view) { redirectActivity(this, User_Schedule.class); }
    public void CLickUserAvailableBus(View view) { redirectActivity(this, User_AvailableBus.class); }
    public void CLickUserReservation(View view) { redirectActivity(this, User_Reservation.class); }
    public void CLickUserProfile(View view) { redirectActivity(this, User_Profile.class); }
    public void CLickLogout(View view) { redirectActivity(this, MainActivity.class); }
    public void logout(User_Dashboard user_dashboard) {
        AlertDialog.Builder builder = new AlertDialog.Builder(user_dashboard);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure you want to logout?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                user_dashboard.finishAffinity();
                System.exit(1);
            }
        });
        builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        builder.show();
    }

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
}