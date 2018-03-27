package com.example.liyuan.justgo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class StartActivity extends AppCompatActivity {

    private Button viewVacations;
    private Button createVacation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView( R.layout.activity_start);

        PlanActivity.vacations = FileSaver.readVacationList();

        // Assign the button fields we created in Java to the button we made in the layout xml file
        viewVacations = (Button)findViewById(R.id.view_vacations_btn);
        createVacation = (Button)findViewById(R.id.create_vacation_btn);

        viewVacations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (StartActivity.this, PlanActivity.class);
                startActivity(intent);
            }
        });


        /* This registers the click of the createVacation button and sends the user to the vacation
           activity where they can create a new vacation
         */
        createVacation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (StartActivity.this, VacationActivity.class);
                startActivity(intent);
            }
        });




    }


    /**
     * This method is used when the user exits the application, so that the vacations can be written out
     * to external storage before the app gets closed
     */
    @Override
    protected void onPause() {
        super.onPause();
        FileSaver.writeVacationList(PlanActivity.vacations);

    }
}
