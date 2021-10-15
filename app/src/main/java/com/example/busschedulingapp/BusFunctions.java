package com.example.busschedulingapp;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.HashMap;

public class BusFunctions {

    private DatabaseReference databaseReference;

    public BusFunctions() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference(Bus.class.getSimpleName());
    }

    public Task<Void> add(Bus bus) {
        return databaseReference.push().setValue(bus); // generate unique key
    }

    public Task<Void> update(String key, HashMap<String, Object> hashMap) { // update child
        return databaseReference.child(key).updateChildren(hashMap);
    }

    public Task<Void> remove(String key) { // delete child
        return databaseReference.child(key).removeValue();
    }

    public Query get() {
        return databaseReference.orderByKey();
    }
}
