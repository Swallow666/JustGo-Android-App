package com.example.liyuan.justgo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.liyuan.justgo.Model.searchModel;

import java.util.ArrayList;

import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.BaseSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.SearchResultListener;


public class MainActivity extends AppCompatActivity {

    private Button findButton;

    private ArrayList<searchModel> createSampleData(){
        ArrayList<searchModel> items = new ArrayList<>();
        items.add(new searchModel("Montreal"));
        items.add(new searchModel("Toronto"));
        items.add(new searchModel("Vancouver"));
        items.add(new searchModel("New York"));
        items.add(new searchModel("Shanghai"));
        items.add(new searchModel("Beijing"));
        items.add(new searchModel("Tokyo"));
        items.add(new searchModel("London"));
        items.add(new searchModel("more..."));
        return items;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findButton=(Button)findViewById(R.id.rbFind);
        buttonClickListener listener=new buttonClickListener();

        findButton.setOnClickListener(listener);

        //search button
        findViewById(R.id.searchButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new SimpleSearchDialogCompat<>(MainActivity.this, "Search Location...",
                        "Which city you wonder...?", null, createSampleData(),
                        new SearchResultListener<searchModel>() {
                            @Override
                            public void onSelected(BaseSearchDialogCompat dialog,
                                                   searchModel item, int position) {
                                Toast.makeText(MainActivity.this, item.getTitle(),
                                        Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                            }
                        }).show();
            }
        });
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
