package com.busschedulingap.busschedulingapp.user;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.busschedulingap.busschedulingapp.R;

public class User_Reserve_Seat extends AppCompatActivity {

    private ImageView backbtn;
    private TextView user_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_reserve_seat);

        backbtn = findViewById(R.id.backbtn);
        user_title = findViewById(R.id.user_title);
        user_title.setText("Reserve Seat");

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(User_Reserve_Seat.this, User_Bus_Information.class);
                startActivity(intent);
            }
        });
    }
}