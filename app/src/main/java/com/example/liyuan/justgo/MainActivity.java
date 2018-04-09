package com.example.liyuan.justgo;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import android.widget.Toast;

import com.example.liyuan.justgo.Activities.LoginPage;
import com.example.liyuan.justgo.Activities.PlanPage;
import com.example.liyuan.justgo.Activities.RegisterPage;
import com.example.liyuan.justgo.Model.searchModel;

import java.util.ArrayList;

import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.BaseSearchDialogCompat;
import ir.mirrajabi.searchdialog.core.SearchResultListener;

import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;



public class MainActivity extends AppCompatActivity {

    private Button find;
    private Button plan;
    private Button me;

    private static final int ACCESS_FINE_LOCATION = 1;
    private static ArrayList<Place> arrayOfPlaces = new ArrayList<Place>();
    private static PlacesAdapter adapter = null;
    private static Location currentLocation = null;

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
        Toolbar myToolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(myToolbar);


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

        getLocationPermission();

        // Acquire a reference to the system Location Manager
        LocationManager locationManager = (LocationManager) this.getSystemService( Context.LOCATION_SERVICE);

        // Define a listener that responds to location updates
        LocationListener locationListener = new LocationListener() {

            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.
                currentLocation = location;
                Double lat = location.getLatitude();
                Double lng = location.getLongitude();

                //Log.d("Location", lat.toString() + ", " + lng.toString());


                AsyncHttpClient client = new AsyncHttpClient();
                client.get("https://maps.googleapis.com/maps/api/place/textsearch/json?query=tourist+attraction&location=" + lat.toString() + "," + lng.toString() + "&rankby=distance&language=en&key=AIzaSyDX5qCv5dhVzcmJ6xutbfiWk31o2KoQsps", new JsonHttpResponseHandler () {

                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        //Log.d("JSON", response.toString());
                        adapter.clear();
                        arrayOfPlaces = new ArrayList<Place>();
                        try {
                            JSONArray resultsArray = response.getJSONArray("results");

                            for(int i = 0; i < resultsArray.length();i++){

                                //Log.d("ID", resultsArray.getJSONObject(i).get("id").toString());

                                double placeLat = Double.parseDouble(resultsArray.getJSONObject(i).getJSONObject("geometry").getJSONObject("location").get("lat").toString());
                                double placeLng = Double.parseDouble(resultsArray.getJSONObject(i).getJSONObject("geometry").getJSONObject("location").get("lng").toString());
                                Location locationPlace = new Location("");
                                locationPlace.setLatitude(placeLat);
                                locationPlace.setLongitude(placeLng);

                                double distance = locationPlace.distanceTo(currentLocation)*0.000621371192;;

                                String refPhoto;
                                String isOpen;
                                double rating;

                                if(resultsArray.getJSONObject(i).has("photos")){
                                    refPhoto = resultsArray.getJSONObject(i).getJSONArray("photos").getJSONObject(0).getString("photo_reference");
                                } else {
                                    refPhoto = "test";
                                }


                                if(resultsArray.getJSONObject(i).has("opening_hours")) {
                                    if (resultsArray.getJSONObject(i).getJSONObject("opening_hours").get("open_now").toString().equals("true")) {
                                        isOpen = "Open Now";
                                    } else {
                                        isOpen = "Closed";
                                    }
                                } else {
                                    isOpen = "Unavaliable";
                                }

                                if(resultsArray.getJSONObject(i).has("rating")){
                                    rating = Double.valueOf(resultsArray.getJSONObject(i).get("rating").toString());
                                } else {
                                    rating = 0;
                                }


                                Place place = new Place(resultsArray.getJSONObject(i).get("place_id").toString(), resultsArray.getJSONObject(i).get("name").toString(), rating, refPhoto, distance, isOpen);
                                arrayOfPlaces.add(place);
                            }
                            adapter.addAll(arrayOfPlaces);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }


                    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                        Toast.makeText(getApplicationContext(), "Error grabbing nearby places", Toast.LENGTH_SHORT).show();
                    }
                });

            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

        public void onProviderDisabled(String provider) {
        }
        };

        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 60000, 500, locationListener);
            // Construct the data source
            // Create the adapter to convert the array to views
            adapter = new PlacesAdapter(this, arrayOfPlaces);
            // Attach the adapter to a ListView
            ListView listView = (ListView) findViewById(R.id.listViewPlaces);
            listView.setAdapter(adapter);


            listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Place item = adapter.getItemAtPosition(position);

                    // Log.d("Place", item.getName());

                    Intent intent = new Intent(MainActivity.this, PlaceActivity.class);
                    //based on item add info to intent
                    intent.putExtra("place_id", item.getId());
                    startActivity(intent);
                }

            });
        } catch (SecurityException e){

        }
        //registerReceiver(gpsReciever, new IntentFilter("android.location.PROVIDERS_CHANGED"));

        find=(Button)findViewById(R.id.rbFind);
        plan=(Button)findViewById(R.id.rbPlan);
        me=(Button)findViewById ( R.id.rbMine );

        find.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,MapsActivity.class);
                startActivity(intent);
            }
        });

        plan.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,PlanPage.class);
                startActivity(intent);
            }
        });

        me.setOnClickListener(new View.OnClickListener(){

            public void onClick(View v) {
                Intent intent=new Intent(MainActivity.this,LoginPage.class);
                startActivity(intent);
            }
        });
    }

    private void getLocationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                        ACCESS_FINE_LOCATION);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_bookmarks:
                Intent intent = new Intent(MainActivity.this, BookmarksActivity.class);
                startActivity(intent);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        // Make sure it's our original READ_CONTACTS request
        if (requestCode == ACCESS_FINE_LOCATION) {
            if (grantResults.length == 1 &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            }
        }
    }



}
