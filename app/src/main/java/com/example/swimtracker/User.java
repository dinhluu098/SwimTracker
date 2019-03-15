package com.example.swimtracker;

import org.json.JSONObject;

import static java.sql.Types.NULL;

public abstract class User {
    private String username, address, dob, email, first_name, last_name, phone;
    private int gender, id, role_id;

    public User(){

    }
    public User(String username,String address, String dob, String email, String first_name, String last_name, String phone, int gender, int id, int role_id) {
        this.username = username;
        this.dob = dob;
        this.email = email;
        this.first_name = first_name;
        this.last_name = last_name;
        this.phone = phone;
        this.gender = gender;
        this.id = id;
        this.role_id = role_id;

    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUsername() {
        return username;
    }

    public String getDob() {
        return dob;
    }

    public String getEmail() {
        return email;
    }

    public String getFirst_name() {
        return first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public String getPhone() {
        return phone;
    }

    public int getGender() {
        return gender;
    }

    public int getId() {
        return id;
    }

    public int getRole_id() {
        return role_id;
    }

    public String getParent_name(){return null;}

    public String  getParent_phone() {return null; }

    public float getHeight() {return NULL; }
    public float getWeight() {return NULL; }

    public String getName(){
        return first_name + " " + last_name;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }


    public void setUser(String address, String dob, String email,String first_name, String last_name, String phone, int gender, int id, int role_id ){
        setAddress(address);
        setDob(dob);
        setEmail(email);
        setFirst_name(first_name);
        setLast_name(last_name);
        setPhone(phone);
        setGender(gender);
        setId(id);
        setRole_id(role_id);

    }

    public abstract void addDataFromJSONObject(JSONObject jsonObject);
    public abstract JSONObject convertToJSONObject();



}
