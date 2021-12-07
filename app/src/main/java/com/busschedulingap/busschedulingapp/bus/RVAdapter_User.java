package com.busschedulingap.busschedulingapp.bus;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.busschedulingap.busschedulingapp.user.User_Bus_Information;
import com.busschedulingap.busschedulingapp.R;

import java.util.ArrayList;

public class RVAdapter_User extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    ArrayList<Bus> list = new ArrayList<>(); // List of Bus Items
    public RVAdapter_User(Context ctx) { // Constructor
        this.context = ctx;
    }
    public void setItems(ArrayList<Bus> bus) { // Set items in the Array List
        list.addAll(bus);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.availablebusitem_user, parent, false);
        return new BusVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        BusVH vh = (BusVH) holder;
        Bus bus = list.get(position);

        // Item OnClickListener
        ((BusVH) holder).v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, User_Bus_Information.class);
                intent.putExtra("BusKey", bus); // pass the id of item
                context.startActivity(intent);
            }
        });

        vh.busname.setText(bus.getBusname());
        if (bus.getBusname().equalsIgnoreCase("ALPS")) { vh.itemimg.setImageResource(R.drawable.alps); }
        else if (bus.getBusname().equalsIgnoreCase("DELAROSA")) { vh.itemimg.setImageResource(R.drawable.delarosa); }
        else if (bus.getBusname().equalsIgnoreCase("DLTBCO")) { vh.itemimg.setImageResource(R.drawable.dltbco); }
        else if (bus.getBusname().equalsIgnoreCase("JAM")) { vh.itemimg.setImageResource(R.drawable.jam); }
        else if (bus.getBusname().equalsIgnoreCase("JAPS")) { vh.itemimg.setImageResource(R.drawable.japs); }
        else if (bus.getBusname().equalsIgnoreCase("SUPREME")) { vh.itemimg.setImageResource(R.drawable.supreme); }
        vh.availableseats.setText(bus.getAvailableseats());
        vh.deptime.setText(bus.getDeparturetime());
    }

    @Override
    public int getItemCount() { // Get the size of the list
        return list.size();
    }
}
