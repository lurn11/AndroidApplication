package com.example.laurenvoigt.hopefullyworks;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import android.content.Intent;
import com.android.volley.toolbox.HttpHeaderParser;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;



public class Main2Activity extends AppCompatActivity {
    static EditText submitFirstName;
    static EditText submitLastName;
    static EditText submitUserID;
    static EditText submitLicensePlate;
    static EditText submitRegisteredState;
    static EditText submitMake;
    static EditText submitModel;
    static EditText submitColor;
    static EditText submitTag;
    Button submitForm;
    static String info;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        submitFirstName = (EditText) findViewById(R.id.firstname);
        submitLastName = (EditText) findViewById(R.id.lastname);
        submitLicensePlate = (EditText) findViewById(R.id.licensePlate);
        submitUserID = (EditText) findViewById(R.id.userid);
        submitRegisteredState = (EditText) findViewById(R.id.registeredstate);
        submitMake = (EditText) findViewById(R.id.make);
        submitModel = (EditText) findViewById(R.id.model);
        submitColor = (EditText) findViewById(R.id.color);
        submitTag = (EditText) findViewById(R.id.tag);
        submitForm = (Button) findViewById(R.id.button_submit);

        submitForm.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //Changed stuff here from MainActivity to Userprofile
                //Main2Activity.class
                try {
                    submitUserInfo();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Intent int1 = new Intent(Main2Activity.this, UserProfile.class);
                startActivity(int1);
            }
        });

    }

    public void submitUserInfo() throws JSONException {
        String firstname = submitFirstName.getText().toString();
        String lastname = submitLastName.getText().toString();
        String licensePlate = submitLicensePlate.getText().toString();
        String userid = submitUserID.getText().toString();
        String registeredstate = submitRegisteredState.getText().toString();
        String make = submitMake.getText().toString();
        String model = submitModel.getText().toString();
        String color = submitColor.getText().toString();
        String tagId = submitTag.getText().toString();


        //formatted correctly?
        //info = "{" + "firstname:" + firstname, "lastname: " + lastname, "userid: " + userid, "licensePlateNumber: " + licensePlateNumber, "make: " + make, "model: " + model,"color: " +color, "registeredsdate: " + registeredstate + "}";


        //JSONParser parser = new JSONParser();
        //JSONObject json = (JSONObject) parser.parse(stringToParse);
        JSONObject vehicalObj = new JSONObject();
        vehicalObj.put("licensePlate", licensePlate);
        vehicalObj.put("registeredState", registeredstate);
        vehicalObj.put("make", make);
        vehicalObj.put("model", model);
        vehicalObj.put("color", color);


        JSONObject userObj = new JSONObject();
        userObj.put("firstname", firstname);
        userObj.put("lastname", lastname);
        //userObj.put("facebookId", getIntent().getParcelableExtra("FacebookId"));
        userObj.put("facebookId", "1234");
        userObj.put("tagId",tagId);
        userObj.put("vehicle", vehicalObj);



        info = userObj.toString();
        Log.i("Json shit", info);
        posting();
        Intent intent = new Intent(this, UserProfile.class);
        startActivity(intent);
    }
        //Give this information to Valerie and her database

    public void posting(){
        //final TextView mTextView = (TextView) findViewById(R.id.texttest2);
        RequestQueue queue = Volley.newRequestQueue(this);
        //String url = "http://parkngo-api.azurewebsites.net/Signup/Facebook";
        String url = "http://192.168.1.13:57494/Signup/Facebook";
        try {
            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // response
                            Log.i("Response", response.toString());
                            //mTextView.setText(response.toString());
                            //mTextView.setText("Response is: " + response.toString());
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // error
                            Log.d("Error.Response", error.toString());
                        }
                    }
            ) {

                @Override
                //Is this right?
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    Log.i("Right before put:", info);
                    params.putAll(info);
                    //params.put("domain", "http://parkngo-api.azurewebsites.net/api/values/hivalerie");

                    return params;
                }


                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    HashMap<String, String> headers = new HashMap<String, String>();
                    headers.put("Content-Type", "application/json; charset=utf-8");
                    //headers.put("Content-Type", "application/json");
                    return headers;
                }



                /*
                @Override
                public String getBodyContentType() {
                    return "application/json";
                }


                public void onErrorResponse(VolleyError error) {

                    // As of f605da3 the following should work
                    NetworkResponse response = error.networkResponse;
                    if (error instanceof ServerError && response != null) {
                        try {
                            String res = new String(response.data,
                                    HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                            // Now you can use any deserializer to make sense of data
                            JSONObject obj = new JSONObject(res);
                        } catch (UnsupportedEncodingException e1) {
                            // Couldn't properly decode data to string
                            e1.printStackTrace();
                        } catch (JSONException e2) {
                            // returned data is not JSONObject?
                            e2.printStackTrace();
                        }
                    }
                }
                */
                /*
                @Override
                public byte[] getBody() {
                    return info.getBytes();
                }

                @Override
                public String getBodyContentType() {
                    return "application/json; charset=utf-8";
                }
                */


            };
            queue.add(postRequest);
        }
        catch (Exception e){
            Log.i("Server Error", "caught");
        }
    }



}

