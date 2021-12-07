package com.busschedulingap.busschedulingapp.reservation;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.busschedulingap.busschedulingapp.R;

public class ReservationVH extends RecyclerView.ViewHolder {

    public TextView status, busname, deptime, destination, platenumber, resid, date;
    public ImageView statusimg;
    View vi;

    public ReservationVH(@NonNull View itemView) {
        super(itemView);

        statusimg = itemView.findViewById(R.id.rstatusimg);
        status = itemView.findViewById(R.id.rstatus);
        busname= itemView.findViewById(R.id.rbusname);
        deptime= itemView.findViewById(R.id.rdeparturetime);
        destination= itemView.findViewById(R.id.rdestination);
        platenumber= itemView.findViewById(R.id.rplatenumber);
        resid= itemView.findViewById(R.id.rresid);
        date = itemView.findViewById(R.id.reservedate);
        vi = itemView;
    }
}