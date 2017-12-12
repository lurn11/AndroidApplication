package com.example.laurenvoigt.hopefullyworks;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class HttpURLConnectionExample extends AsyncTask<String, Void, String> {
    private final String USER_AGENT = "Mozilla/5.0";
    @Override
    protected String doInBackground(String... params) {
        try {
            //need to test if these are given
            String url = params[0];
            String jsonString = params[1];
            String httpType = params[2];

            HttpURLConnectionExample http = new HttpURLConnectionExample();
            if (params[2] == "get") {
                System.out.println("Testing 1 - Send Http GET request");
                return http.sendGet(url,jsonString);
            }
            if (params[2] == "post") {
                System.out.println("\nTesting 2 - Send Http POST request");
                http.sendPost(url, jsonString);
            }
        }
        catch (Exception e){
            Log.i("do it background error:",e.getMessage());
            e.printStackTrace();
        }
        return "";
    }

    // HTTP GET request
    public String sendGet(String url, String JsonString)  {

        try {
            URL obj = new URL(url + "/" + JsonString);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            // optional default is GET
            con.setRequestMethod("GET");

            //add request header
            con.setRequestProperty("User-Agent", USER_AGENT);

            int responseCode = con.getResponseCode();
            Log.i("Sending 'GET' to URL : ", url);
            //Log.i("Response Code : " , (sresponseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            //print result
            Log.i("Get Result: ", response.toString());
            return response.toString();
        }
        catch (ProtocolException e){
            Log.i("exception: ", e.getMessage());
        }
        catch (MalformedURLException e){
            Log.i("exception: ", e.getMessage());
        }
        catch (IOException e){
            Log.i("exception: ", e.getMessage());
        }
        return "";
    }

    // HTTP POST request
    public void sendPost(String url, String jsonString){
        try {

            Log.i("request url:", url);
            URL obj = new URL(url);
            HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

            //add reuqest header
            con.setRequestMethod("POST");
            con.setRequestProperty("User-Agent", USER_AGENT);
            con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
            con.setRequestProperty("Content-type", "application/json");
            String urlParameters = jsonString;

            // Send post request
            con.setDoOutput(true);
            DataOutputStream wr = new DataOutputStream(con.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.flush();
            wr.close();

            int responseCode = con.getResponseCode();
            Log.i("Sending 'POST'request: ", url);
            System.out.println("Post parameters : " + urlParameters);
            System.out.println("Response Code : " + responseCode);

            BufferedReader in = new BufferedReader(
                    new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            //print result
            Log.i("Post Result: ", response.toString());
        }
        catch (ProtocolException e){
            Log.i("exception: ", e.getMessage());
        }
        catch (MalformedURLException e){
            Log.i("exception: ", e.getMessage());
        }
        catch (IOException e){
            Log.i("exception: ", e.getMessage());
        }
    }

}