package com.example.swimtracker;

public class UserProfile {
    private static String accessToken;
    private static User user;
    private static UserProfile ourInstance ;

    public static UserProfile getInstance() {
        if (ourInstance == null)
            ourInstance = new UserProfile();
        return ourInstance;
    }


    public  void setAccessToken(String accessToken){
        this.accessToken = accessToken;
    }

    public static String getAccessToken(){
        return accessToken;
    }

    public static User getUser(){
        return new Swimmer();
    }

    public void getData(User user){
        this.user = user;
    }
}
