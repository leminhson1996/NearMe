//package com.example.leminhson.nearme;
//
//import android.app.ProgressDialog;
//import android.os.AsyncTask;
//import android.util.Log;
//import android.widget.Toast;
//
//import com.google.android.gms.maps.model.LatLng;
//
//import org.json.JSONArray;
//import org.json.JSONException;
//import org.json.JSONObject;
//
//import java.io.InputStream;
//import java.util.ArrayList;
//
///**
// * Created by leminhson on 5/7/17.
// */
//
//
//public class LoadData extends AsyncTask<Void, Void, Void> {
//
//    private static String url = "https://leminhson1996.github.io/EcommerceGroup1/NearMe.json";
//    ArrayList<Model> categories;
//    private ProgressDialog pDialog;
//    private String TAG = HomeActivity.class.getSimpleName();
//    private ProgressDialog pDialog;
//
//    @Override
//    protected Void doInBackground(Void... arg0) {
//        HttpHandler sh = new HttpHandler();
//
//        // Making a request to url and getting response
//        String jsonStr = sh.makeServiceCall(url);
//
//        Log.d("HomeActivity", "Response from url: " + jsonStr);
//
//        if (jsonStr != null) {
//            try {
//                JSONObject jsonObj = new JSONObject(jsonStr);
//
//                // Getting JSON Array node
//                JSONArray categoriesJSON = jsonObj.getJSONArray("Categories");
//                JSONArray categoryJSON = jsonObj.getJSONArray("Categories");
//                categories = new ArrayList<Model>();
//                // looping through All Contacts
//                for (int i = 0; i < categoriesJSON.length(); i++) {
//                    JSONObject c = categoriesJSON.getJSONObject(i);
//                    JSONArray itemsJSON = c.getJSONArray("items");
//
//                    String imageLink = c.getString("imageLink");
//                    String title = c.getString("title");
////                    Double latitude = c.getDouble("latitude");
////                    Double longtitude = c.getDouble("longtitude");
//
//                    Model category = new Model(title);
//                    category.setImageLink(imageLink);
//                    category.setTitle(title);
//                    category.setCounter(itemsJSON.length() + "");
//
//                    // adding contact to contact list
//                    categories.add(category);
//                }
//            } catch (final JSONException e) {
//                Log.d("HomeActivity", "Json parsing error: " + e.getMessage());
//
//            }
//        } else {
//            Log.d("HomeActivity", "Couldn't get json from server.");
//
//        }
//
//        return null;
//    }
//
//    @Override
//    protected void onPostExecute(Void result) {
//        super.onPostExecute(result);
//        // Dismiss the progress dialog
//        if (pDialog.isShowing())
//            pDialog.dismiss();
//        /**
//         * Updating parsed JSON data into ListView
//         * */
//
//    }
//}