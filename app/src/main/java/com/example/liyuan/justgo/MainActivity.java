package com.example.liyuan.justgo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;



public class MainActivity extends AppCompatActivity {

    private Button findButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findButton=(Button)findViewById(R.id.rbFind);
        buttonClickListener listener=new buttonClickListener();

        findButton.setOnClickListener(listener);
    }

    class  buttonClickListener implements View.OnClickListener{

        @Override
        public void onClick(View v) {
            Intent intent=new Intent();
            intent.setClass(MainActivity.this,MapsActivity.class);
            startActivity(intent);
        }
    }
}
