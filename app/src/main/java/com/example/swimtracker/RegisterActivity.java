package com.example.swimtracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {
    Spinner spn_gender;
    Button btn_register;
    EditText edt_username, edt_password, edt_repassword, edt_fname, edt_lname, edt_dob, edt_address, edt_phone, edt_email;
    String URL_SIGNUP = URLLibrary.getURLMain() + "signup";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initDialog();
        solve();
    }

    public void solve(){
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
    }

    public void register(){
        if (!isValidForm()){
            Toast.makeText(this, "Kiểm tra lại thông tin", Toast.LENGTH_SHORT).show();
            return;
        }
        //Get Text
        final String username = edt_username.getText().toString();
        final String password = edt_password.getText().toString();
        final String first_name = edt_fname.getText().toString();
        final String last_name = edt_lname.getText().toString();
        final String dob = edt_dob.getText().toString();
        final String address = edt_address.getText().toString();
        final String phone = edt_phone.getText().toString();
        final String email = edt_email.getText().toString();
        int gender = getGender();
        //Create JSON

        JSONObject result = convertToJSONObject(username,password,first_name,last_name,dob,address,phone,gender,email);

        //API Request
        AndroidNetworking.post(URL_SIGNUP)
                .addJSONObjectBody(result)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getJSONObject("result").getString("status").equals("success")){
                                Toast.makeText(RegisterActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(RegisterActivity.this, response.getString(""), Toast.LENGTH_SHORT).show();
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

    public JSONObject convertToJSONObject (String username, String password, String first_name, String last_name,String address, String phone, String dob, int gender, String email) {
        JSONObject jsonObject = new JSONObject();
        JSONObject coach = new JSONObject();

        try{
            coach.put("username",username);
            coach.put("password",password);
            coach.put("first_name",first_name);
            coach.put("last_name",last_name);
            coach.put("dob",dob);
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

    public void initDialog(){
        //Network
        AndroidNetworking.initialize(getApplication());

        //Spinner
        String listGender[] = {"Giới tính","Nam","Nữ"};
        spn_gender = findViewById(R.id.spn_gender);
        ArrayAdapter<String> adapterSpiner = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, listGender);
        spn_gender.setAdapter(adapterSpiner);

        //EditText
        edt_username = findViewById(R.id.edt_username);
        edt_password = findViewById(R.id.edt_password);
        edt_repassword = findViewById(R.id.edt_repassword);
        edt_fname = findViewById(R.id.edt_fname);
        edt_lname = findViewById(R.id.edt_lname);
        edt_dob = findViewById(R.id.edt_dob);
        edt_address = findViewById(R.id.edt_address);
        edt_phone = findViewById(R.id.edt_phone);
        edt_email = findViewById(R.id.edt_email);
        //Button
        btn_register = findViewById(R.id.btn_register);

        //TimePicker
    }

    public boolean isValidForm(){
        return isValidAddress() && isValidEmail() && isValidPhone() && isValidName(edt_fname) && isValidName(edt_lname) && isValidUsername() && isValidPassword() ;
    }


    public boolean isValidEmail(){
        String email = edt_email.getText().toString();
        String pattern ="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (email.matches(pattern))
            return true;
        return false;
    }

    public boolean isValidUsername() {
        String username = edt_username.getText().toString();
        String pattern ="[a-zA-Z0-9]*";

        if(username.matches(pattern) && username.length()>=6)
            return true;
        return false;

    }

    public boolean isValidPassword() {
        String password = edt_password.getText().toString();
        String pattern = "[a-zA-Z0-9.! ]*";

        if(password.matches(pattern) && password.length()>=6)
            return true;
        return false;
    }

    public boolean isValidPhone(){
        return edt_phone.getText().toString().length() == 10;
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

    public int getGender () {
        String gender = spn_gender.getSelectedItem().toString();

        if(gender.equals("Nam"))
            return 1;
        return 0;
    }


}
