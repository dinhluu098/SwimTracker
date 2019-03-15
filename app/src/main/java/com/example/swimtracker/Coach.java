package com.example.swimtracker;

import org.json.JSONObject;

public class Coach extends User {
    public Coach(String username, String address, String dob, String email, String first_name, String last_name, String phone, int gender, int id, int role_id) {
        super(username,address, dob, email, first_name, last_name, phone, gender, id, role_id);
    }

    @Override
    public void addDataFromJSONObject(JSONObject jsonObject) {
        try {
            super.setUser(jsonObject.getString("address"),
                    jsonObject.getString("dob"),
                    jsonObject.getString("email"),
                    jsonObject.getString("first_name"),
                    jsonObject.getString("last_name"),
                    jsonObject.getString("phone"),
                    jsonObject.getInt("gender"),
                    jsonObject.getInt("id"),
                    jsonObject.getInt("role_id")
            );
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public JSONObject convertToJSONObject() {
        return null;
    }

    public Coach(){
        super();
    }
}
