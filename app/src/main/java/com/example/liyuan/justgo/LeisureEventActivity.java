package com.example.liyuan.justgo;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class LeisureEventActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText leisureEventName;
    private EditText leisureEventDate;
    private EditText leisureEventTime;
    private EditText leisureEventLocation;
    private EditText leisureEventNote;
    private Calendar calendar;
    private Button leisureCancel;
    private Button leisureCreate;
    private ImageButton leisureDelete;

    private LeisureEvent event;


    private int passedEventIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leisure_event);

        passedEventIndex = getIntent().getIntExtra(VacationsMainActivity.EVENT_KEY, -1);

        leisureEventName = (EditText)findViewById(R.id.leisure_event_name);
        leisureEventDate = (EditText)findViewById(R.id.leisure_event_date);
        leisureEventTime = (EditText)findViewById(R.id.leisure_event_time);
        leisureEventLocation = (EditText)findViewById(R.id.leisure_event_location);
        leisureEventNote = (EditText)findViewById(R.id.leisure_event_notes);
        leisureCancel = (Button)findViewById(R.id.leisure_cancel_btn);
        leisureCreate = (Button)findViewById(R.id.leisure_create_btn);
        leisureDelete = (ImageButton)findViewById(R.id.delete_leisure_event_btn);


        if(passedEventIndex != -1) {
            event = (LeisureEvent) VacationsMainActivity.vacation.events.get(passedEventIndex);
            leisureEventName.setText(event.getName());
            leisureEventDate.setText(event.getDate());
            leisureEventTime.setText(event.getTime());
            leisureEventLocation.setText(event.getLocation());
            leisureEventNote.setText(event.getNote());
        }

        // This is used to set an action listener for the buttons in this activity
        leisureCancel.setOnClickListener(this);
        leisureCreate.setOnClickListener(this);
        leisureDelete.setOnClickListener(this);

        dateChooser();
        timeChooser();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.leisure_cancel_btn:
                onBackPressed();
                break;
            case R.id.leisure_create_btn:

                String eventName = leisureEventName.getText().toString();
                String eventDate = leisureEventDate.getText().toString();
                String eventTime = leisureEventTime.getText().toString();
                String eventLocation = leisureEventLocation.getText().toString();
                String eventNotes = leisureEventNote.getText().toString();

                if(eventName.isEmpty() || eventDate.isEmpty() || eventTime.isEmpty() || eventLocation.isEmpty()) {
                    AlertDialog alertDialog = new AlertDialog.Builder(LeisureEventActivity.this).create();
                    alertDialog.setTitle("Error");
                    alertDialog.setMessage("Fields cannot be empty.");
                    alertDialog.setButton( AlertDialog.BUTTON_NEUTRAL, "CLOSE",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }
                else if(dateOutsideRange(eventDate)) {
                    AlertDialog alertDialog = new AlertDialog.Builder(LeisureEventActivity.this).create();
                    alertDialog.setTitle("Error");
                    alertDialog.setMessage("Date must be within dates of vacation");
                    alertDialog.setButton( AlertDialog.BUTTON_NEUTRAL, "CLOSE",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }
                else {

                    if(passedEventIndex != -1) {
                        event.setName(eventName);
                        event.setDate(eventDate);
                        event.setTime(eventTime);
                        event.setLocation(eventLocation);
                        event.setNote(eventNotes);
                        onBackPressed();
                    }
                    else {
                        Event event = new LeisureEvent(eventName, eventDate, eventTime, eventLocation, eventNotes);
                        VacationsMainActivity.vacation.events.add(event);
                        onBackPressed();
                    }
                }
                break;
            case R.id.delete_leisure_event_btn:


                if(passedEventIndex == -1) {
                    break;
                }

                AlertDialog alertDialog = new AlertDialog.Builder(LeisureEventActivity.this).create();
                alertDialog.setTitle("Confirm");
                alertDialog.setMessage("Are you sure you want to delete this item?");
                alertDialog.setButton( AlertDialog.BUTTON_NEGATIVE, "No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                alertDialog.setButton( AlertDialog.BUTTON_POSITIVE, "Yes",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        VacationsMainActivity.vacation.events.remove(VacationsMainActivity.vacation.events.indexOf(event));

                        dialog.dismiss();
                        onBackPressed();


                    }
                });
                alertDialog.show();

                break;
        }
    }


    private boolean dateOutsideRange(String date) {

        try {
            SimpleDateFormat sdf = new SimpleDateFormat ("MM/dd/yy");

            Date d = sdf.parse(date);
            Date startDate = sdf.parse(VacationsMainActivity.vacation.getStartDate());
            Date endDate = sdf.parse(VacationsMainActivity.vacation.getEndDate());

            return d.before(startDate) || d.after(endDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }



    private void timeChooser() {

        leisureEventTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get( Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get( Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog (LeisureEventActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        if(selectedMinute < 10) {
                            leisureEventTime.setText(selectedHour + ":0" + selectedMinute);
                        }
                        else {
                            leisureEventTime.setText(selectedHour + ":" + selectedMinute);
                        }
                    }
                }, hour, minute, false);
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });
    }



    private void dateChooser() {
        calendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                calendar.set( Calendar.YEAR, year);
                calendar.set( Calendar.MONTH, monthOfYear);
                calendar.set( Calendar.DAY_OF_MONTH, dayOfMonth);
                updateDate();
            }

        };

        leisureEventDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog (LeisureEventActivity.this, date, calendar
                        .get( Calendar.YEAR), calendar.get( Calendar.MONTH),
                        calendar.get( Calendar.DAY_OF_MONTH)).show();
            }
        });
    }


    private void updateDate() {
        String sdf = "MM/dd/yy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat (sdf);

        leisureEventDate.setText(simpleDateFormat.format(calendar.getTime()));
    }
}
