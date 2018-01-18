package com.example.designcut.nycia.Salon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import com.example.designcut.nycia.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Max_BookingsActivity extends AppCompatActivity {

    @BindView(R.id.Add_Button)
    Button Add;
    @BindView(R.id.Subtract_Button)
    Button Subtract;
    @BindView(R.id.Max_Value)
    TextView Value;

    @BindView(R.id.Submit_Button)
    Button Submit;
    //Integer to hold and send the value to api for max bookings in the day
    static int Value_hold = 0;

    private String Url = "http://ec2-18-217-140-197.us-east-2.compute.amazonaws.com:8080/setSeats";

    // Setting Mediatype For Okhttp
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    String test;
    String email;

    JSONObject Body;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_max__bookings);
        ButterKnife.bind(this);


        Intent get = getIntent();
        email = get.getStringExtra("Email");




        Value.setText("" +Value_hold);

    }

    @OnClick(R.id.Submit_Button)
    public void Submit_but(){
        new Thread(new Runnable() {
            @Override
            public void run() {

                Body= new JSONObject();

                try {
                    Body.put("email",email);
                    Body.put("seats",Value_hold);
                    Log.e("check", "checker"+email+"  "+Value_hold);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    test= post(Url, Body.toString());
                    Log.e("check", "setting seats " +test);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @OnClick(R.id.Add_Button)
    public void Addtoit(){
        ++Value_hold;
        Value.setText("" +Value_hold);
    }

    @OnClick(R.id.Subtract_Button)
    public void subfromit(){
        if(Value_hold!=0){
            --Value_hold;
            Value.setText("" +Value_hold);
        }
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
