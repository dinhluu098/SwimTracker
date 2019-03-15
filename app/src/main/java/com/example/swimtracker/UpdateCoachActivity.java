package com.example.swimtracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.text.SimpleDateFormat;

public class UpdateCoachActivity extends AppCompatActivity {

    Button btn_cancel, btn_agree;
    Spinner spn_gender;
    ImageButton calender;
    EditText datepiker, edt_address, edt_pnumber, edt_email,edt_dob;
    String URL_EDIT = URLLibrary.getURLMain() + "profile/<username>/edit";

    private UserProfile coach;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coach_info);
        init();
        action();
        loadData();
    }

    public void init() {
        //Network init
        AndroidNetworking.initialize(getApplicationContext());

        //Spinner
        String[] bankNames = {"Nam", "Nữ"};
        Spinner spin = (Spinner) findViewById(R.id.spn_gender);
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, bankNames);
        spin.setAdapter(aa);

        //Buttons
        btn_cancel = (Button) findViewById(R.id.btn_cancel);
        btn_agree = (Button) findViewById(R.id.btn_agree);

        //EditText
        datepiker = (EditText) findViewById(R.id.edt_dob);
        edt_address = (EditText) findViewById(R.id.dt_address);
        edt_pnumber = (EditText) findViewById(R.id.dt_pnumber);
        edt_email = (EditText) findViewById(R.id.dt_mail);
        edt_dob = (EditText) findViewById(R.id.dt_dob);

       //Others
        calender = (ImageButton) findViewById(R.id.img_calender);


    }

    public void loadData() {
        coach = UserProfile.getInstance();

        edt_address.setHint(coach.getUser().getAddress());
        edt_pnumber.setHint(coach.getUser().getPhone());
        edt_email.setHint(coach.getUser().getEmail());

        SimpleDateFormat date = new SimpleDateFormat("dd/MM/yyyy");
        String dob = date.format(Date.parse(coach.getUser().getDob()));

        edt_dob.setHint(dob);


    }

    public void action() {
        btn_agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update();
            }
        });

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               finish();
            }
        });
    }

    public void update() {
        if (!isValidForm()){
            Toast.makeText(this, "Kiểm tra lại thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        String address = edt_address.getText().toString();
        String pnumber = edt_pnumber.getText().toString();
        String email = edt_email.getText().toString();
        int gender = getGender();

        JSONObject result = convertToJSONObject(address,pnumber,gender,email);

        AndroidNetworking.post(URL_EDIT)
                .addJSONObjectBody(result)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getJSONObject("result").getString("status").equals("success")){
                                Toast.makeText(UpdateCoachActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(UpdateCoachActivity.this, MainActivity.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(UpdateCoachActivity.this, response.getString(""), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }

    public JSONObject convertToJSONObject (String address, String phone, int gender, String email) {
        JSONObject jsonObject = new JSONObject();
        JSONObject coach = new JSONObject();

        try{
            coach.put("address",address);
            coach.put("phone",phone);
            coach.put("email",email);
            coach.put("gender",gender);

            jsonObject.put("coach",coach);
        }catch (Exception e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    public boolean isValidForm(){
        return isValidAddress() && isValidEmail() && isValidPhone() ;
    }

    public boolean isValidEmail(){
        String email = edt_email.getText().toString();
        String pattern ="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (!email.matches(pattern))
            return false;
        return true;
    }

    public boolean isValidPhone(){
        return edt_pnumber.getText().toString().length() == 10;
    }

    public boolean isValidAddress(){
        String address = edt_address.getText().toString();
        String pattern = "[a-zA-Z0-9,/ ]*";
        if(address.matches(pattern) && address.length()<=100)
            return true;
        return false;
    }


    public int getGender () {
        String gender = spn_gender.getSelectedItem().toString();

        if(gender.equals("Nam"))
            return 1;
        return 0;
    }
}

