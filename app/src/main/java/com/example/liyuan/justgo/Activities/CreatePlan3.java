/**
 * Created by Fernando Pereira Borges
 * Assignment2 - Mobile Development 2
 * Date: November 03, 2017
 * Purpose: Database implementation - user Database
 */
package com.example.liyuan.justgo.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.liyuan.justgo.Database.DataBaseHelper;
import com.example.liyuan.justgo.Model.location1;
import com.example.liyuan.justgo.Model.plan;
import com.example.liyuan.justgo.R;

import static com.example.liyuan.justgo.Activities.PlanPage.CUR_UID;
import static com.example.liyuan.justgo.Activities.PlanPage.CUR_UID;


public class CreatePlan3 extends AppCompatActivity implements View.OnClickListener {

    private final AppCompatActivity activity = CreatePlan3.this;

    private String nameOfPlan;
    //private String nameOfLocation;
    private String nameOfCity;
    private String startDateOfPlan;
    private String endDateOfPlan;
    private Double budgetOfPlan;
    private EditText txtDescription;
    public static String SuccessPlanMessage = "Plan Created!";
    private DataBaseHelper databaseHelper;
    private Button btnCreate;
    private plan plan;
    private location1 location;

    private TextView planName;
    private TextView locationName;
    private TextView startDate;
    private TextView endDate;
    private TextView budGet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_plan3);

        btnCreate = (Button)findViewById(R.id.btnCreate);

        //Create variables to get all information from activities to save on database
        nameOfPlan = getIntent().getStringExtra("PLAN_NAME_AC1");
        nameOfCity = getIntent().getStringExtra("CITY_NAME_AC1");
        startDateOfPlan = getIntent().getStringExtra("START_DATE");
        endDateOfPlan = getIntent().getStringExtra("END_DATE");
        budgetOfPlan = getIntent().getDoubleExtra("BUDGET", 0);
        txtDescription = (EditText)findViewById(R.id.txtDescription);


        //assign private variables to text view ids
        planName = (TextView)findViewById(R.id.textViewPlan);
        locationName = (TextView)findViewById(R.id.textViewLocation);
        startDate = (TextView)findViewById(R.id.textViewStartDate);
        endDate = (TextView)findViewById(R.id.textViewEndDate);
        budGet = (TextView)findViewById(R.id.textViewBudget);


        planName.setText(getIntent().getStringExtra("PLAN_NAME_AC1"));
        locationName.setText(getIntent().getExtras().getString("CITY_NAME_AC1"));
        startDate.setText(getIntent().getStringExtra("START_DATE"));
        endDate.setText(getIntent().getExtras().getString("END_DATE"));

        String stringDoubleBudget= Double.toString(getIntent().getDoubleExtra("BUDGET", 0));
        budGet.setText(stringDoubleBudget);


        btnCreate.setOnClickListener(this);
        databaseHelper = new DataBaseHelper(activity);
        plan = new plan();
        location = new location1();
    }


    public void onClick(View v)
    {
        switch (v.getId()) {
            case R.id.btnCreate:
                creatingPlan();
                break;
        }
    }

    public void creatingPlan()
    {
        SharedPreferences prefs = getSharedPreferences(CUR_UID, MODE_PRIVATE);
        Integer cur_userid = prefs.getInt("CUR_UID", 0);//"0 is the default value.

        String tDescription = txtDescription.getText().toString().trim();

        //saving in database
            plan.setPlan_name(nameOfPlan);
            plan.setBudget(budgetOfPlan);
            plan.setDescription(tDescription);
            plan.setUserid(cur_userid);
            databaseHelper.addPlan(plan);

            location.setLocation_name(nameOfCity);
            location.setStart_date(startDateOfPlan);
            location.setEnd_date(endDateOfPlan);
            databaseHelper.addLocation(location);

            Toast.makeText(this, SuccessPlanMessage, Toast.LENGTH_LONG).show();

            startActivity(new Intent (this, PlanPage.class));
    }

}
