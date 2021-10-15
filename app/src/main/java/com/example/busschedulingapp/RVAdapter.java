package com.example.busschedulingapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;

public class RVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context context;
    ArrayList<Bus> list = new ArrayList<>(); // List of Bus Items

    public RVAdapter(Context ctx) { // Constructor
        this.context = ctx;
    }

    public void setItems(ArrayList<Bus> bus) { // Set items in the Array List
        list.addAll(bus);
    }

    private AlertDialog.Builder dialogBuilder;
    private AlertDialog dialog;

    public static String stat = "true";
    public static boolean status = true;

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.availablebusitem, parent, false);
        return new BusVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        BusVH vh = (BusVH) holder;
        Bus bus = list.get(position);

        Placeholder editClicked = new Placeholder();
        int pos = position;
        vh.busname.setText(bus.getBusname());
        vh.availableseats.setText(bus.getAvailableseats());
        vh.deptime.setText(bus.getDeparturetime());
        vh.textoption.setOnClickListener(view -> {
            PopupMenu popupMenu = new PopupMenu(context, vh.textoption);
            popupMenu.inflate(R.menu.option_menu); // DISPLAY THE OPTION
            popupMenu.setOnMenuItemClickListener(item -> {
                switch (item.getItemId()) {
                    case R.id.menu_edit: // EDIT ITEM FROM BUS LIST
                        Placeholder.setEditClick(true);
                        stat = "true";
                        Intent intent = new Intent(context, ManageBuses.class);
                        intent.putExtra("EDIT", bus);
                        context.startActivity(intent); // Send data to manage buses
                        break;
                    case R.id.menu_delete: // DELETE ITEM FROM BUS LIST
                        BusFunctions busfunc = new BusFunctions();
                        busfunc.remove(bus.getKey()).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(context, "Record is remove", Toast.LENGTH_SHORT).show();
                                notifyItemRemoved(pos);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(context, "" + e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                        break;
                }
                return false;
            });
            popupMenu.show();
        });
        //stat = "false";
    }

    @Override
    public int getItemCount() { // Get the size of the list
        return list.size();
    }

    public static String getEditClick(){
        return stat;
    }
}
