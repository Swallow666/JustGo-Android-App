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


public class TransportationEventActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText transEventName;
    private EditText transEventDate;
    private EditText transEventTime;
    private EditText transEventMode;
    private EditText transEventOrigin;
    private EditText transEventDestination;
    private Calendar calendar;
    private Button transCancel;
    private Button transCreate;
    private ImageButton transDelete;


    private TransportationEvent event;

    private int passedEventIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transportation_event);

        passedEventIndex = getIntent().getIntExtra(VacationsMainActivity.EVENT_KEY, -1);

        transEventName = (EditText)findViewById(R.id.trans_event_name);
        transEventDate = (EditText)findViewById(R.id.trans_event_date);
        transEventTime = (EditText)findViewById(R.id.trans_event_time);
        transEventMode = (EditText)findViewById(R.id.trans_event_mode);
        transEventOrigin = (EditText)findViewById(R.id.trans_event_origin);
        transEventDestination = (EditText)findViewById(R.id.trans_event_destination);
        transCancel = (Button)findViewById(R.id.trans_cancel_btn);
        transCreate = (Button)findViewById(R.id.trans_create_btn);
        transDelete = (ImageButton)findViewById(R.id.delete_trans_event_btn);


        if(passedEventIndex != -1) {
            event = (TransportationEvent) VacationsMainActivity.vacation.events.get(passedEventIndex);
            transEventName.setText(event.getName());
            transEventDate.setText(event.getDate());
            transEventTime.setText(event.getTime());
            transEventMode.setText(event.getMode());
            transEventOrigin.setText(event.getOrigin());
            transEventDestination.setText(event.getDestination());
        }

        // This is used to set an action listener for the buttons in this activity
        transCancel.setOnClickListener(this);
        transCreate.setOnClickListener(this);
        transDelete.setOnClickListener(this);

        dateChooser();
        timeChooser();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.trans_cancel_btn:
                onBackPressed();
                break;
            case R.id.trans_create_btn:

                String eventName = transEventName.getText().toString();
                String eventDate = transEventDate.getText().toString();
                String eventTime = transEventTime.getText().toString();
                String eventMode = transEventMode.getText().toString();
                String eventOrigin = transEventOrigin.getText().toString();
                String eventDestination = transEventDestination.getText().toString();

                if(eventName.isEmpty() || eventDate.isEmpty() || eventTime.isEmpty() ||
                        eventMode.isEmpty() || eventOrigin.isEmpty() || eventDestination.isEmpty()) {
                    AlertDialog alertDialog = new AlertDialog.Builder(TransportationEventActivity.this).create();
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
                    AlertDialog alertDialog = new AlertDialog.Builder(TransportationEventActivity.this).create();
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
                        event.setMode(eventMode);
                        event.setOrigin(eventOrigin);
                        event.setDestination(eventDestination);
                        onBackPressed();
                    }
                    else {
                        Event event = new TransportationEvent(eventName, eventDate, eventTime, eventMode, eventOrigin, eventDestination);
                        VacationsMainActivity.vacation.events.add(event);
                        onBackPressed();
                    }

                }
                break;
            case R.id.delete_trans_event_btn:

                if(passedEventIndex == -1) {
                    break;
                }

                AlertDialog alertDialog = new AlertDialog.Builder(TransportationEventActivity.this).create();
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

        transEventTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get( Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get( Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog (TransportationEventActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        if(selectedMinute < 10) {
                            transEventTime.setText(selectedHour + ":0" + selectedMinute);
                        }
                        else {
                            transEventTime.setText(selectedHour + ":" + selectedMinute);
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

        transEventDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog (TransportationEventActivity.this, date, calendar
                        .get( Calendar.YEAR), calendar.get( Calendar.MONTH),
                        calendar.get( Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateDate() {
        String sdf = "MM/dd/yy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat (sdf);

        transEventDate.setText(simpleDateFormat.format(calendar.getTime()));
    }

}
