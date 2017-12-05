package com.example.laurenvoigt.hopefullyworks;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.Request;
import com.android.volley.RequestQueue;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity{
    CallbackManager callbackManager;
    public boolean result;
    public String facebookId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Scott's stuff here
        //super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);
        //testFunc();
        //posting();


        callbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions("email");
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                getUserDetails(loginResult);
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);


        /*
        super.onActivityResult(requestCode, resultCode, data);
        android.app.Fragment fragment = getFragmentManager().findFragmentById(R.id.fragment_blank);
        fragment.onActivityResult(requestCode, resultCode, data);
        */
    }

    protected void getUserDetails(final LoginResult loginResult) {
        GraphRequest data_request = GraphRequest.newMeRequest(
                loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(
                            JSONObject json_object,
                            GraphResponse response) {
                        if(getLogin() == true){

                            Intent intent = new Intent(MainActivity.this, UserProfile.class);
                            //intent.putExtra("userProfile", json_object.toString());
                            startActivity(intent);
                        }
                        else {
                            Intent intent = new Intent(MainActivity.this, Main2Activity.class);

                            intent.putExtra("FacebookId", facebookId);
                            startActivity(intent);
                        }

                    }

                });
        Bundle permission_param = new Bundle();
        permission_param.putString("fields", "id,name,email,picture.width(120).height(120)");
        data_request.setParameters(permission_param);
        data_request.executeAsync();

    }

    protected void onResume() {
        super.onResume();
        // Logs 'install' and 'app activate' App Events.
        AppEventsLogger.activateApp(this);
    }
    @Override
    protected void onPause() {
        super.onPause();
        // Logs 'app deactivate' App Event.
        AppEventsLogger.deactivateApp(this);
    }

    /*
    public void testFunc() {
        final TextView mTextView = (TextView) findViewById(R.id.texttest);

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
                        mTextView.setText(response.toString());
                        //mTextView.setText("Response is: " + response.substring(0, 24));
                        mTextView.setText("Response is: " + response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                mTextView.setText("That didn't work!");
            }
        });

        queue.add(stringRequest);
    }

    public void posting(){
        final TextView mTextView = (TextView) findViewById(R.id.texttest2);
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://httpbin.org/post";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        mTextView.setText(response.toString());
                        mTextView.setText("Response is: " + response.toString());
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
                params.put("name", "ParkingSpot");
                params.put("domain", "http://parkngo-api.azurewebsites.net/api/values/hivalerie");

                return params;
            }
        };
        queue.add(postRequest);
    }
    */

    //returning a string?
    //Is this a post or a get?
    //200 vs. 500
    public boolean getLogin() {
        Log.i("fuck","fuck");
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
        //String url = "http://parkngo-api.azurewebsites.net/Login/Facebook/" + "1234";
        String url = "http://192.168.1.13:57494/Login/Facebook/" + "1234";
        //String url="http://google.com";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.i("Response", response.toString());
                        // Display the first 500 characters of the response string.
                        //mTextView.setText(response.toString());
                        //mTextView.setText("Response is: " + response.substring(0, 24));
                        //mTextView.setText("Response is: " + response.toString());
                        try {
                            JSONArray jsonArray = new JSONArray(response.toString());
                            Log.i("Get Login Info: ",response.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.i("Response Error: " , error.toString());
            }
        });

        queue.add(stringRequest);
        return result;
    }


}