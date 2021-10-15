// BUS VIEW HOLDER CLASS

package com.example.busschedulingapp;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BusVH extends RecyclerView.ViewHolder {

    public TextView busname, availableseats, deptime, textoption;

    public BusVH(@NonNull View itemView) {
        super(itemView);
        busname = itemView.findViewById(R.id.busname);
        availableseats = itemView.findViewById(R.id.availableseats);
        deptime = itemView.findViewById(R.id.departuretime);
        textoption = itemView.findViewById(R.id.text_option);
    }


}
