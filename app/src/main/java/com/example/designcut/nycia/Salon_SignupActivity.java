package com.example.designcut.nycia;

import android.app.ProgressDialog;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.designcut.nycia.Salon.Owner_MainActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Salon_SignupActivity extends AppCompatActivity {
    private static final String TAG = "Salon_SignupActivity";
    ArrayAdapter<CharSequence> adapter;

    int Type_int;
    @BindView(R.id.Type_Spinner)
    Spinner Type_spinner;
    @BindView(R.id.input_name)
    EditText _nameText;
    @BindView(R.id.pincode)
    EditText pincode;
    @BindView(R.id.input_address) EditText _addressText;
    @BindView(R.id.input_email) EditText _emailText;
    @BindView(R.id.input_mobile) EditText _mobileText;
    @BindView(R.id.input_password) EditText _passwordText;
    @BindView(R.id.input_reEnterPassword) EditText _reEnterPasswordText;
    @BindView(R.id.btn_sign_up)
    Button sign_up_Button;
    @BindView(R.id.link_login)
    TextView login_Link;
    @BindView(R.id.input_Locality_no) EditText Locality;
    @BindView(R.id.input_state) EditText State;
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    String Url ="http://ec2-18-217-140-197.us-east-2.compute.amazonaws.com:8080/signup";

    String Salon_test = null;
    String tester = "exists";

    double latitude, longitude;

    String Token ="saloons";

    ConnectionDetector cd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salon__signup);

        ButterKnife.bind(this);

        adapter =adapter.createFromResource(this, R.array.Categories,android.R.layout.simple_spinner_dropdown_item);

        Type_spinner.setAdapter(adapter);

        cd = new ConnectionDetector(this);

        sign_up_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });

        login_Link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Finish the registration screen and return to the Login activity
                Intent intent = new Intent(getApplicationContext(),PortalActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });
    }

    public void signup() {
        Log.d(TAG, "Signup");

        if (!validate()) {
            onSignupFailed();
            return;
        }

        sign_up_Button.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(Salon_SignupActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Creating Account...");
        progressDialog.show();


        Type_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               String Type=(String) adapterView.getItemAtPosition(i);

                if(Type.equals(getString(R.string.Salon))){
                    Type_int=0;

                }else if(Type.equals(getString(R.string.spa))){
                    Type_int =1;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        int Type= Type_int;
        String set_Type;
        if(Type_int==0){
            set_Type="0";
        }else{
            set_Type="1";
        }
        String Locality_text = Locality.getText().toString();
        String State_text = State.getText().toString();
        String name = _nameText.getText().toString();
        String address = _addressText.getText().toString();
        String email = _emailText.getText().toString();
        String mobile = _mobileText.getText().toString();
        String password = _passwordText.getText().toString();
        String text_pincode =pincode.getText().toString();
        String reEnterPassword = _reEnterPasswordText.getText().toString();

        String find_address_for= address +" "+Locality_text+" "+State_text + " "+ text_pincode;

        Geocoder gc = new Geocoder(this);
        try {
            List<Address> list = gc.getFromLocationName(find_address_for, 1);
            Address add = list.get(0);
            latitude = add.getLatitude();
            longitude =add.getLongitude();
            Log.e(TAG,"Check lat"+latitude+longitude);
        } catch (IOException e) {
            e.printStackTrace();
        }


        // TODO: Implement My Api signup logic here.

       final JSONObject Body = new JSONObject();

        try {
            Body.put("token" ,Token);
            Body.put("name" ,name);
            Body.put("address",address);
            Body.put("locality",Locality_text);
            Body.put("state",State_text);
            Body.put("email" ,email);
            Body.put("phone_no" ,mobile);
            Body.put("type",set_Type);
            Body.put("latitude",latitude);
            Body.put("longitude",longitude);
            Body.put("password" ,password);







        } catch (JSONException e) {
            e.printStackTrace();
        }
        if(cd.isConnected()) {

            new Thread(new Runnable() {
                @Override
                public void run() {


                    try {


                        Salon_test = post(Url, Body.toString());
                        Log.e(TAG, "check  "+Salon_test);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }).start();

        }else{
            Toast.makeText(getApplicationContext(),"Internet network isn't there ",Toast.LENGTH_SHORT).show();
            onSignupFailed();
        }
        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onSignupSuccess or onSignupFailed
                        // depending on success
                        if(Salon_test.equals(tester)){
                            onSignupFailed();
                        }else if(cd.isConnected()) {
                            onSignupSuccess();
                        }else{

                             onSignupFailed();
                        }
                        progressDialog.dismiss();
                    }
                }, 3000);
    }

    public void onSignupSuccess() {
        sign_up_Button.setEnabled(true);
        Intent myIntent =new Intent(Salon_SignupActivity.this, PortalActivity.class);
        startActivity(myIntent);
    }

    public void onSignupFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        sign_up_Button.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;
        String Locality_text = Locality.getText().toString();
        String State_text = State.getText().toString();
        String name = _nameText.getText().toString();
        String address = _addressText.getText().toString();
        String email = _emailText.getText().toString();
        String mobile = _mobileText.getText().toString();
        String password = _passwordText.getText().toString();
        String reEnterPassword = _reEnterPasswordText.getText().toString();

        if (name.isEmpty() || name.length() < 3) {
            _nameText.setError("at least 3 characters");
            valid = false;
        } else {
            _nameText.setError(null);
        }

        if (address.isEmpty()) {
            _addressText.setError("Enter Valid Address");
            valid = false;
        } else {
            _addressText.setError(null);
        }

        if (Locality_text.isEmpty()) {
            Locality.setError("Enter Valid Locality");
            valid = false;
        } else {
            Locality.setError(null);
        }

        if (State_text.isEmpty()) {
            State.setError("Enter Valid State");
            valid = false;
        } else {
            State.setError(null);
        }


        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (mobile.isEmpty() || mobile.length()!=10) {
            _mobileText.setError("Enter Valid Mobile Number");
            valid = false;
        } else {
            _mobileText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        if (reEnterPassword.isEmpty() || reEnterPassword.length() < 4 || reEnterPassword.length() > 20 || !(reEnterPassword.equals(password))) {
            _reEnterPasswordText.setError("Password Do not match");
            valid = false;
        } else {
            _reEnterPasswordText.setError(null);
        }

        return valid;
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
