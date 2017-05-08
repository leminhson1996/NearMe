package com.example.leminhson.nearme;

import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import android.os.Handler;
import android.widget.Toast;

import java.util.Locale;
import java.util.concurrent.RunnableFuture;
import java.util.logging.LogRecord;

import static java.lang.System.in;

public class HomeActivity extends AppCompatActivity {
    private String TAG = HomeActivity.class.getSimpleName();
    public static final String EXTRA_MESSAGE = "categoryTitle";
    ArrayList<Model> categories;
    private ListView listView;
    public HomeAdapter adapter;
    private ProgressDialog pDialog;
    private final int REQ_CODE_SPEECH_INPUT = 100;
    // URL to get contacts JSON
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        categories = new ArrayList<Model>();

        listView = (ListView)findViewById(R.id.listview);
        new LoadData().execute();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
                String category = categories.get(position).getTitle();
                Intent intent = new Intent(HomeActivity.this, CategoryDetail.class);
                intent.putExtra(EXTRA_MESSAGE, category);
                startActivity(intent);
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class LoadData extends AsyncTask<Void, Void, Void> {

        private String url = "https://leminhson1996.github.io/EcommerceGroup1/NearMe.json";
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            // Showing progress dialog
            pDialog = new ProgressDialog(HomeActivity.this);
            pDialog.setMessage("Please wait...");
            pDialog.setCancelable(false);
            pDialog.show();

        }

        @Override
        protected Void doInBackground(Void... arg0) {
            HttpHandler sh = new HttpHandler();

            // Making a request to url and getting response
            String jsonStr = sh.makeServiceCall(url);

            Log.d("HomeActivity", "Response from url: " + jsonStr);

            if (jsonStr != null) {
                try {
                    JSONObject jsonObj = new JSONObject(jsonStr);

                    // Getting JSON Array node
                    JSONArray categoriesJSON = jsonObj.getJSONArray("Categories");
                    JSONArray categoryJSON = jsonObj.getJSONArray("Categories");
                    // looping through All Contacts
                    for (int i = 0; i < categoriesJSON.length(); i++) {
                        JSONObject c = categoriesJSON.getJSONObject(i);
                        JSONArray itemsJSON = c.getJSONArray("items");

                        String imageLink = c.getString("imageLink");
                        String title = c.getString("title");
//                    Double latitude = c.getDouble("latitude");
//                    Double longtitude = c.getDouble("longtitude");

                        Model category = new Model(title);
                        category.setImageLink(imageLink);
                        category.setTitle(title);
                        category.setCounter(itemsJSON.length() + "");

                        // adding contact to contact list
                        categories.add(category);
                    }
                } catch (final JSONException e) {
                    Log.d("HomeActivity", "Json parsing error: " + e.getMessage());

                }
            } else {
                Log.d("HomeActivity", "Couldn't get json from server.");

            }

            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            // Dismiss the progress dialog
            if (pDialog.isShowing())
                pDialog.dismiss();

            String link = categories.get(0).getImageLink();
            adapter = new HomeAdapter(HomeActivity.this, categories);
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run()
                {
                    listView.setAdapter(adapter);
                }
            }, 1000);
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
                    for (Model category: categories){
                        if (category.getTitle().toLowerCase().equals(result.get(0).toLowerCase())){
                            String title = category.getTitle();
                            Intent intent = new Intent(HomeActivity.this, CategoryDetail.class);
                            intent.putExtra(EXTRA_MESSAGE, title);
                            startActivity(intent);
                        }
                    }
                }
                break;
            }

        }
    }
}