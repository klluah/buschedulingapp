package com.busschedulingap.busschedulingapp.reservation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.busschedulingap.busschedulingapp.Placeholder;
import com.busschedulingap.busschedulingapp.R;

import java.util.ArrayList;

public class RESAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    ArrayList<Reservation> list = new ArrayList<>(); // List of Bus Items
    public RESAdapter(Context ctx) { // Constructor
        this.context = ctx;
    }
    public void setItems(ArrayList<Reservation> reservations) { // Set items in the Array List
        list.addAll(reservations);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.reservationitem, parent, false);
        return new ReservationVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ReservationVH vh = (ReservationVH) holder;
        Reservation reservation = list.get(position);

        if (reservation.rstatus.equalsIgnoreCase("Pending")) { vh.statusimg.setImageResource(R.drawable.pending); }
        else if (reservation.rstatus.equalsIgnoreCase("Successful")) { vh.statusimg.setImageResource(R.drawable.successful); }
        vh.status.setText(reservation.rstatus);
        vh.busname.setText(reservation.rbusname);
        vh.deptime.setText(reservation.rdeptime);
        vh.destination.setText(reservation.rdestination);
        vh.platenumber.setText(reservation.rplatenumber);
        vh.resid.setText(reservation.rresid);
        vh.date.setText(Placeholder.currentdate);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
