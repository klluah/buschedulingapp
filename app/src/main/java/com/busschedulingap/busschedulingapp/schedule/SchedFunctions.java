package com.busschedulingap.busschedulingapp.schedule;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.HashMap;

public class SchedFunctions {
    private DatabaseReference databaseReference;

    public SchedFunctions() {
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        databaseReference = db.getReference(Schedule.class.getSimpleName());
    }

    public Task<Void> add(Schedule schedule) {
        return databaseReference.push().setValue(schedule); // generate unique key
    }

    public Task<Void> update(String key, HashMap<String, Object> hashMap) { // update child
        return databaseReference.child(key).updateChildren(hashMap);
    }

    public Task<Void> remove(String key) { // delete child
        return databaseReference.child(key).removeValue();
    }

    public Query get() {
        return databaseReference.orderByKey();
    } // get data from db
}
