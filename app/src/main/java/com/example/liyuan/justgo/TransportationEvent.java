package com.example.liyuan.justgo;


public class TransportationEvent extends Event {
    private String mode;
    private String origin;
    private String destination;


    public TransportationEvent(String name, String date, String time, String mode, String origin, String destination) {
        super(name, date, time);
        this.mode = mode;
        this.origin = origin;
        this.destination = destination;
    }


    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }


}
