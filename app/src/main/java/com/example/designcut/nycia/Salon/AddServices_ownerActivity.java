package com.example.designcut.nycia.Salon;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.designcut.nycia.ConnectionDetector;
import com.example.designcut.nycia.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AddServices_ownerActivity extends AppCompatActivity {

    @BindView(R.id.add_service)
    EditText Add_Service;
    @BindView(R.id.add_amount)
    EditText Add_amount;
    @BindView(R.id.Add_servicebutton)
    Button Service_Button;

    String email;
    private String Url = "http://ec2-18-217-140-197.us-east-2.compute.amazonaws.com:8080/addServices";

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    ArrayList<Services> services;

    ConnectionDetector cd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_services_owner);

        ButterKnife.bind(this);

         cd = new ConnectionDetector(this);
        services = new ArrayList<Services>();

        Intent getIntent = getIntent();
        String test = getIntent.getStringExtra("json_res");

        try {
            JSONObject js = new JSONObject(test);
            email = js.getString("email");
            JSONArray ar =js.getJSONArray("services");
            for(int i=0;i<ar.length();i++){
                JSONObject jr = ar.getJSONObject(i);
                services.add(new Services(jr.getString("name"),jr.getString("amount")));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ListView ls = (ListView) findViewById(R.id.listview_services);

        ServiceArrayAdaptor adaptor = new ServiceArrayAdaptor(this, services);

        ls.setAdapter(adaptor);


    }

    @OnClick(R.id.Add_servicebutton)
    public void Clicked(){
        String Service =Add_Service.getText().toString().toLowerCase();
        String Amount =Add_amount.getText().toString().toLowerCase();

        final JSONObject Body = new JSONObject();
        try {
            Body.put("email",email);
            Body.put("name",Service);
            Body.put("amount",Amount);
            Log.e("Checking", "    "+email+Service+Amount);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                 String checker =   post(Url, Body.toString());
                 Log.e("AddServiceActivity", "Check    "+checker);

                 if(checker.equals("0")){
                     Toast.makeText(getApplicationContext(),"Can't Add Service", Toast.LENGTH_SHORT).show();
                 }else if(cd.isConnected()){
                     Toast.makeText(getApplicationContext(),"Added Service", Toast.LENGTH_SHORT).show();
                 }


                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
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
