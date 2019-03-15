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

public class UpdateSwimmerActivity extends AppCompatActivity {

    Spinner spn_gender;
    Button btn_cancel, btn_agree;
    ImageButton calender;
    EditText datepiker, edt_address, edt_pnumber, edt_email,edt_parent_name,edt_parent_pnumber;
    String URL_EDIT = URLLibrary.getURLMain() + "profile/<username>/edit";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_swimmer_info);
        init();
        action();
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
        datepiker.setEnabled(false);
        edt_address = (EditText) findViewById(R.id.dt_address);
        edt_pnumber = (EditText) findViewById(R.id.dt_pnumber);
        edt_email = (EditText) findViewById(R.id.dt_mail);
        edt_parent_name = (EditText) findViewById(R.id.dt_parent_name);
        edt_parent_pnumber = (EditText) findViewById(R.id.dt_parent_pnumber);

        //Others
        calender = (ImageButton) findViewById(R.id.img_calender);
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
        String parent_name = edt_parent_name.getText().toString();
        String parent_pnumber = edt_parent_pnumber.getText().toString();
        int gender  = getGender();

       JSONObject result =  convertToJSONObject(address,pnumber,gender,email,parent_name,parent_pnumber);

       AndroidNetworking.post(URL_EDIT)
                .addJSONObjectBody(result)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getJSONObject("result").getString("status").equals("success")){
                                Toast.makeText(UpdateSwimmerActivity.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(UpdateSwimmerActivity.this, MainActivity.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(UpdateSwimmerActivity.this, response.getString(""), Toast.LENGTH_SHORT).show();
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

    public JSONObject convertToJSONObject (String address, String phone, int gender, String email, String parent_name, String parent_pnumber) {
        JSONObject jsonObject = new JSONObject();
        JSONObject swimmer = new JSONObject();

        try{
            swimmer.put("address",address);
            swimmer.put("phone",phone);
            swimmer.put("email",email);
            swimmer.put("gender",gender);

            jsonObject.put("swimmer",swimmer);
        }catch (Exception e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    public boolean isValidForm(){
        return isValidAddress() && isValidEmail() && isValidPhone(edt_pnumber) && isValidPhone(edt_parent_pnumber)  && isValidName(edt_parent_name) ;
    }

    public boolean isValidEmail(){
        String email = edt_email.getText().toString();
        String pattern ="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (!email.matches(pattern))
            return false;
        return true;
    }

    public boolean isValidPhone(EditText editText){
        String pnumber = edt_pnumber.getText().toString();

        if(pnumber.length() == 10)
            return true;
        return false;

    }

    public boolean isValidAddress(){
        String address = edt_address.getText().toString();
        String pattern = "[a-zA-Z0-9,/ ]*";
        if(address.matches(pattern) && address.length()<=100)
            return true;
        return false;
    }

    public boolean isValidName(EditText editText) {
        String name = editText.getText().toString();
        String pattern = "[a-zA-Z ]*";

        if(name.length() <=45 && name.matches(pattern))
            return true;
        return false;
    }

    public boolean isValidValue (EditText editText) {
        String input = editText.getText().toString();
        float value = Float.valueOf(input);

        if(value > 0)
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

