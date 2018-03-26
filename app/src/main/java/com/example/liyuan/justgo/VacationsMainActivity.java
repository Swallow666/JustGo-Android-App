package com.example.liyuan.justgo;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Collections;
import java.util.Comparator;

public class VacationsMainActivity extends AppCompatActivity implements View.OnClickListener {
    // Similar to other activities, these fields represent the features of our layout file
    private TextView vacationName;
    private TextView location;
    private TextView dates;
    private ImageButton deleteButton;
    private Intent intent;
    private Button newTransportationEvent;
    private Button newLeisureEvent;


    private ListView listView;

    public static Vacation vacation;

    private int index;

    public static final String EVENT_KEY = "event_key";

    private EventListAdapter eventListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vacations_main);

        PlanActivity.vacations = FileSaver.readVacationList();

        int index = getIntent().getIntExtra(PlanActivity.VACATION_KEY, -1);
        vacation = PlanActivity.vacations.get(index);


        vacationName = (EditText) findViewById(R.id.vacations_main_name_text);
        location = (EditText) findViewById(R.id.vacations_main_location_text);
        dates = (EditText) findViewById(R.id.vacations_main_dates_text);
        deleteButton = (ImageButton) findViewById(R.id.delete_vacation_btn);
        newTransportationEvent = (Button)findViewById(R.id.new_transportation_event);
        newLeisureEvent = (Button)findViewById(R.id.new_leisure_event);
        listView = (ListView)findViewById(R.id.vacations_main_event_list);

        String vacationLocationText = "Location: " + vacation.getLocation();
        String vacationDatesText = vacation.getStartDate() + " - " + vacation.getEndDate();

        vacationName.setText(vacation.getName());
        location.setText(vacationLocationText);
        dates.setText(vacationDatesText);

        // Here we have set action listeners for each of these features on our activity
        vacationName.setOnClickListener(this);
        location.setOnClickListener(this);
        dates.setOnClickListener(this);
        deleteButton.setOnClickListener(this);
        newTransportationEvent.setOnClickListener(this);
        newLeisureEvent.setOnClickListener(this);

        eventListAdapter = new EventListAdapter(VacationsMainActivity.this, vacation.events);
        listView.setAdapter(eventListAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Event obj = (Event)vacation.events.get(position);

                if(obj instanceof TransportationEvent) {
                    intent = new Intent (VacationsMainActivity.this, TransportationEventActivity.class);
                    intent.putExtra(EVENT_KEY, position);
                }
                else if (obj instanceof LeisureEvent) {
                    intent = new Intent (VacationsMainActivity.this, LeisureEventActivity.class);
                    intent.putExtra(EVENT_KEY, position);
                }
                startActivity(intent);

            }
        });

        refreshEventList();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.delete_vacation_btn:
                AlertDialog alertDialog = new AlertDialog.Builder(VacationsMainActivity.this).create();
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
                        PlanActivity.vacations.remove(PlanActivity.vacations.indexOf(vacation));
                        dialog.dismiss();
                        onBackPressed();


                    }
                });
                alertDialog.show();
                break;

            case R.id.vacations_main_name_text:
                intent = new Intent (VacationsMainActivity.this, VacationActivity.class);
                intent.putExtra(PlanActivity.VACATION_KEY, PlanActivity.vacations.indexOf(vacation));
                startActivity(intent);
                break;
            case R.id.vacations_main_location_text:
                intent = new Intent (VacationsMainActivity.this, VacationActivity.class);
                intent.putExtra(PlanActivity.VACATION_KEY, PlanActivity.vacations.indexOf(vacation));
                startActivity(intent);
                break;
            case R.id.vacations_main_dates_text:
                intent = new Intent (VacationsMainActivity.this, VacationActivity.class);
                intent.putExtra(PlanActivity.VACATION_KEY, PlanActivity.vacations.indexOf(vacation));
                startActivity(intent);
                break;

            case R.id.new_transportation_event:
                intent = new Intent (VacationsMainActivity.this, TransportationEventActivity.class);
                startActivity(intent);
                break;
            case R.id.new_leisure_event:
                intent = new Intent (VacationsMainActivity.this, LeisureEventActivity.class);
                startActivity(intent);
                break;
        }

    }


    public void refreshEventList(){

        Comparator comparator = Event.sortByDates();
        Collections.sort(vacation.events, comparator);
        eventListAdapter.setData(vacation.events);
        eventListAdapter.notifyDataSetChanged();

    }

    @Override
    public void onResume() {
        super.onResume();
        vacationName.setText(vacation.getName());
        String vacationLocationText = "Location: " + vacation.getLocation();
        String vacationDatesText = vacation.getStartDate() + " - " + vacation.getEndDate();
        location.setText(vacationLocationText);
        dates.setText(vacationDatesText);
        refreshEventList();

    }

    @Override
    protected void onPause() {
        super.onPause();
        FileSaver.writeVacationList(PlanActivity.vacations);
    }
}
