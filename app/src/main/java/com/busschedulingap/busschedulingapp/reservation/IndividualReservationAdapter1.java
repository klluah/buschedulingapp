package com.busschedulingap.busschedulingapp.reservation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.busschedulingap.busschedulingapp.R;
import java.util.ArrayList;

public class IndividualReservationAdapter1 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    ArrayList<Reservation> list = new ArrayList<>(); // List of Bus Items
    public IndividualReservationAdapter1(Context ctx) { // Constructor
        this.context = ctx;
    }
    public void setItems(ArrayList<Reservation> reservations) { // Set items in the Array List
        list.addAll(reservations);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.reservation_individualbusitem_successful, parent, false);
        return new IndividualReservationVH1(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        IndividualReservationVH1 vh = (IndividualReservationVH1) holder;
        Reservation reservation = list.get(position);

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
}
