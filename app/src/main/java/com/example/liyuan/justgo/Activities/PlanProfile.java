package com.example.liyuan.justgo.Activities;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.liyuan.justgo.Database.DataBaseHelper;
import com.example.liyuan.justgo.Model.location1;
import com.example.liyuan.justgo.Model.plan;
import com.example.liyuan.justgo.R;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

import java.util.Calendar;


public class PlanProfile extends AppCompatActivity implements View.OnClickListener{

    private final AppCompatActivity activity = PlanProfile.this;
    DataBaseHelper databaseHelper;
    private static final String TAG = "PlanProfile";
    private ImageButton btnBackP;
    private TextView txtPlanUpdate;
    private TextView txtPlanDelete;
    private plan plan;
    private location1 location;
    private EditText textViewPlanName;
    private EditText textViewBudget;
    private EditText txtDescription;
    private EditText textViewStartDate;
    private EditText textViewEndDate;
    public String locationName;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private DatePickerDialog.OnDateSetListener mDateSetListener2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plan_profile);


        btnBackP = (ImageButton)findViewById(R.id.btnBackP);
        txtPlanUpdate = (TextView) findViewById(R.id.txtPlanUpdate);
        txtPlanDelete = (TextView)findViewById(R.id.txtPlanDelete);
        textViewPlanName = (EditText)findViewById(R.id.textViewPname);
        textViewStartDate = (EditText)findViewById(R.id.textViewSdate);
        textViewEndDate = (EditText)findViewById(R.id.textViewEdate);
        textViewBudget = (EditText)findViewById(R.id.textViewPbudget);
        txtDescription = (EditText)findViewById(R.id.editTextDesc);

        Intent intent = getIntent();

        String p_name = intent.getStringExtra("P_NAME");
        String l_name = intent.getStringExtra("P_LOCATION");
        String s_date = intent.getStringExtra("S_DATE");
        String e_date = intent.getStringExtra("E_DATE");
        String stringDoubleBudget= Double.toString(getIntent().getDoubleExtra("P_BUDGET", 0));
        String p_desc = intent.getStringExtra("P_DESC");


        textViewPlanName.setText(p_name);
        textViewStartDate.setText(s_date);
        textViewEndDate.setText(e_date);
        textViewBudget.setText(stringDoubleBudget);
        txtDescription.setText(p_desc);
        locationName = l_name;

        btnBackP.setOnClickListener(this);
        txtPlanUpdate.setOnClickListener(this);
        txtPlanDelete.setOnClickListener(this);

        AutoCompleteLocation();
        calendarDates();

        databaseHelper = new DataBaseHelper(activity);
        plan = new plan();
        location = new location1();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBackP:
                startActivity(new Intent (this, PlanPage.class));
                break;
            case R.id.txtPlanUpdate:
                if(textViewPlanName.length() == 0 || locationName == null || textViewStartDate.length() == 0 ||
                        textViewEndDate.length() == 0 || textViewBudget.length() == 0 || txtDescription.length() == 0)
                {
                    Toast.makeText(this, "All fields must be filled!", Toast.LENGTH_LONG).show();
                }
                else{
                updatePlan();
                updateLocation();
                Toast.makeText(this, "Plan Updated", Toast.LENGTH_LONG).show();
                startActivity(new Intent (PlanProfile.this, PlanPage.class));}
                break;
            case R.id.txtPlanDelete:
                AlertDialog diaBox = AskForPlanDelete();
                diaBox.show();
                break;
        }
    }

    private AlertDialog AskForPlanDelete()
    {
        AlertDialog myQuittingDialogBox =new AlertDialog.Builder(this)
                .setTitle("Delete")
                .setMessage("Do you want delete this plan?")
                .setIcon(R.drawable.delete)
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        deletePlan();
                        dialog.dismiss();
                        startActivity(new Intent (PlanProfile.this, PlanPage.class));
                    }
                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        return myQuittingDialogBox;
    }

    public void deletePlan()
    {
        plan.setPlan_name(textViewPlanName.getText().toString().trim());
        databaseHelper.deletePlan(plan);
    }

    public void updatePlan()
    {
        plan.setPlan_name(textViewPlanName.getText().toString().trim());
        plan.setBudget( Double.parseDouble(textViewBudget.getText().toString().trim()));
        plan.setDescription(txtDescription.getText().toString().trim());

        databaseHelper.updatePlan(plan);
    }
    public void updateLocation()
    {
        location.setLocation_name(locationName.trim());
        location.setStart_date(textViewStartDate.getText().toString().trim());
        location.setEnd_date(textViewEndDate.getText().toString().trim());

        databaseHelper.updateLocation(location);
    }

    public void calendarDates()
    {
        //Handle start date calendar
        textViewStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get( Calendar.YEAR);
                int month = cal.get( Calendar.MONTH);
                int day = cal.get( Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog (
                        PlanProfile.this,
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
                textViewStartDate.setText(date);
            }
        };


        textViewEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get( Calendar.YEAR);
                int month = cal.get( Calendar.MONTH);
                int day = cal.get( Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog (
                        PlanProfile.this,
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
                textViewEndDate.setText(date);
            }
        };
    }

    public void AutoCompleteLocation()
    {
        PlaceAutocompleteFragment places= (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        places.setOnPlaceSelectedListener(new PlaceSelectionListener () {
            @Override
            public void onPlaceSelected(Place place) {

                locationName = place.getName().toString();
            }

            @Override
            public void onError(Status status) {

                Toast.makeText(getApplicationContext(),status.toString(), Toast.LENGTH_SHORT).show();

            }
        });
        AutocompleteFilter typeFilter = new AutocompleteFilter.Builder()
                .setTypeFilter( AutocompleteFilter.TYPE_FILTER_CITIES)
                .build();
        places.setFilter(typeFilter);
    }
}
