package com.busschedulingap.busschedulingapp.reservation;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.busschedulingap.busschedulingapp.R;

public class IndividualReservationVH extends RecyclerView.ViewHolder {

    public TextView confirmres_btn, resid, name, number, destination, deptime, driver, fare;
    View vi;

    public IndividualReservationVH(@NonNull View itemView) {
        super(itemView);

        confirmres_btn = itemView.findViewById(R.id.confirmres_btn);
        resid = itemView.findViewById(R.id.irresid);
        name = itemView.findViewById(R.id.irfullname);
        number = itemView.findViewById(R.id.irnumber);
        destination = itemView.findViewById(R.id.irdestination);
        deptime = itemView.findViewById(R.id.irdeptime);
        driver = itemView.findViewById(R.id.irdriver);
        fare = itemView.findViewById(R.id.irfare);
        vi = itemView;
    }
}
