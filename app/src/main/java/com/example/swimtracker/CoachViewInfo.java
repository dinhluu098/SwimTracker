package com.example.swimtracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;

import java.sql.Date;
import java.text.SimpleDateFormat;


public class CoachViewInfo extends AppCompatActivity {


    Button btn_update;
    TextView tv_name,tv_address,tv_pnumber,tv_email,tv_dob,tv_gender;


    private UserProfile coach;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.coach_view_info);
        init();
        action();
        displayData();
    }

    public void init() {
        //Network init
        AndroidNetworking.initialize(getApplicationContext());

        //Buttons
        btn_update = (Button) findViewById(R.id.btn_update);

        //Text View
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_address = (TextView) findViewById(R.id.tv_address);
        tv_pnumber = (TextView) findViewById(R.id.tv_pnumber);
        tv_email = (TextView) findViewById(R.id.tv_email);
        tv_dob = (TextView) findViewById(R.id.tv_dob);

    }

    public void action() {
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CoachViewInfo.this, UpdateCoachActivity.class);
                startActivity(intent);
            }
        });
    }

    public void displayData () {
        coach = UserProfile.getInstance();

        String name = new StringBuilder().append(coach.getUser().getLast_name()).append(coach.getUser().getFirst_name()).toString();
       tv_name.setText(name);
        tv_address.setText(coach.getUser().getAddress());
        tv_pnumber.setText(coach.getUser().getPhone());
        tv_email.setText(coach.getUser().getEmail());


        SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
        String dob = date.format(Date.parse(coach.getUser().getDob()));

        tv_dob.setText(dob);

        /*String gender_id = String.valueOf(coach.getUser().getGender());

        if(gender_id == "1") {
            tv_gender.setText("Nam");
        }
        else {
            tv_gender.setText("Nữ");
        }*/
    }

}

