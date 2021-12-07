package com.busschedulingap.busschedulingapp.reservation;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.busschedulingap.busschedulingapp.Placeholder;
import com.busschedulingap.busschedulingapp.R;
import com.busschedulingap.busschedulingapp.admin.Reservation_Busname;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class IndividualReservationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    ArrayList<Reservation> list = new ArrayList<>(); // List of Bus Items
    public IndividualReservationAdapter(Context ctx) { // Constructor
        this.context = ctx;
    }
    public void setItems(ArrayList<Reservation> reservations) { // Set items in the Array List
        list.addAll(reservations);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.reservation_individualbusitem, parent, false);
        return new IndividualReservationVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        IndividualReservationVH vh = (IndividualReservationVH) holder;
        Reservation reservation = list.get(position);
        int pos = position;

        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference pendingref = db.getReference(Reservation.class.getSimpleName())
                .child(Placeholder.currentdate).child("Pending").child(reservation.key);
        DatabaseReference successref = db.getReference(Reservation.class.getSimpleName())
                .child(Placeholder.currentdate).child("Successful").child(reservation.key);

        vh.confirmres_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pendingref.child("rstatus").setValue("Successful");

                // Update Available Seats when user reservation is successful
                DatabaseReference availseatref = FirebaseDatabase.getInstance().getReference().child("Bus")
                        .child(Placeholder.currentdate).child(Placeholder.cbuskey);
                availseatref.child("availableseats").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if(Placeholder.availseatsChanged){
                            int currentavailseats = Integer.parseInt(snapshot.getValue(String.class));
                            currentavailseats = currentavailseats-1;
                            Toast.makeText(context, String.valueOf(currentavailseats), Toast.LENGTH_LONG).show();
                            availseatref.child("availableseats").setValue(String.valueOf(currentavailseats));
                            Placeholder.availseatsChanged = false;
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) { }
                });

                movependingtosuccess(pendingref, successref);
                removekey(pendingref, successref);

                Intent intent = new Intent(context, Reservation_Busname.class);
                context.startActivity(intent);
            }
        });

        vh.resid.setText(reservation.rresid);
        vh.name.setText(reservation.rname);
        vh.number.setText(reservation.rnumber);
        vh.destination.setText(reservation.rdestination);
        vh.deptime.setText(reservation.rdeptime);
        vh.driver.setText(reservation.rdriver);
        vh.fare.setText(reservation.rfare);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    // Move Peding child to Successful child
    private void movependingtosuccess(final DatabaseReference fromPath, final DatabaseReference toPath) {
        fromPath.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                toPath.setValue(dataSnapshot.getValue(), new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError firebaseError, DatabaseReference firebase) {
                        if (firebaseError != null) {
                            System.out.println("Copy failed");
                        } else {
                            System.out.println("Success");
                        }
                    }
                });
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    // Remove the current reservation
    private void removekey(final DatabaseReference fromPath, final DatabaseReference toPath) {
        fromPath.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                toPath.setValue(dataSnapshot.getValue(), new DatabaseReference.CompletionListener() {
                    @Override
                    public void onComplete(DatabaseError firebaseError, DatabaseReference firebase) {
                        if (firebaseError != null) {
                            System.out.println("Copy failed");
                        } else {
                            fromPath.setValue(null);
                            System.out.println("Success");
                        }
                    }
                });
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }
}
