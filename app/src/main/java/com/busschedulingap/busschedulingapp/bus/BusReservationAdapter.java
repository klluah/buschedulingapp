package com.busschedulingap.busschedulingapp.bus;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.busschedulingap.busschedulingapp.admin.ManageReservation_Individual;
import com.busschedulingap.busschedulingapp.Placeholder;
import com.busschedulingap.busschedulingapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class BusReservationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    ArrayList<Bus> list = new ArrayList<>(); // List of Bus Items
    public BusReservationAdapter(Context ctx) { // Constructor
        this.context = ctx;
    }
    public void setItems(ArrayList<Bus> bus) { // Set items in the Array List
        list.addAll(bus);
    }
    public static String stat = "true";
    public static String busname;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.reservation_busitem, parent, false);
        return new BusReservationVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        BusReservationVH vh = (BusReservationVH) holder;
        Bus bus = list.get(position);
        int pos = position;


        // Item OnClickListener
        ((BusReservationVH) holder).vh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ManageReservation_Individual.class);
                intent.putExtra("Buskey", bus); // pass the id of item
                Placeholder.cbuskey = bus.key;
                context.startActivity(intent);
            }
        });

        // count the total number of reservation per bus
        // query to check if platenumber exist in reservations, if yes then count the occurrence
        Query qresnumber = FirebaseDatabase.getInstance().getReference("Reservation")
                .child(Placeholder.currentdate).child("Pending").orderByChild("rplatenumber").equalTo(bus.getPlatenumber());
        qresnumber.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    int resnum = (int) snapshot.getChildrenCount();
                    vh.ritemnoofres.setText(String.valueOf(resnum));
                } else {
                    vh.ritemnoofres.setText(String.valueOf(0));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });



        vh.view_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ManageReservation_Individual.class);
                Placeholder.cbuskey = bus.key;
                context.startActivity(intent);
            }
        });

        vh.ritemplatenumber.setText(bus.getPlatenumber());
        vh.ritemdestination.setText(bus.getDestination());
        vh.ritemdeptime.setText(bus.getDeparturetime());
        vh.ritemarrivaltime.setText(bus.getArrivaltime());
        vh.ritemdriver.setText(bus.getDriver());
        vh.ritemfare.setText(bus.getFare());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
