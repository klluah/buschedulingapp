// BUS VIEW HOLDER CLASS

package com.busschedulingap.busschedulingapp.bus;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.busschedulingap.busschedulingapp.R;

public class BusVH extends RecyclerView.ViewHolder {

    public ImageView itemimg;
    public TextView busname, availableseats, deptime, textoption;
    View v;

    public BusVH(@NonNull View itemView) {
        super(itemView);

        itemimg = itemView.findViewById(R.id.itemimg);
        busname = itemView.findViewById(R.id.rbusname);
        availableseats = itemView.findViewById(R.id.rdestination);
        deptime = itemView.findViewById(R.id.rdeparturetime);
        textoption = itemView.findViewById(R.id.text_option);
        v = itemView;
    }
}
