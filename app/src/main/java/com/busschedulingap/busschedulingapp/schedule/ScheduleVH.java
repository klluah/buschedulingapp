package com.busschedulingap.busschedulingapp.schedule;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.busschedulingap.busschedulingapp.R;

public class ScheduleVH extends RecyclerView.ViewHolder {
    public TextView bus, destination, time, fare;
    public ImageView deletesched;

    View vi;

    public ScheduleVH(@NonNull View itemView) {
        super(itemView);
        bus = itemView.findViewById(R.id.scheditem1);
        destination = itemView.findViewById(R.id.scheditem2);
        time = itemView.findViewById(R.id.scheditem3);
        fare = itemView.findViewById(R.id.scheditem4);
        deletesched = itemView.findViewById(R.id.deletesched);
        vi = itemView;
    }
}
