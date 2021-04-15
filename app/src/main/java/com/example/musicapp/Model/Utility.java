package com.example.musicapp.Model;

public class Utility {
    public static String convertDuration(long duration){
        long mimutes = (duration/1000)/60;
        long seconds = (duration/1000)%60;
        String converted = String.format("%d:%02d",mimutes,seconds);
        return converted;
    }
}
