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

import com.example.liyuan.justgo.Database.DataBaseHelper;
import com.example.liyuan.justgo.Model.user;
import com.example.liyuan.justgo.R;


public class RegisterPage extends AppCompatActivity implements View.OnClickListener {

    private final AppCompatActivity activity = RegisterPage.this;

    private EditText txtFirstName;
    private EditText txtLastName;
    private EditText txtCountry;
    private Button btnRegister;
    private EditText txtEmail;
    private EditText txtPassword;
    private EditText txtRetype;
    public static String SuccessMessage = "Account created!";
    public static String ErrorMessage = "Email already registered!";
    private DataBaseHelper databaseHelper;
    private user user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        txtFirstName = (EditText)findViewById(R.id.txtFirstName);
        txtLastName = (EditText)findViewById(R.id.txtLastName);
        txtCountry = (EditText)findViewById(R.id.txtCountry);
        btnRegister = (Button)findViewById(R.id.btnRegister);
        txtEmail = (EditText)findViewById(R.id.txtEmail);
        txtPassword = (EditText)findViewById(R.id.txtPassword);
        txtRetype = (EditText)findViewById(R.id.txtRetype);

        btnRegister.setOnClickListener(this);

        databaseHelper = new DataBaseHelper(activity);
        user = new user();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRegister:
                validationForm();
                break;
        }
    }

    public void validationForm(){
        txtEmail.setError(null);
        txtPassword.setError(null);
        String userEmail = txtEmail.getText().toString();
        String userPassword = txtPassword.getText().toString();
        String userRetype = txtRetype.getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

        if(userEmail.isEmpty() || userPassword.isEmpty())
        {
            Toast.makeText(this, "Email and Password are Required!", Toast.LENGTH_LONG).show();
        }
        else if(!userEmail.matches(emailPattern))
        {
            Toast.makeText(this, "Please check the Email format", Toast.LENGTH_LONG).show();
            txtEmail.requestFocus();
        }
        else if(!userRetype.matches(userPassword))
        {
            Toast.makeText(this, "Both passwords must be the same", Toast.LENGTH_LONG).show();
            txtRetype.requestFocus();
        }
        else if (!databaseHelper.checkEmail(txtEmail.getText().toString().trim())) {

            user.setFirst_name(txtFirstName.getText().toString().trim());
            user.setLast_name(txtLastName.getText().toString().trim());
            user.setCountry(txtCountry.getText().toString().trim());
            user.setEmail(txtEmail.getText().toString().trim());
            user.setPassword(txtPassword.getText().toString().trim());

            databaseHelper.addUser(user);

            Toast.makeText(this, SuccessMessage, Toast.LENGTH_LONG).show();

            startActivity(new Intent (this, LoginPage.class));

        } else {
            Toast.makeText(this, ErrorMessage, Toast.LENGTH_LONG).show();
        }

    }

}




