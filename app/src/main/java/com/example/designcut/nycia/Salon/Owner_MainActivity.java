package com.example.designcut.nycia.Salon;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.designcut.nycia.ConnectionDetector;
import com.example.designcut.nycia.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class Owner_MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    ConnectionDetector cd;

    String email;

    //Url for sending data to login server
    private String Url = "http://ec2-18-217-140-197.us-east-2.compute.amazonaws.com:8080/getSaloonBookings";
    // Setting Mediatype For Okhttp
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    String test;

    String res;

    ArrayList<Bookings> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner__main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
       Intent getintent = getIntent();
       test = getintent.getStringExtra("Login");


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

       if(test.equals("0")){
           Toast.makeText(this,"Login failed",Toast.LENGTH_LONG);
       }else{
          getData(test);
       }

       list =new ArrayList<Bookings>();


        cd= new ConnectionDetector(this);



        final JSONObject Body = new JSONObject();
        try {
            Body.put("email",email);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    res = post(Url, Body.toString());
                    if(res.equals("")){
                        Toast.makeText(Owner_MainActivity.this,"No bookings", Toast.LENGTH_SHORT).show();
                    }else if(cd.isConnected()) {
                        getDataforBookings(res);
                    }else{
                        Toast.makeText(Owner_MainActivity.this, "No internet", Toast.LENGTH_SHORT).show();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.owner__main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_add_images) {
            Intent ImageIntent =new Intent(Owner_MainActivity.this, OwnerADDIMage_Activity.class);
            startActivity(ImageIntent);
            // Handle the camera action
        } else if (id == R.id.nav_Max_Seats) {
            Intent MaxIntent = new Intent(Owner_MainActivity.this, Max_BookingsActivity.class);
            MaxIntent.putExtra("Email", email);
            startActivity(MaxIntent);

        } else if (id == R.id.nav_Overall_Details) {
            Intent UpdateIntent = new Intent(Owner_MainActivity.this, Update_DetailsActivity.class);
            startActivity(UpdateIntent);

        } else if(id== R.id.add_service){
            Intent AddService = new Intent(Owner_MainActivity.this,AddServices_ownerActivity.class);
            AddService.putExtra("json_res",test);
            startActivity(AddService);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    String post(String url,  String json) throws IOException {
        OkHttpClient client =new OkHttpClient();

        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    void getData(String data){
        try {
            JSONObject js = new JSONObject(data);
            email = js.getString("email");

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    void getDataforBookings(String data){

        try {
            JSONObject js = new JSONObject(data);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
