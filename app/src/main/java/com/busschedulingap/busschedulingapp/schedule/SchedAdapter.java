package com.busschedulingap.busschedulingapp.schedule;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.busschedulingap.busschedulingapp.R;
import com.busschedulingap.busschedulingapp.admin.ManageSchedule;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;

public class SchedAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    ArrayList<Schedule> list = new ArrayList<>(); // List of Bus Items
    public SchedAdapter(Context ctx) { // Constructor
        this.context = ctx;
    }
    public void setItems(ArrayList<Schedule> schedules) { // Set items in the Array List
        list.addAll(schedules);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.scheduleitem, parent, false);
        return new ScheduleVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ScheduleVH vh = (ScheduleVH) holder;
        Schedule schedule = list.get(position);
        int pos = position;

        vh.bus.setText(schedule.sbusname);
        vh.destination.setText(schedule.sdestination);
        vh.time.setText(schedule.stime);
        vh.fare.setText(schedule.sfare);
        vh.deletesched.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SchedFunctions schedFunctions = new SchedFunctions();
                schedFunctions.remove(schedule.getKey()).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(context, "Schedule removed.", Toast.LENGTH_SHORT).show();
                        notifyItemRemoved(pos);
                        Intent intent = new Intent(context, ManageSchedule.class);
                        context.startActivity(intent);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(context, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
