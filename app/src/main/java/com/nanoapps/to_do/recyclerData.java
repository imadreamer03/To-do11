package com.nanoapps.to_do;

public class recyclerData{
    int id;
    String firstLetters;
    String description;
    String time;
    int gradient;

    public recyclerData(String description, String firstLetters, String time, int gradient) {
        this.firstLetters = firstLetters;
        this.description = description;
        this.gradient = gradient;
        this.time = time;
    }

    public String getTime(){
        return time;
    }

    public void setTime(String time){
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstLetters() {
        return firstLetters;
    }

    public String getDescription() {
        return description;
    }

    public int getGradient() {
        return gradient;
    }

    public void setGradient(int gradient) {
        this.gradient = gradient;
    }
}