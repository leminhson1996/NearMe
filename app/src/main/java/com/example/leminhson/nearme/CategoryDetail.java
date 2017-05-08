package com.example.leminhson.nearme;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.nearby.messages.EddystoneUid;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Locale;

public class CategoryDetail extends AppCompatActivity {
    public DetailAdapter adapter;
    ArrayList<Item> places;
    ArrayList<Item> changes;
    ListView listView;
    Button NearestButton;
    private ProgressDialog pDialog;
    public String message;
    private Location myLocation;
    private final int REQ_CODE_SPEECH_INPUT = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        NearestButton = (Button)findViewById(R.id.nearestButton);
        Button RadiusButton = (Button)findViewById(R.id.radiusButton);
        final EditText inputRadius = (EditText) findViewById(R.id.inputRadius);

        places = new ArrayList<Item>();
        listView = (ListView) findViewById(R.id.listview);

        new LoadData().execute();
        final Intent intent = getIntent();
        message = intent.getStringExtra(HomeActivity.EXTRA_MESSAGE);

        final ListView listView = (ListView) findViewById(R.id.listview);

        //get your locaion
        LocationManager locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);
        Criteria criteria = new Criteria();

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        myLocation = locationManager.getLastKnownLocation(locationManager
                .getBestProvider(criteria, false));




        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                LatLng latLng = changes.get(position).getPosition();
                ArrayList<String> infor = new ArrayList<String>();
                infor.add(latLng.latitude + "");
                infor.add(latLng.longitude + "");
                infor.add(changes.get(position).getPhone());
                Intent intent = new Intent(CategoryDetail.this, MapsActivity.class);
                intent.putExtra("INFO", infor);
                startActivity(intent);
            }
        });

        NearestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Item nearestPlace = places.get(0);
                for(Item place: places){
                    if (Integer.parseInt(place.getCounter()) < Integer.parseInt(nearestPlace.getCounter())){
                        nearestPlace = place;
                    }
                }

                changes.removeAll(changes);
                changes.add(nearestPlace);
                adapter.notifyDataSetChanged();
            }
        });

        RadiusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                float[] results = new float[1];
                changes.removeAll(changes);
                float radius = Float.parseFloat(inputRadius.getText().toString());
                for(Item place: places){
                    float delta = Float.parseFloat(place.getCounter()) - radius;
                    if (delta <= 0.0){
                        changes.add(place);
                    }
                };
                adapter.notifyDataSetChanged();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });
    }

    public class LoadData extends AsyncTask<Void, Void, Void> {

        private String url = "https://leminhson1996.github.io/EcommerceGroup1/NearMe.json";
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(CategoryDetail.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            Log.d("CategoryDetail", "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray categoriesJSON = jsonObj.getJSONArray("Categories");
                    JSONArray categoryJSON = jsonObj.getJSONArray("Categories");
                    // looping through All Contacts
                    for (int i = 0; i < categoriesJSON.length(); i++) {
                        JSONObject c = categoriesJSON.getJSONObject(i);

                        if(c.getString("title").equals(message)){
                            JSONArray itemsJSON = c.getJSONArray("items");
                            for (int j = 0; j < itemsJSON.length(); j++)
                            {
                                JSONObject place = itemsJSON.getJSONObject(j);
                                String placetitle = place.getString("title");
                                Double placelatitude = place.getDouble("latitude");
                                Double placelongtitude = place.getDouble("longtitude");
                                String phone = place.getString("phone");

                                Item item = new Item(placetitle);
                                LatLng latLng = new LatLng(placelatitude, placelongtitude);
                                item.setPosition(latLng);
                                item.setPhone(phone);

                                // find distance from item to your position
                                float[] results = new float[1];
                                Location.distanceBetween(myLocation.getLatitude(), myLocation.getLongitude(),
                                        placelatitude, placelongtitude,
                                        results);

                                item.setCounter(Math.round(results[0]/1000) + "");
                                item.setImageLink(c.getString("imageLink"));
                                places.add(item);

                            }
                        }

                    }
                } catch (final JSONException e) {
                    Log.d("CategoryDetail", "Json parsing error: " + e.getMessage());

                }
            } else {
                Log.d("CategoryDetail", "Couldn't get json from server.");

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();
            changes = new ArrayList<Item>(places);
            adapter = new DetailAdapter(CategoryDetail.this, changes);
            listView.setAdapter(adapter);
        }
    }

    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    for (Item item: changes){
                        if (item.getTitle().toLowerCase().equals(result.get(0).toLowerCase())){
                            LatLng latLng = item.getPosition();
                            ArrayList<String> infor = new ArrayList<String>();
                            infor.add(latLng.latitude + "");
                            infor.add(latLng.longitude + "");
                            infor.add(item.getPhone());
                            Intent intent = new Intent(CategoryDetail.this, MapsActivity.class);
                            intent.putExtra("INFO", infor);
                            startActivity(intent);
                        }
                    }

                    if (result.get(0).toLowerCase().equals("gần nhất")){
                        NearestButton.performClick();
                    }
                }
                break;
            }

        }
    }
}