
package com.example.liyuan.justgo.Activities;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.liyuan.justgo.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class CreatePlan2 extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "CreatePlan2";
    private TextView startDate;
    private TextView endDate;
    private EditText start_date;
    private EditText end_date;
    private EditText budget;
    private String plan_Name;
    //private String location_Name;
    private String city_Name;
    private Button btnNext;


    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private DatePickerDialog.OnDateSetListener mDateSetListener2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_plan2);

        startDate = (TextView) findViewById(R.id.startDate);
        endDate = (TextView) findViewById(R.id.endDate);

        start_date = (EditText)findViewById(R.id.startDate);
        end_date = (EditText)findViewById(R.id.endDate);
        budget = (EditText)findViewById(R.id.txtBudget);
        btnNext =(Button)findViewById(R.id.btnGoCreate);


        //Handle start date calendar
        startDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get( Calendar.YEAR);
                int month = cal.get( Calendar.MONTH);
                int day = cal.get( Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog (
                        CreatePlan2.this,
                        mDateSetListener,
                        year,month,day);
                dialog.show();
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                String date = month + "/" + day + "/" + year;
                startDate.setText(date);
            }
        };


        endDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get( Calendar.YEAR);
                int month = cal.get( Calendar.MONTH);
                int day = cal.get( Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog (
                        CreatePlan2.this,
                        mDateSetListener2,
                        year,month,day);
                dialog.show();
            }
        });
        mDateSetListener2 = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                String date = month + "/" + day + "/" + year;
                endDate.setText(date);
            }
        };

        btnNext.setOnClickListener(this);
    }

    public void SendToCreatePlan3() {
        Intent SecondActivity = getIntent();
        plan_Name = SecondActivity.getStringExtra("PLAN_NAME");
        city_Name = SecondActivity.getStringExtra("CITY_NAME");
        String startDate = start_date.getText().toString();
        String endDate = end_date.getText().toString();
        String checkBudget = budget.getText().toString();


        try{
            SimpleDateFormat formatter = new SimpleDateFormat ("MM/dd/yyyy");

            Date date1 = formatter.parse(startDate);
            Date date2 = formatter.parse(endDate);

            if(startDate.isEmpty() || endDate.isEmpty() || checkBudget.isEmpty())
            {
                Toast.makeText(this, "Please enter all fields above", Toast.LENGTH_LONG).show();
            }
            else if (date2.before(date1))
            {
                Toast.makeText(this, "End date must be higher than start date", Toast.LENGTH_LONG).show();
            }
            else
            {
                Double nBudget = Double.parseDouble(budget.getText().toString());
            //first parameter is the context, second is the class of the activity to launch
                Intent planName2 = new Intent (CreatePlan2.this, CreatePlan3.class);
                planName2.putExtra("PLAN_NAME_AC1", plan_Name);
                planName2.putExtra("CITY_NAME_AC1", city_Name);
                planName2.putExtra("START_DATE", startDate.trim());
                planName2.putExtra("END_DATE", endDate.trim());
                planName2.putExtra("BUDGET", nBudget);
                startActivity(planName2);
            }
         }catch (ParseException e1){
            Toast.makeText(this, "Please enter all fields correctly", Toast.LENGTH_LONG).show();
        }
    }
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btnGoCreate:
                SendToCreatePlan3();
                break;
        }
    }
}
