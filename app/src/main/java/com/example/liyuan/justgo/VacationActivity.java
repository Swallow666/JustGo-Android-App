package com.example.liyuan.justgo;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class VacationActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText vacationNameText;
    private EditText locationText;
    private EditText startDateText;
    private EditText endDateText;
    private Button createVacationBtn;
    private Button cancelBtn;
    private Calendar calendar;


    private Vacation vacation;

    private int passedVacationIndex;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacation);


        passedVacationIndex = getIntent().getIntExtra(PlanActivity.VACATION_KEY, -1);


        // Assigns the fields we created in the Java class to the names of those fields in the xml file
        vacationNameText = (EditText)findViewById(R.id.vacation_name);
        locationText = (EditText)findViewById(R.id.vacation_location);
        startDateText = (EditText)findViewById(R.id.vacation_start_date);
        endDateText = (EditText)findViewById(R.id.vacation_end_date);
        createVacationBtn = (Button)findViewById(R.id.create_vacation_btn);
        cancelBtn = (Button)findViewById(R.id.cancel_btn);



        if(passedVacationIndex != -1) {
            vacation = PlanActivity.vacations.get(passedVacationIndex);
            vacationNameText.setText(vacation.getName());
            locationText.setText(vacation.getLocation());
            startDateText.setText(vacation.getStartDate());
            endDateText.setText(vacation.getEndDate());
        }

        // This sets an action listener for the buttons in this activity, a click to be detected
        createVacationBtn.setOnClickListener(this);
        cancelBtn.setOnClickListener(this);


        startDateChooser();
        endDateChooser();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel_btn:
                onBackPressed();
                break;
            case R.id.create_vacation_btn:
                String vacationName = vacationNameText.getText().toString();
                String location = locationText.getText().toString();
                String startDate = startDateText.getText().toString();
                String endDate = endDateText.getText().toString();

                if(vacationName.isEmpty() || location.isEmpty() || startDate.isEmpty() || endDate.isEmpty()) {
                    AlertDialog alertDialog = new AlertDialog.Builder(VacationActivity.this).create();
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
                else if(endBeforeStartDate(startDate, endDate)) {
                    AlertDialog alertDialog = new AlertDialog.Builder(VacationActivity.this).create();
                    alertDialog.setTitle("Error");
                    alertDialog.setMessage("End date cannot be before start date!");
                    alertDialog.setButton( AlertDialog.BUTTON_NEUTRAL, "CLOSE",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }
                else{

                    if(passedVacationIndex != -1) {
                        vacation.setName(vacationName);
                        vacation.setLocation(location);
                        vacation.setStartDate(startDate);
                        vacation.setEndDate(endDate);
                        onBackPressed();
                    }
                    else {
                        ArrayList<Event> events = new ArrayList<> ();
                        Vacation vacation = new Vacation(vacationName, location, startDate, endDate, events);

                        PlanActivity.vacations.add(vacation);
                        onBackPressed();
                    }
                }
        }
    }

    private boolean endBeforeStartDate(String date1, String date2) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat ("MM/dd/yy");

            Date d1 = sdf.parse(date1);
            Date d2 = sdf.parse(date2);

            return d1.after(d2);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return false;
    }

    private void startDateChooser() {
        calendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                calendar.set( Calendar.YEAR, year);
                calendar.set( Calendar.MONTH, monthOfYear);
                calendar.set( Calendar.DAY_OF_MONTH, dayOfMonth);
                updateStartDate();
            }

        };

        startDateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog (VacationActivity.this, date, calendar
                        .get( Calendar.YEAR), calendar.get( Calendar.MONTH),
                        calendar.get( Calendar.DAY_OF_MONTH)).show();
            }
        });
    }


    private void updateStartDate() {
        String sdf = "MM/dd/yy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat (sdf);

        startDateText.setText(simpleDateFormat.format(calendar.getTime()));
    }

    private void endDateChooser() {
        calendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                calendar.set( Calendar.YEAR, year);
                calendar.set( Calendar.MONTH, monthOfYear);
                calendar.set( Calendar.DAY_OF_MONTH, dayOfMonth);
                updateEndDate();
            }

        };

        endDateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog (VacationActivity.this, date, calendar
                        .get( Calendar.YEAR), calendar.get( Calendar.MONTH),
                        calendar.get( Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void updateEndDate() {
        String sdf = "MM/dd/yy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat (sdf);

        endDateText.setText(simpleDateFormat.format(calendar.getTime()));
    }
}
