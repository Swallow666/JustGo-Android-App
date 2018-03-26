package com.example.liyuan.justgo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class PlanActivity extends AppCompatActivity {

    public static final String VACATION_KEY = "vacation_key";
    /** This is the main ArrayList of vacations, which holds all the vacations, we read the list of vacations
     * from external storage, using the readVacationList() static method from the FileSaver class
     */
    public static ArrayList<Vacation> vacations = FileSaver.readVacationList();

    /** This is our field for the listView of all the vacations we created in the xml file
     */
    private ListView listView;

    /** Here we instantiate a new VacationListAdapter object which will connect the ArrayList of our
     *vacations to the list view, so we can see our vacations in the list view
     */
    private VacationListAdapter vacationListAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan);


        // Assigning our list view field to the list view we created in the activity_main.xml file
        listView = (ListView)findViewById(R.id.main_list_view);



        vacationListAdapter = new VacationListAdapter(PlanActivity.this, vacations);
        listView.setAdapter(vacationListAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(PlanActivity.this,VacationsMainActivity.class);
                intent.putExtra(VACATION_KEY,position);
                startActivity(intent);

            }
        });
        refreshVacationList();
    }


    public void refreshVacationList(){
        // The following lines use the VacationListAdapter to sort the vacations in chronological order,
        // Using the sortByDates() method from the Vacation.java class
        Comparator comparator = Vacation.sortByDates();
        Collections.sort(vacations, comparator);
        vacationListAdapter.setData(vacations);
        vacationListAdapter.notifyDataSetChanged();

    }
    @Override
    public void onResume()
    {
        super.onResume();
        refreshVacationList();

    }


    /** This method writes the user's vacations to external storage if the user closes the app from this activity
     */
    @Override
    protected void onPause() {
        super.onPause();
        FileSaver.writeVacationList(vacations);

    }
}
