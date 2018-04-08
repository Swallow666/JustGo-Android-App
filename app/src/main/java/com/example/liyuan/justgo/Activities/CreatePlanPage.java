/**
 * Created by Fernando Pereira Borges
 * Assignment2 - Mobile Development 2
 * Date: November 03, 2017
 * Purpose: Database implementation - user Database
 */
package com.example.liyuan.justgo.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.liyuan.justgo.R;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.AutocompleteFilter;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;

public class CreatePlanPage extends AppCompatActivity implements View.OnClickListener{


    private EditText txtPlanName;
    public String cityLocation;
    private Button btnNext;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_plan_page);

        txtPlanName = (EditText)findViewById(R.id.txtPlanName);
        btnNext = (Button)findViewById(R.id.btnGoCreate);


        btnNext.setOnClickListener(this);

        PlaceAutocompleteFragment places= (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        places.setOnPlaceSelectedListener(new PlaceSelectionListener () {
            @Override
            public void onPlaceSelected(Place place) {

                Toast.makeText(getApplicationContext(),"you chose "+place.getName(), Toast.LENGTH_SHORT).show();
                cityLocation = place.getName().toString();
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

    public void sendToMap(View view) {
        Intent intent = new Intent (this, MapPage.class);
        startActivity(intent);
    }

    public void ValidateCreatePlan1()
    {
        String planName = txtPlanName.getText().toString();
       // String locationName = txtLocation.getText().toString();

        if(planName.isEmpty() || cityLocation == null)
        {
            Toast.makeText(this, "Please enter Plan name and location name", Toast.LENGTH_LONG).show();
        }
        else
        {
            Intent nextPage = new Intent (CreatePlanPage.this, CreatePlan2.class);
            nextPage.putExtra("PLAN_NAME", planName.trim());
            //nextPage.putExtra("LOCATION_NAME", locationName.trim());
            nextPage.putExtra("CITY_NAME", cityLocation.trim());
            startActivity(nextPage);
        }
    }
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnGoCreate:
                ValidateCreatePlan1();
                break;
        }
    }
}

