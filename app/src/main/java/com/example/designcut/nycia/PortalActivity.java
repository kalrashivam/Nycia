package com.example.designcut.nycia;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.transition.TransitionManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

public class PortalActivity extends AppCompatActivity {

    private static final int REQUEST_SIGNUP = 0;

    public static int Type_Login =0;

  @BindView(R.id.Portal_Button)
    Button Portal;

  @BindView(R.id.Portal_User_Button)
    Button Portal_user;

    @BindView(R.id.input_email)
    EditText _emailText;
    @BindView(R.id.input_password) EditText _passwordText;
    @BindView(R.id.btn_login) Button _loginButton;
    @BindView(R.id.link_signup)
    TextView _signupLink;

      public int color_user=0;
      public int color_salon=0;

    private static final String TAG = "PortalActivity";
//Url for sending data to login server
    private String Login_Url = "http://localhost:8080/login";
// Setting Mediatype For Okhttp
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portal);
        ButterKnife.bind(this);

       Portal.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               //A function to set the Portal type and to dynamically change the button look

               color_user=0;
               color_salon=1;
               setcolor(Portal_user,color_user);
               setcolor(Portal,color_salon);
               Type_Login=0;

           }
       });

       Portal_user.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               //A function to set the Portal type and to dynamically change the button look
               color_user=1;
               color_salon=0;
               setcolor(Portal_user,color_user);
               setcolor(Portal,color_salon);
               Type_Login=1;

           }
       });

        _loginButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });

        _signupLink.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Start the Signup activity
               if(Type_Login==0){ Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
                startActivityForResult(intent, REQUEST_SIGNUP);
//                startActivity(intent);
                      }else{
                   Intent intent = new Intent(getApplicationContext(), Salon_SignupActivity.class);
                   startActivityForResult(intent, REQUEST_SIGNUP);
               }
                finish();
                overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
            }
        });

    }

    public void setcolor(Button button,int x){
        if(x==1){
            button.setTextColor(getResources().getColor(R.color.black));
        }else{
            button.setTextColor(getResources().getColor(R.color.white));
        }
    }

    public void login() {
        Log.d(TAG, "Login");

        if (!validate()) {
            onLoginFailed();
            return;
        }

        _loginButton.setEnabled(false);

        final ProgressDialog progressDialog = new ProgressDialog(PortalActivity.this,
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Authenticating...");
        progressDialog.show();

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        final JSONObject Body = new JSONObject();
        try {
            Body.put("email",email);
            Body.put("password",password);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        // TODO: Implement my Api authentication logic here.

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {
                        // On complete call either onLoginSuccess or onLoginFailed
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    String test =post(Login_Url,Body.toString());
                                    Log.v(TAG,"post response:    "+  test);
                                    Toast.makeText(getApplicationContext(),test,Toast.LENGTH_LONG).show();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });


                        onLoginSuccess();
                        // onLoginFailed();
                        progressDialog.dismiss();
                    }
                }, 3000);
    }

    public boolean validate() {
        boolean valid = true;

        String email = _emailText.getText().toString();
        String password = _passwordText.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            _emailText.setError("enter a valid email address");
            valid = false;
        } else {
            _emailText.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            _passwordText.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            _passwordText.setError(null);
        }

        return valid;
    }

    public void onLoginSuccess() {
        _loginButton.setEnabled(true);
        finish();
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();

        _loginButton.setEnabled(true);
    }

    String post(String url,  String json) throws IOException {
        OkHttpClient client =new OkHttpClient();
        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();

        Log.e(TAG,"post response:    "+ response.body().string() );
        return response.body().string();
    }


}
