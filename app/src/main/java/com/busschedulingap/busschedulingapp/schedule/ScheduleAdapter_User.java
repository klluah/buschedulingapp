package com.busschedulingap.busschedulingapp.schedule;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.busschedulingap.busschedulingapp.R;

import java.util.ArrayList;

public class ScheduleAdapter_User extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private Context context;
    ArrayList<Schedule> list = new ArrayList<>(); // List of Bus Items
    public ScheduleAdapter_User(Context ctx) { // Constructor
        this.context = ctx;
    }
    public void setItems(ArrayList<Schedule> schedules) { // Set items in the Array List
        list.addAll(schedules);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.scheduleitem_user, parent, false);
        return new ScheduleVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ScheduleVH vh = (ScheduleVH) holder;
        Schedule schedule = list.get(position);

        vh.bus.setText(schedule.sbusname);
        vh.destination.setText(schedule.sdestination);
        vh.time.setText(schedule.stime);
        vh.fare.setText(schedule.sfare);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
