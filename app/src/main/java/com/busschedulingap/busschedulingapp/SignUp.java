package com.busschedulingap.busschedulingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.busschedulingap.busschedulingapp.user.User;
import com.busschedulingap.busschedulingapp.R;
import com.busschedulingap.busschedulingapp.user.User_Dashboard;
import com.facebook.CallbackManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity implements View.OnClickListener {
    /*    Define Variables   */
    private Button signuptodashboard;
    private EditText signup_fullname, signup_mobilenumber, signup_email, signup_birthdate, signup_password, signup_confirmpassword;
    private ImageView gg_signin, tt_signin;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    private final static int RC_SIGN_IN = 123;
    private static final String TAG = "FacebookAuthentication";
    private CallbackManager mCallbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth = FirebaseAuth.getInstance();

        /*       Initialize and Call Functions       */
        signup_fullname = (EditText) findViewById(R.id.popup_name);
        signup_mobilenumber = (EditText) findViewById(R.id.popup_number);
        signup_email = (EditText) findViewById(R.id.popup_email);
        signup_birthdate = (EditText) findViewById(R.id.popup_birthdate);
        signup_password = (EditText) findViewById(R.id.signup_password);
        signup_confirmpassword = (EditText) findViewById(R.id.signup_confirmpassword);

        signuptodashboard = (Button) findViewById(R.id.popup_save);
        signuptodashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUpUser();
            }
        });

        gg_signin = findViewById(R.id.gg_signin);
        tt_signin = findViewById(R.id.tt_signin);


        gg_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUp.this, GoogleActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });

        createRequest();

        gg_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn(); // OPEN SIGN METHOD WHEN BUTTON CLICKED
            }
        });

        tt_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUp.this, TwitterActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });
    }

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
        String birthdate = signup_birthdate.getText().toString().trim();
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

        if (birthdate.isEmpty()) {
            signup_birthdate.setError("Email is required.");
            signup_birthdate.requestFocus();
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

        // Save the user data to Database
        mAuth.createUserWithEmailAndPassword(email, password) // CHECK IF USER IS REGISTERED
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) { // IF REGISTERED SUCCESSFULLY
                            User user = new User(fullname, mobilenumber, email, birthdate); // OBJECT THAT WILL BE SEND IN THE DB

                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid()) // RETURN THE ID OF REGISTERED USER
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) { // IF USER IS REGISTERED, INFORM USER
                                        Toast.makeText(SignUp.this, "Register Successful.", Toast.LENGTH_LONG).show(); // USER MESSAGE
                                        Intent intent = new Intent(SignUp.this, SignIn.class);
                                        startActivity(intent);
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


    // --------------------------------   GOOGLE METHODS START  --------------------------------- //
    // CREATE REQUEST
    private void createRequest() {
        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_clientid))
                .requestEmail()
                .build();

        // HANDLE THE REQUEST TO GOOGLE SIGN IN CLIENT
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
    }

    // CALLED WHEN USER CLICK ON SIGN IN BUTTON
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    // GOOGLE OR FB RECEIVE DATA FROM THE USER
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
            }
        } else {
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    // SUCCESSFUL LOGIN, PROCEED
    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            boolean isNew = task.getResult().getAdditionalUserInfo().isNewUser();
                            if(isNew){
                                createUserInfoDialog();
                            } else {
                                Intent intent = new Intent(SignUp.this, User_Dashboard.class);
                                startActivity(intent);
                            }
                            // popup menu to enter the info (name, email, number, birthdate)
                            // Save user info to User Realtime Database including (uid, name, email, number, birthdate)


                            //updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                        }
                    }
                });
    }

    private void createUserInfoDialog() {
        //Toast.makeText(this, "Pop Up Successful", Toast.LENGTH_LONG).show();
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this); // create dialog
        final View adduserinfopopup = getLayoutInflater().inflate(R.layout.userinfopopup, null);

        EditText name, email, number, birthdate, password, confirm_password;
        // Assign the Value of EditText
        name = (EditText) adduserinfopopup.findViewById(R.id.popup_name);
        email = (EditText) adduserinfopopup.findViewById(R.id.popup_email);
        number = (EditText) adduserinfopopup.findViewById(R.id.popup_number);
        birthdate = (EditText) adduserinfopopup.findViewById(R.id.popup_birthdate);
        //password = adduserinfopopup.findViewById(R.id.popup_password);
        //confirm_password = adduserinfopopup.findViewById(R.id.popup_confirmpassword);
        Button popup_save = (Button) adduserinfopopup.findViewById(R.id.popup_save);

        dialogBuilder.setView(adduserinfopopup);

        AlertDialog dialog = dialogBuilder.create();
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
                            Toast.makeText(SignUp.this, "Register Successful.", Toast.LENGTH_LONG).show(); // USER MESSAGE
                            Intent intent = new Intent(SignUp.this, User_Dashboard.class);
                            startActivity(intent);
                        } else { // IF USER REGISTRATION FAILED
                            Toast.makeText(SignUp.this, "Failed to register, try again.", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }
}