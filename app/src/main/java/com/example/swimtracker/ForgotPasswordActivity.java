package com.example.swimtracker;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

public class ForgotPasswordActivity extends AppCompatActivity {

    RelativeLayout ly_fgemail,ly_confirmCode,ly_newPassword;
    Button btn_next,btn_next2,btn_agree;
    EditText edt_email,edt_confrimCode,edt_newPassword,edt_confrimNewPw;
    String URL_FGPSW = URLManage.getInstance().getMainURL() + "/signin";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        init();
        action();
    }

    private void action() {
        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextBtnEmail();
            }
        });
        btn_next2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextBtnCode();
            }
        });
        btn_agree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                agreeBtn();


            }
        });
    }

    public void init() {
        //Network
        AndroidNetworking.initialize(getApplication());

        //Layout
        ly_fgemail = (RelativeLayout) findViewById(R.id.ly_fgemail);
        ly_fgemail.setVisibility(RelativeLayout.VISIBLE);
        ly_confirmCode = ( RelativeLayout) findViewById(R.id.ly_confirm_code);
        ly_confirmCode.setVisibility(RelativeLayout.GONE);
        ly_newPassword = (RelativeLayout) findViewById(R.id.ly_new_password);
        ly_newPassword.setVisibility(RelativeLayout.GONE);

        //Button
        btn_next = (Button) findViewById(R.id.btn_next);
        btn_next2 = (Button) findViewById(R.id.btn_next2);
        btn_agree = (Button) findViewById(R.id.btn_agree);

        //EditText
        edt_email = (EditText) findViewById(R.id.edt_fgemail);
        edt_confrimCode = (EditText) findViewById(R.id.edt_confirm_code);
        edt_confrimNewPw = (EditText) findViewById(R.id.edt_confirm_password);
        edt_newPassword = (EditText) findViewById(R.id.edt_new_password);
    }

    public void nextBtnEmail () {
        if(!isValidEmail()){
            Toast.makeText(this, "Kiểm tra lại Email", Toast.LENGTH_SHORT).show();
            return;
        }


        JSONObject result = convertToJSONObject(edt_email);

        AndroidNetworking.post(URL_FGPSW)
                .addJSONObjectBody(result)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getString("status").equals("success")){
                                Toast.makeText(ForgotPasswordActivity.this, "Vui lòng kiểm tra Email", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(ForgotPasswordActivity.this, MainActivity.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(ForgotPasswordActivity.this, response.getString(""), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });

        ly_fgemail.setVisibility(RelativeLayout.GONE);
        ly_confirmCode.setVisibility(RelativeLayout.VISIBLE);

    }

    public void nextBtnCode() {
        if(isEmpty(edt_confrimCode)) {
            Toast.makeText(this, "Vui lòng nhập mã xác nhận", Toast.LENGTH_SHORT).show();
            return;
        }

        JSONObject result = convertToJSONObject(edt_confrimCode);

        AndroidNetworking.post(URLLibrary.getURLMain() + "")
                .addJSONObjectBody(result)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getString("status").equals("success")){
                                Intent intent = new Intent(ForgotPasswordActivity.this, MainActivity.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(ForgotPasswordActivity.this, response.getString(""), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });

        ly_confirmCode.setVisibility(RelativeLayout.GONE);
        ly_newPassword.setVisibility(RelativeLayout.VISIBLE);
    }

    public void agreeBtn() {
        if(isEmpty(edt_newPassword)) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        if(isValidPassword()) {
            Toast.makeText(this, "Vui lòng nhập lại mật khẩu", Toast.LENGTH_SHORT).show();
            return;
        }

        if(checkSpecialCharacterPassword(edt_newPassword)) {
            Toast.makeText(this, "Mật Khẩu không được có ký tự đặc biệt", Toast.LENGTH_SHORT).show();
            return;
        }

       JSONObject result = convertToJSONObject(edt_newPassword);

        AndroidNetworking.post(URLLibrary.getURLMain() + "")
                .addJSONObjectBody(result)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            if (response.getString("status").equals("success")){
                                Intent intent = new Intent(ForgotPasswordActivity.this, MainActivity.class);
                                startActivity(intent);
                            }else{
                                Toast.makeText(ForgotPasswordActivity.this, response.getString(""), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });

        setContentView(R.layout.activity_login);
    }

    public JSONObject convertToJSONObject (EditText editText) {
        String string = editText.getText().toString().trim();

        JSONObject jsonObject = new JSONObject();
        JSONObject key= new JSONObject();

        try{
            key.put("password",string);
            jsonObject.put("key",key);
        }catch (Exception e) {
            e.printStackTrace();
        }

        return jsonObject;
    }


    public boolean isValidEmail(){
        String email = edt_email.getText().toString();
        String pattern ="[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if (!email.matches(pattern))
            return false;
        return true;
    }


    public boolean isEmpty(EditText editText) {
        String string = editText.getText().toString();

        if(string.equals(""))
            return true;
        return false;
    }

    public boolean isValidPassword() {
        String newPassword = edt_newPassword.getText().toString();
        String newPasswordConfirm  = edt_confrimNewPw.getText().toString();

        if(newPassword.length() <= 6 && !newPasswordConfirm.equals(newPassword))
            return true;
        return false;
    }

    public boolean checkSpecialCharacterPassword(EditText editText) {
        String string = editText.getText().toString();
        String pattern = "[a-zA-Z0-9.! ]*";

        if(!string.matches(pattern))
            return true;
        return false;
    }

}
