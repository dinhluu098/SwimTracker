package com.example.swimtracker;

public class URLManage {
    private String mainURL = "http://stapi.pythonanywhere.com";
    private static URLManage ourInstance = new URLManage();

    public static URLManage getInstance() {
        if (ourInstance != null)
            ourInstance = new URLManage();
        return ourInstance;
    }
    public String getMainURL(){
        return mainURL;
    }
}
