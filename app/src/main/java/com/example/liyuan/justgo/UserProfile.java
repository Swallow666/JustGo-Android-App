package com.example.liyuan.justgo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.liyuan.justgo.Activities.LoginPage;
import com.example.liyuan.justgo.Activities.PlanPage;
import com.example.liyuan.justgo.Activities.RegisterPage;

public class UserProfile extends AppCompatActivity {

    private Button sinin;
    private Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate ( savedInstanceState );
        setContentView ( R.layout.activity_user_profile );

        sinin=(Button)findViewById(R.id.SignIn);
        register=(Button)findViewById ( R.id.Register );

        sinin.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v) {
                Intent intent=new Intent(UserProfile.this,LoginPage.class);
                startActivity(intent);
            }
        });

        register.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v) {
                Intent intent=new Intent(UserProfile.this,RegisterPage.class);
                startActivity(intent);
            }
        });
    }
}
