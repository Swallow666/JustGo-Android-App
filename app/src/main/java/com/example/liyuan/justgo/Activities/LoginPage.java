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
import com.example.liyuan.justgo.MainActivity;
import com.example.liyuan.justgo.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class LoginPage extends AppCompatActivity implements View.OnClickListener{


    private final AppCompatActivity activity = LoginPage.this;
    private EditText txtEmail;
    private EditText txtPassword;
    private Button btnSignIn;
    private TextView txtReg;
    private DataBaseHelper databaseHelper;
    public static final String MY_PREF_NAME = "MyProfile";
    GoogleSignInClient mGoogleSignInClient;
    SignInButton button;
    int RC_SIGN_IN = 99;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        btnSignIn = (Button)findViewById(R.id.btnSignIn);
        txtReg = (TextView)findViewById(R.id.txtReg);
        txtEmail = (EditText)findViewById(R.id.txtEmail);
        txtPassword = (EditText)findViewById(R.id.txtRetype);


        btnSignIn.setOnClickListener(this);
        txtReg.setOnClickListener(this);

        databaseHelper = new DataBaseHelper(activity);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        button = findViewById(R.id.GoogleLogin);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        } catch (ApiException e) {
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnSignIn:
                validationFields();
                break;
            case R.id.txtReg:
                startActivity(new Intent (this, RegisterPage.class));
                break;
        }
    }

    public void validationFields(){
        txtEmail.setError(null);
        txtPassword.setError(null);
        String userEmail = txtEmail.getText().toString();
        String userPassword = txtPassword.getText().toString();


        if(userEmail.isEmpty() || userPassword.isEmpty())
        {
            Toast.makeText(this, "Email and Password are Required!", Toast.LENGTH_LONG).show();
        }

        else if (databaseHelper.checkUser(txtEmail.getText().toString().trim()
                , txtPassword.getText().toString().trim())) {

            SharedPreferences settings = getSharedPreferences(MY_PREF_NAME, 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("CUR_EMAIL", userEmail);
            editor.commit();
            txtEmail.setText(null);
            txtPassword.setText(null);
            startActivity(new Intent (LoginPage.this, MainActivity.class));
        }
        else
        {
            Toast.makeText(this, "Email or Password invalid!", Toast.LENGTH_LONG).show();
        }
    }

}
