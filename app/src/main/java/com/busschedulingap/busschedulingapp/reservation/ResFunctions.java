package com.busschedulingap.busschedulingapp.reservation;

import com.busschedulingap.busschedulingapp.Placeholder;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class ResFunctions {
    private DatabaseReference databaseReference;

    public ResFunctions(int num) {
        FirebaseDatabase db = FirebaseDatabase.getInstance();

        if(num == 1){ // if parameter is 1 then set reference to Pending
            databaseReference = db.getReference(Reservation.class.getSimpleName()).child(Placeholder.currentdate).child("Pending");
        } else if(num == 2){ // if parameter is 1 then set reference to Successful
            databaseReference = db.getReference(Reservation.class.getSimpleName()).child(Placeholder.currentdate).child("Successful");
        }
        /*else if(num == 1){ // if parameter is 1 then set reference to Pending
            databaseReference = db.getReference(Reservation.class.getSimpleName()).child(Placeholder.currentdate).child("Pending");
        } else if(num == 2){ // if parameter is 1 then set reference to Successful
            databaseReference = db.getReference(Reservation.class.getSimpleName()).child(Placeholder.currentdate).child("Successful");
        }*/
    }

    public Task<Void> add(Reservation res) {
        return databaseReference.push().setValue(res);  // generate unique key
    }

    public Query get() {
        return databaseReference.orderByKey();
    } // get data from db

    public Task<Void> update(HashMap<String, Object> hashMap) { // update child
        return databaseReference.updateChildren(hashMap);
    }

    public void addValueEventListener(ValueEventListener valueEventListener) {
    }
}
