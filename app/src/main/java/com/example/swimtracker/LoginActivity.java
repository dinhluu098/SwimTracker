package com.example.swimtracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    EditText edtUsername, edtPassword;
    Button btnLogin, btnResgister;
    TextView txtForgotPassword;
    String URL_LOGIN = URLManage.getInstance().getMainURL() + "/api/public/login";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
        action();
    }

    public void init(){
        AndroidNetworking.initialize(getApplication());

        edtUsername = findViewById(R.id.edt_username);
        edtPassword = findViewById(R.id.edt_password);
        btnLogin = findViewById(R.id.btn_login);
        btnResgister = findViewById(R.id.btn_register);
        txtForgotPassword = findViewById(R.id.tv_forgotpassword);


    }

    public void login()  {
        final String username = edtUsername.getText().toString();
        String password = edtPassword.getText().toString();

        if(!isValidUsername(edtUsername) || !isValidPassword(edtPassword) || isEmpty(edtPassword) || isEmpty(edtUsername)) {
            Toast.makeText(LoginActivity.this, "Tài khoản hoặc mật khẩu không hợp lệ", Toast.LENGTH_SHORT).show();
        }else{
            JSONObject result = addToJSONObject(username, password);

            AndroidNetworking.post(URL_LOGIN)
                    .addJSONObjectBody(result)
                    .build()
                    .getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {
                            Log.e("ABC", "A");
                            try {
                                JSONObject jsonObject = response.getJSONObject("values");
                                if (response.getBoolean("success")) {
                                    Log.d("ABC", "Success");
                                    loadData();

                                    UserProfile.getInstance().setAccessToken(response.getJSONObject("values").getString("token"));
                                    Toast.makeText(LoginActivity.this, "Yes", Toast.LENGTH_SHORT).show();
                                } else {
                                    Log.d("ABC", "Fail");
                                    Toast.makeText(LoginActivity.this, "Nope", Toast.LENGTH_SHORT).show();
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

    }

    public void action() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });

        String text = txtForgotPassword.getText().toString();
        SpannableString spannableString = new SpannableString(text);

        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Intent intent = new Intent(LoginActivity.this,ForgotPasswordActivity.class);
                startActivity(intent);
            }
        };

        spannableString.setSpan(clickableSpan,0,15, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        txtForgotPassword.setText(spannableString);
        txtForgotPassword.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public JSONObject addToJSONObject(String username, String password){
        JSONObject result = new JSONObject();
        JSONObject user = new JSONObject();
        try {
            user.put("username", username);
            user.put("password", password);
            result.put("user", user);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return user;

    }

    public void loadData(){
        final String username = edtUsername.getText().toString();
        AndroidNetworking.get(URLManage.getInstance().getMainURL() +"api/profile/" +username+"/view")
                .addHeaders("Authorization","Bearer "+UserProfile.getAccessToken())
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        User user = null;
                        try {
                            JSONObject jsonObject = response.getJSONObject("values").getJSONObject("user");
                            if (jsonObject.getInt("role_id") == 0) {
                                user = new Coach();
                                user.addDataFromJSONObject(jsonObject);
                            } else {
                                user = new Swimmer();
                                user.addDataFromJSONObject(jsonObject);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                      user.setUsername(username);
                        UserProfile.getInstance().getData(user);

                        Intent intent= new Intent(LoginActivity.this,CoachViewInfo.class);
                        startActivity(intent);

                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }

    public boolean isValidUsername(EditText editText) {
        String username = editText.getText().toString();
        String pattern ="[a-zA-Z0-9]*";

        if(username.matches(pattern) && username.length()>=6)
            return true;
        return false;

    }

    public boolean isValidPassword(EditText editText) {
        String password = editText.getText().toString();
        String pattern = "[a-zA-Z0-9.! ]*";

        if(password.matches(pattern) && password.length()>=6)
            return true;
        return false;
    }

    public boolean isEmpty (EditText editText) {
        String inputField = editText.getText().toString();

        if(inputField.matches(""))
            return true;
        return false;
    }


}
