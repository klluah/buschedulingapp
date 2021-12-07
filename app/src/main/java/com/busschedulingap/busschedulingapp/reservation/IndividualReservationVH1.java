package com.busschedulingap.busschedulingapp.reservation;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.busschedulingap.busschedulingapp.R;

public class IndividualReservationVH1 extends RecyclerView.ViewHolder  {
    public TextView resid, name, number, destination, deptime, driver, fare;
    View vi;

    public IndividualReservationVH1(@NonNull View itemView) {
        super(itemView);

        resid = itemView.findViewById(R.id.irresid1);
        name = itemView.findViewById(R.id.irfullname1);
        number = itemView.findViewById(R.id.irnumber1);
        destination = itemView.findViewById(R.id.irdestination1);
        deptime = itemView.findViewById(R.id.irdeptime1);
        driver = itemView.findViewById(R.id.irdriver1);
        fare = itemView.findViewById(R.id.irfare1);
        vi = itemView;
    }
}
