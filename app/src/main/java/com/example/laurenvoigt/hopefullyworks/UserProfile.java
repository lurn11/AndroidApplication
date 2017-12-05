package com.example.laurenvoigt.hopefullyworks;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.support.v4.app.FragmentTransaction;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.facebook.login.widget.ProfilePictureView;
import android.net.Network;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;


//import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class UserProfile extends AppCompatActivity {
    JSONObject response, profile_pic_data, profile_pic_url;
    ProfilePictureView profilePictureView;
    private DrawerLayout dl;
    private DrawerLayout nv;
    private ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);


        //Drawer stuff here
        /*
        Intent intent = getIntent();
        String jsondata = intent.getStringExtra("userProfile");
        //Log.w("Jsondata", jsondata);
        TextView user_name = (TextView) findViewById(R.id.UserName);
        //ImageView user_picture = (ImageView) findViewById(R.id.profilePic);
        TextView user_email = (TextView) findViewById(R.id.email);
        profilePictureView = (ProfilePictureView)findViewById(R.id.picture);
        try {
            response = new JSONObject(jsondata);
            user_email.setText(response.get("email").toString());
            user_name.setText(response.get("name").toString());
            profile_pic_data = new JSONObject(response.get("picture").toString());
            //profile_pic_url = new JSONObject(profile_pic_data.getString("data"));
            //Picasso.with(this).load(profile_pic_url.getString("url")).into(user_picture);
            profilePictureView.setPresetSize(ProfilePictureView.NORMAL);
            profilePictureView.setProfileId(response.getString("id"));

        } catch(Exception e){
            e.printStackTrace();
        }

        //getSupportFragmentManager().beginTransaction().replace(R.id.flcontent, new BlankFragment()).addToBackStack(null).commit();
        */
        //Button Stuff here
        Button button1 = (Button)findViewById(R.id.button1);
        //Button button2 = (Button)findViewById(R.id.button2);

        button1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //Changed stuff here from MainActivity to Userprofile
                //Main2Activity.class
                Intent int1 = new Intent(UserProfile.this, MapsActivity.class);
                startActivity(int1);
            }
        });
        /*
        button2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                //Changed stuff here as well - like above
                Intent int2 = new Intent(UserProfile.this, Main3Activity.class);
                startActivity(int2);
            }
        });
        */
    }

}

