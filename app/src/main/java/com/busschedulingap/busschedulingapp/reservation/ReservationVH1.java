package com.busschedulingap.busschedulingapp.reservation;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.busschedulingap.busschedulingapp.R;

public class ReservationVH1 extends RecyclerView.ViewHolder {

    public TextView busname, deptime, destination, platenumber, resid, date;
    View vi;

    public ReservationVH1(@NonNull View itemView) {
        super(itemView);

        busname= itemView.findViewById(R.id.rbusname1);
        deptime= itemView.findViewById(R.id.rdeparturetime1);
        destination= itemView.findViewById(R.id.rdestination1);
        platenumber= itemView.findViewById(R.id.rplatenumber1);
        resid= itemView.findViewById(R.id.rresid1);
        date = itemView.findViewById(R.id.reservedate1);
        vi = itemView;
    }
}
