package com.example.liyuan.justgo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.liyuan.justgo.Model.searchModel;

import java.io.IOException;
import java.util.ArrayList;

import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.BaseSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.SearchResultListener;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;


public class MainActivity extends AppCompatActivity {

    private Button findButton;
    private TextView result;
    private Button festivalButton;
    private WebView wv;
    String url = "https://en.wikipedia.org/w/index.php?title=Special:Search&search=festivals+in+";

    private String Location = "Montreal";

    private ArrayList<searchModel> createSampleData() {
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

        findButton = (Button) findViewById(R.id.rbFind);
        buttonClickListener listener = new buttonClickListener();

        result = (TextView) findViewById(R.id.result);
        findButton.setOnClickListener(listener);

        festivalButton = (Button) findViewById(R.id.festivalButton);
        festivalButton.setOnClickListener(listener);

        wv = (WebView) findViewById(R.id.Web);

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
                                Location = item.getTitle();
                            }
                        }).show();

            }
        });
        festivalButton.setText("Festivals in "+Location+" by Wikipedia");
        findViewById(R.id.festivalButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyBrowser my = new MyBrowser();
                my.shouldOverrideUrlLoading(wv,url+Location);
            }
        }) ;

        };
        //getWebsite();




    private void getWebsite(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                String temp="China";
                final StringBuilder builder=new StringBuilder();
                try{
                    Document doc=Jsoup.connect("https://en.wikipedia.org/wiki/List_of_festivals_in_"+temp).get();
                    String title=doc.title();
                    Elements links=doc.select("div#bodycontent");
                    builder.append(title).append("\n");
                    for(Element link:links){
                        builder.append("\n").append("Link: ").append(link.attr("href"))
                                .append("\n").append("Text: ").append(link.text());
                    }

                }catch (IOException e){
                        builder.append("Error: ").append(e.getMessage()).append("\n");
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        result.setText(builder.toString());
                    }
                });
            }
        }).start();
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
