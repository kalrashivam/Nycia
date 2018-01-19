package com.example.designcut.nycia.user;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.designcut.nycia.ConnectionDetector;
import com.example.designcut.nycia.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Salon_Details extends AppCompatActivity {

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    String Url ="http://ec2-18-217-140-197.us-east-2.compute.amazonaws.com:8080/getStateSaloons";

    String test;

    JSONObject data;

    String email;

    ConnectionDetector cd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salon__details);

        Intent get =getIntent();
        email = get.getStringExtra("email");

        cd = new ConnectionDetector(this);

        final ProgressDialog progressDialog = new ProgressDialog(this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Getting data...");
        progressDialog.show();

        final JSONObject Body = new JSONObject();
        try {
            Body.put("state","delhi");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        new Thread(new Runnable() {
            @Override
            public void run() {


                try {


                    test = post(Url, Body.toString());
                    Log.e("SalonDetails", "check  "+test);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();


        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {

                        if(test.equals("0")){
                            Toast.makeText(Salon_Details.this,"can't  find any Salons in ur state",Toast.LENGTH_LONG);
                        }else if(cd.isConnected()) {
                            Toast.makeText(Salon_Details.this,"Salons in ur state",Toast.LENGTH_LONG);
                            try {
                                JSONArray jr =new JSONArray(test);
                                for(int i=0;i<jr.length();i++){
                                    JSONObject js= jr.getJSONObject(i);
                                    String name =js.getString("email");
                                    if(name.equals(email)){
                                      data=js;
                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        progressDialog.dismiss();
                    }
                }, 3000);



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
}
