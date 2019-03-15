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

public class SwimmerViewInfo extends AppCompatActivity {


    Button btn_update;
    TextView tv_name,tv_address,tv_pnumber,tv_email,tv_dob,tv_gender,tv_parent_name,tv_parent_pnumber,tv_height,tv_weight;


    private UserProfile swimmer;



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
        /*tv_gender = (TextView) findViewById(R.id.tv_gender);
        tv_parent_name = (TextView) findViewById(R.id.tv_parent_name);
        tv_parent_pnumber = (TextView) findViewById(R.id.tv_parent_pnumber);
        tv_height = (TextView) findViewById(R.id.tv_height);
        tv_weight = (TextView) findViewById(R.id.tv_weight);*/
    }

    public void action() {
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SwimmerViewInfo.this, UpdateSwimmerActivity.class);
                startActivity(intent);
            }
        });
    }

    public void displayData () {
        swimmer = UserProfile.getInstance();
       User user = new Swimmer();


        String name = new StringBuilder().append(swimmer.getUser().getLast_name()).append(swimmer.getUser().getFirst_name()).toString();
        tv_name.setText(name);
        tv_address.setText(swimmer.getUser().getAddress());
        tv_pnumber.setText(swimmer.getUser().getPhone());
        tv_email.setText(swimmer.getUser().getEmail());
        /*tv_parent_name.setText(swimmer.getUser().getParent_name());
        tv_parent_pnumber.setText(swimmer.getUser().getParent_phone());
        tv_height.setText(""+swimmer.getUser().getHeight());
        tv_weight.setText(""+swimmer.getUser().getWeight());*/

        SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
        String dob = date.format(Date.parse(swimmer.getUser().getDob()));

        tv_dob.setText(dob);

        /*String gender_id = String.valueOf(swimmer.getUser().getGender());

        if(gender_id == "1") {
            tv_gender.setText("Nam");
        }
        else {
            tv_gender.setText("Nữ");
        }*/
    }
}
