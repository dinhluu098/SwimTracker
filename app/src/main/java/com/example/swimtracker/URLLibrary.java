package com.example.swimtracker;

public class URLLibrary {
    public static String URL_MAIN = "http://stapi.pythonanywhere.com/";
    private static final URLLibrary ourInstance = new URLLibrary();

    public static URLLibrary getInstance() {
        return ourInstance;
    }
    public static String getURLMain(){return URL_MAIN; };
    private URLLibrary() {
    }
}
