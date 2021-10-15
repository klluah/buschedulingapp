package com.example.busschedulingapp.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.busschedulingapp.DashboardActivity;
import com.example.busschedulingapp.MainActivity;
import com.example.busschedulingapp.SignIn;
import com.example.busschedulingapp.User;
import com.example.busschedulingapp.databinding.FragmentProfileBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment {

    // VARIABLES TO ACCESS USER DATA FROM DB
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userID;

    Button logout;

    private ProfileViewModel profileViewModel;
    private FragmentProfileBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);

        final TextView textView = binding.profileName;
        profileViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        user = FirebaseAuth.getInstance().getCurrentUser(); // INITIALIZE USER VARIABLE
        reference = FirebaseDatabase.getInstance().getReference("Users"); // REFERENCE USER
        userID = user.getUid();

        final TextView fullnameTextView = (TextView) binding.profileName; // ACCESS TEXT VIEW
        final TextView emailTextView = (TextView) binding.email;
        final TextView mobilenumberTextView = (TextView) binding.mobileNumber;

        // GET AND SET DATA FROM DB
        reference.child(userID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) { // IF USER LOGGED IN
                User userProfile = snapshot.getValue(User.class);

                if (userProfile != null) {
                    String fullname = userProfile.fullname; // GET DATA FROM DB
                    String email = userProfile.email;
                    String mobilenumber = userProfile.mobilenumber;

                    fullnameTextView.setText(fullname); // SET DATA FROM DB
                    emailTextView.setText(email);
                    mobilenumberTextView.setText(mobilenumber);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) { // IF USER LOGGED IN
                //Toast.makeText(ProfileFragment.this, "Something wrong happened", Toast.LENGTH_LONG).show();
            }
        });

        final Button logout = (Button) binding.logoutbutton;
        logout.setOnClickListener(new View.OnClickListener() { // LOGOUT USER
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity().getApplicationContext(), SignIn.class);
                startActivity(intent);
            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}