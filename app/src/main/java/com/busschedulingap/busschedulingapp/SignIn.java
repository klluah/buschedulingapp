package com.busschedulingap.busschedulingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.util.Patterns;

import com.busschedulingap.busschedulingapp.user.User;
import com.busschedulingap.busschedulingapp.R;
import com.busschedulingap.busschedulingapp.admin.ManageSchedule;
import com.busschedulingap.busschedulingapp.user.User_Dashboard;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.auth.FirebaseUser;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignIn extends AppCompatActivity implements View.OnClickListener {

    private CallbackManager mCallbackManager;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private Button signintodashboard ,popup_save;
    private TextView signintosignup;
    private TextView textViewUser;
    private ImageView mLogo;
    private LoginButton loginButton;
    private AccessTokenTracker accessTokenTracker;
    private AccessToken accessToken;
    private static final String TAG = "FacebookAuthentication";

    private EditText signin_email;
    private EditText signin_password;
    private FirebaseAuth mAuth;

    // GOOGLE SIGN IN
    private GoogleSignInClient mGoogleSignInClient;
    private final static int RC_SIGN_IN = 123;

    ImageView signintwitter;
    ImageView signingoogle;
    ImageView signinfacebook;

    private DatabaseReference adminReference;
    private DatabaseReference adminIdReference;
    private DatabaseReference adminEmailReference;
    private String userId;
    private String userEmail;

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;


    // ------------------------------------- CONSTRUCTOR ------------------------------------- //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        // ------ MANUAL INITIALIZERS ------ //
        mAuth = FirebaseAuth.getInstance();

        signintodashboard = (Button) findViewById(R.id.signintodashboard);
        signintodashboard.setOnClickListener(this);

        signintosignup = (TextView) findViewById(R.id.signintosignup);
        signintosignup.setOnClickListener(this);

        signin_email = (EditText) findViewById(R.id.signin_email);
        signin_password = (EditText) findViewById(R.id.signin_password);


        adminReference = FirebaseDatabase.getInstance().getReference().child("Admin"); // access admin from db
        adminIdReference = adminReference.child("LnmMZQk0vndCgpgsVFjbjgJ7MPj3"); // access admin id from db
        adminEmailReference = adminIdReference.child("email"); // access email from db

        //Picasso.get().load()
        // ------ GOOGLE INITIALIZERS ------ //

        signingoogle = findViewById(R.id.google_signin);

        signingoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignIn.this, GoogleActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });

        createRequest();

        signingoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn(); // OPEN SIGN METHOD WHEN BUTTON CLICKED
            }
        });

        /*// ------ FACEBOOK INITIALIZERS  ------ //

        signinfacebook = findViewById(R.id.facebook_signin);
        signinfacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignIn.this, FacebookActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });*/

        // ------ TWITTER INITIALIZERS ------ //
        signintwitter = findViewById(R.id.twittersignin);

        signintwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignIn.this, TwitterActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            }
        });
    }


    // --------------------------------   MANUAL USER LOGIC  --------------------------------- //
    private void userLogin() {
        String email = signin_email.getText().toString().trim();
        String password = signin_password.getText().toString().trim();

        if (email.isEmpty()) {
            signin_email.setError("Email is required.");
            signin_email.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            signin_email.setError("Please enter valid email.");
            signin_email.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            signin_password.setError("Password is required.");
            signin_password.requestFocus();
            return;
        }

        if (password.length() < 6) { // PASSWORD LENGTH VALIDATOR
            signin_password.setError("Minimum of 6 characters.");
            signin_password.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if (task.isSuccessful()) { // IF SUCCESSFUL THEN MOVE TO DASHBOARD
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    // Get Current User ID and Email
                    for (UserInfo profile : user.getProviderData()) {
                        userId = profile.getProviderId();
                        userEmail = profile.getEmail();
                    }

                    if (((user.isEmailVerified()) && (!(userId.equalsIgnoreCase(adminEmailReference.toString())))) && (!(userEmail.equalsIgnoreCase("busschedulingapp@gmail.com")))) {
                        // CHECK IF USER EMAIL IS VERIFIED AND NOT ADMIN
                        //openManageSchedule();
                        openUserDashboard();
                        Toast.makeText(SignIn.this, "User Login Successful", Toast.LENGTH_SHORT).show();
                        String Uid = String.valueOf(FirebaseAuth.getInstance().getCurrentUser().getUid()); //get current user uid
                        //Toast.makeText(SignIn.this, Uid, Toast.LENGTH_SHORT).show();
                    } else if ((user.isEmailVerified()) && (userEmail.equalsIgnoreCase("busschedulingapp@gmail.com"))) {
                        // ((userId.equalsIgnoreCase("LnmMZQk0vndCgpgsVFjbjgJ7MPj3")) || (userId.equalsIgnoreCase("xn9dlzs1ashAmdNDKYG0iO6mpVj2")))
                        // if user id and email matches admin then display admin pages
                        openManageSchedule();
                        Toast.makeText(SignIn.this, "Admin Login Successful", Toast.LENGTH_SHORT).show();
                    } else { // IF NOT THEN SEND VERIFICATION LINK
                        user.sendEmailVerification();
                        Toast.makeText(SignIn.this, "Check your email to verify your account.", Toast.LENGTH_LONG).show();
                    }
                } else { // IF NOT SUCCESSFUL THEN INFORM USER
                    Toast.makeText(SignIn.this, "Failed to login, please check your credentials", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signintosignup:
                startActivity(new Intent(this, SignUp.class));
                break;

            case R.id.signintodashboard:
                userLogin();
                break;
        }
    }

    public void openUserDashboard() {
        Intent intent = new Intent(this, User_Dashboard.class);
        startActivity(intent);
    }

    public void openManageSchedule() {
        Intent intent = new Intent(this, ManageSchedule.class);
        startActivity(intent);
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
                                Intent intent = new Intent(SignIn.this, User_Dashboard.class);
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
        dialogBuilder = new AlertDialog.Builder(this); // create dialog
        final View adduserinfopopup = getLayoutInflater().inflate(R.layout.userinfopopup, null);

        EditText name, email, number, birthdate, password, confirm_password;
        // Assign the Value of EditText
        name = (EditText) adduserinfopopup.findViewById(R.id.popup_name);
        email = (EditText) adduserinfopopup.findViewById(R.id.popup_email);
        number = (EditText) adduserinfopopup.findViewById(R.id.popup_number);
        birthdate = (EditText) adduserinfopopup.findViewById(R.id.popup_birthdate);
        //password = adduserinfopopup.findViewById(R.id.popup_password);
        //confirm_password = adduserinfopopup.findViewById(R.id.popup_confirmpassword);
        popup_save = (Button) adduserinfopopup.findViewById(R.id.popup_save);

        dialogBuilder.setView(adduserinfopopup);

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
                            Toast.makeText(SignIn.this, "Register Successful.", Toast.LENGTH_LONG).show(); // USER MESSAGE
                            Intent intent = new Intent(SignIn.this, User_Dashboard.class);
                            startActivity(intent);
                        } else { // IF USER REGISTRATION FAILED
                            Toast.makeText(SignIn.this, "Failed to register, try again.", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });
    }
}