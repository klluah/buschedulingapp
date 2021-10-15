package com.example.busschedulingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.nsd.NsdManager;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.common.api.internal.RegisterListenerMethod;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity implements View.OnClickListener {
    /*    Define Variables   */
    private Button signuptodashboard;
    private EditText signup_fullname, signup_mobilenumber, signup_email, signup_password, signup_confirmpassword;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        /*       Initialize and Call Functions       */
        signup_fullname = (EditText) findViewById(R.id.signup_fullname);
        signup_mobilenumber = (EditText) findViewById(R.id.signup_mobilenumber);
        signup_email = (EditText) findViewById(R.id.signup_email);
        signup_password = (EditText) findViewById(R.id.signup_password);
        signup_confirmpassword = (EditText) findViewById(R.id.signup_confirmpassword);

        signuptodashboard = (Button) findViewById(R.id.signuptodashboard);
        signuptodashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUpUser();
            }
        });


    }

    /*         Function Definition            */
    /*public void openDashboard() {
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
    }*/

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signup:
                signUpUser();
                break;
        }
    }

    private void signUpUser() {
        String fullname = signup_fullname.getText().toString().trim(); // GET USER INPUT
        String email = signup_email.getText().toString().trim();
        String mobilenumber = signup_mobilenumber.getText().toString().trim();
        String password = signup_password.getText().toString().trim();
        String confirmpassword = signup_confirmpassword.getText().toString().trim();

        if (fullname.isEmpty()) { // INFORM USER TO FILL UP
            signup_fullname.setError("Full name is required.");
            signup_fullname.requestFocus();
            return;
        }

        if (mobilenumber.isEmpty()) {
            signup_mobilenumber.setError("Mobile number is required.");
            signup_mobilenumber.requestFocus();
            return;
        }

        if (email.isEmpty()) {
            signup_email.setError("Email is required.");
            signup_email.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) { // EMAIL VALIDATOR
            signup_email.setError("Please provide valid email");
            signup_email.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            signup_password.setError("Password is required.");
            signup_password.requestFocus();
            return;
        }

        if (password.length() < 6) { // PASSWORD LENGTH VALIDATOR
            signup_password.setError("Minimum of 6 characters.");
            signup_password.requestFocus();
            return;
        }

        if (confirmpassword.isEmpty()) {
            signup_confirmpassword.setError("Confirm password is required.");
            signup_confirmpassword.requestFocus();
            return;
        }

        /*TODO: Add Strong Password and Password Viewer */

        if (!password.equals(confirmpassword)) {
            signup_confirmpassword.setError("Password did not match.");
            signup_confirmpassword.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password) // CHECK IF USER IS REGISTERED
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) { // IF REGISTERED SUCCESSFULLY
                            User user = new User(fullname, mobilenumber, email); // OBJECT THAT WILL BE SEND IN THE DB

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid()) // RETURN THE ID OF REGISTERED USER
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) { // IF USER IS REGISTERED, INFORM USER
                                        Toast.makeText(SignUp.this, "Register Successful.", Toast.LENGTH_LONG).show(); // USER MESSAGE
                                    } else { // IF USER REGISTRATION FAILED
                                        Toast.makeText(SignUp.this, "Failed to register, try again.", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        } else { // IF USER REGISTRATION FAILED
                            Toast.makeText(SignUp.this, "Failed to register, try again.", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}