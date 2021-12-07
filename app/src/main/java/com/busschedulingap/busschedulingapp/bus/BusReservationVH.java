package com.busschedulingap.busschedulingapp.bus;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.busschedulingap.busschedulingapp.R;

public class BusReservationVH extends RecyclerView.ViewHolder {

    public TextView view_btn, ritemnoofres, ritemplatenumber, ritemdestination,
                    ritemdeptime, ritemarrivaltime, ritemdriver, ritemfare;
    View vh;

    public BusReservationVH(@NonNull View itemView) {
        super(itemView);
        view_btn = itemView.findViewById(R.id.view_btn);
        ritemnoofres = itemView.findViewById(R.id.ritenumberofreservation);
        ritemplatenumber = itemView.findViewById(R.id.ritemplatenumber);
        ritemdestination = itemView.findViewById(R.id.ritemdestination);
        ritemdeptime = itemView.findViewById(R.id.ritemdeptime);
        ritemarrivaltime = itemView.findViewById(R.id.ritemarrivaltime);
        ritemdriver = itemView.findViewById(R.id.irdriver);
        ritemfare = itemView.findViewById(R.id.irfare);
        vh = itemView;
    }
}
