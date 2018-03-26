package com.example.liyuan.justgo;

import java.io.Serializable;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;


public class Vacation implements Serializable {
    private String name;
    private String location;
    private String startDate;
    private String endDate;
    public ArrayList events;


    public Vacation(String name, String location, String startDate, String endDate, ArrayList<Event> events) {
        this.name = name;
        this.location = location;
        this.startDate = startDate;
        this.endDate = endDate;
        this.events = events;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return this.location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getStartDate() {
        return this.startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return this.endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public ArrayList getEvents() {
        return events;
    }

    public void setEvents(ArrayList events) {
        this.events = events;
    }

    static Comparator<Vacation> sortByDates() {
        return new Comparator<Vacation> () {
            /**
             * This method compares two vacations' dates and returns -1 if the first one comes before the second
             * and 1 if vice versa
             * @param v1 The first vacation to be compared
             * @param v2 The second vacation to be compared
             * @return int
             */
            @Override
            public int compare(Vacation v1, Vacation v2) {
                try {

                    ParsePosition position = new ParsePosition (0);
                    SimpleDateFormat df = new SimpleDateFormat ("MM/dd/yy");


                    Date vacationOneStartDate = df.parse(v1.startDate);
                    Date vacationTwoStartDate = df.parse(v2.startDate);

                    Date vacationOneEndDate = df.parse(v1.endDate);
                    Date vacationTwoEndDate = df.parse(v2.endDate);


                    if (vacationOneStartDate.before(vacationTwoStartDate)) {
                        return -1;
                    }
                    else if (vacationOneStartDate.after(vacationTwoStartDate)) {
                        return 1;
                    }

                    else if(vacationOneStartDate.equals(vacationTwoStartDate)) {
                        if(vacationOneEndDate.before(vacationTwoEndDate)) {
                            return -1;
                        }
                        else if(vacationOneEndDate.after(vacationTwoEndDate)) {
                            return 1;
                        }
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return 0;
            }
        };
    }


}
