package com.example.laurenvoigt.hopefullyworks;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.util.Log;
import java.util.HashMap;
import java.util.Map;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.Request;
import com.android.volley.RequestQueue;

import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.content.Intent;
import android.provider.Settings;
import android.os.Build;
import android.Manifest;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private LocationManager locationManager;
    private LocationListener listener;
    private ArrayList<String> coordinates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        //Scott's stuff

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);


        listener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                //t.append("\n " + location.getLongitude() + " " + location.getLatitude());
                addPoint(location.getLongitude(), location.getLatitude());


                //posting();
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {

                Intent i = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(i);
            }
        };

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_COARSE_LOCATION,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.INTERNET}
                    ,10);
        }
        locationManager.requestLocationUpdates("gps", 5000, 0, listener);

    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        /*
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        */
        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
        //googleMap.addMarker(new MarkerOptions().position(new LatLng(30.642075, -96.317176)).title("Parking Spot 1").snippet("Not filled"));

        //Marker m2 = googleMap.addMarker(new MarkerOptions().position(new LatLng(30.642106, -96.317131)).title("Parking Spot 2").snippet("Not Filled"));
        //Marker m3 = googleMap.addMarker(new MarkerOptions().position(new LatLng(30.642137, -96.317088)).title("Parking Spot 3").snippet("Not Filled"));

        //change this to current location
        CameraPosition Liberty = CameraPosition.builder().target(new LatLng(30.642075, -96.317176)).zoom(1).bearing(0).tilt(0).build();
        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(Liberty));

    }
    public void addPoint(double lng, double lat){
        mMap.addMarker(new MarkerOptions().position(new LatLng(lng, lat)));
        Log.i("lng", Double.toString(lng));
        Log.i("lat", Double.toString(lat));
    }

    public void getSpots() {
        //final TextView mTextView = (TextView) findViewById(R.id.texttest);

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://parkngo-api.azurewebsites.net/Zone/Get";
        //String url="http://google.com";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        //mTextView.setText(response.toString());
                        //mTextView.setText("Response is: " + response.substring(0, 24));
                        //mTextView.setText("Response is: " + response.toString());
                        try {
                            JSONArray jsonArray = new JSONArray(response.toString());
                            Log.i("Get Spot Json: ",response.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //mTextView.setText("That didn't work!");
            }
        });

        queue.add(stringRequest);
    }

    public void posting(final String currentLocation){
        //final TextView mTextView = (TextView) findViewById(R.id.texttest2);
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://httpbin.org/post";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        //mTextView.setText(response.toString());
                        //mTextView.setText("Response is: " + response.toString());
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Log.d("Error.Response", error.toString());
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();
                params.put("name", currentLocation);
                params.put("domain", "http://parkngo-api.azurewebsites.net/api/values/hivalerie");

                return params;
            }
        };
        queue.add(postRequest);
    }
}

