package com.example.liyuan.justgo;


public class LeisureEvent extends Event {
    private String location;
    private String note;

    public LeisureEvent(String name, String date, String time, String location, String note) {

        super(name, date, time);
        this.location = location;
        this.note = note;
    }


    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getNote() {
        return note;
    }


    public void setNote(String note) {
        this.note = note;
    }

}
