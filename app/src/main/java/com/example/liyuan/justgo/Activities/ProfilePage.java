package com.example.liyuan.justgo.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.liyuan.justgo.Database.DataBaseHelper;
import com.example.liyuan.justgo.Model.user;
import com.example.liyuan.justgo.R;


public class ProfilePage extends AppCompatActivity implements View.OnClickListener{

    private final AppCompatActivity activity = ProfilePage.this;
    DataBaseHelper databaseHelper;
    public EditText textViewFirstName;
    public EditText textViewLastName;
    public EditText textViewCountry;
    public EditText textViewEmail;
    public EditText textViewPassword;
    private ImageButton btnBack;
    private Button btnUpdate;
    private Button btnDelete;
    private user user;
    public TextView userid_text;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_page);


        btnBack = (ImageButton) findViewById(R.id.btnBack);
        btnUpdate = (Button)findViewById(R.id.btnUpdate);
        btnDelete = (Button)findViewById(R.id.btnDelete);

        textViewFirstName = (EditText) findViewById(R.id.textViewName);
        textViewLastName= (EditText) findViewById(R.id.textViewLastName);
        textViewCountry = (EditText)findViewById(R.id.textViewCountry);
        textViewEmail = (EditText) findViewById(R.id.textViewEmail);
        textViewPassword = (EditText) findViewById(R.id.textViewPassword);
        userid_text = (TextView) findViewById(R.id.textUserId);

        Intent intent = getIntent();

        String first_name = intent.getStringExtra("F_NAME");
        String last_name = intent.getStringExtra("L_NAME");
        String country = intent.getStringExtra("U_COUNTRY");
        String user_email = intent.getStringExtra("U_EMAIL");



        textViewFirstName.setText(first_name);
        textViewLastName.setText(last_name);
        textViewCountry.setText(country);
        textViewEmail.setText(user_email);
        String stringIntegerId= Integer.toString(getIntent().getIntExtra("U_ID",0));
        userid_text.setText(stringIntegerId);


        btnBack.setOnClickListener(this);
        btnUpdate.setOnClickListener(this);
        btnDelete.setOnClickListener(this);

        databaseHelper = new DataBaseHelper(activity);
        user = new user();

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnUpdate:
                updateProfile();
                Toast.makeText(this, "User Updated", Toast.LENGTH_LONG).show();
                startActivity(new Intent (ProfilePage.this, PlanPage.class));
                break;
            case R.id.btnBack:
                startActivity(new Intent (ProfilePage.this, PlanPage.class));
                break;
            case R.id.btnDelete:
                AlertDialog diaBox = AskForDelete();
                diaBox.show();

                break;
        }
    }

    private AlertDialog AskForDelete()
    {
        AlertDialog myQuittingDialogBox =new AlertDialog.Builder(this)
                .setTitle("Delete")
                .setMessage("Do you want delete your profile?")
                .setIcon(R.drawable.delete)
                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        deleteProfile();
                        dialog.dismiss();
                        startActivity(new Intent (ProfilePage.this, LoginPage.class));
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

    public void deleteProfile()
    {
        user.setEmail(textViewEmail.getText().toString().trim());
        databaseHelper.deleteUser(user);
    }
    public void updateProfile()
    {
        user.setFirst_name(textViewFirstName.getText().toString().trim());
        user.setLast_name(textViewLastName.getText().toString().trim());
        user.setCountry(textViewCountry.getText().toString().trim());
        user.setEmail(textViewEmail.getText().toString().trim());
        user.setPassword(textViewPassword.getText().toString().trim());

        databaseHelper.updateUser(user);
    }
}
