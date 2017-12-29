package com.example.designcut.nycia;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PortalActivity extends AppCompatActivity {

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
  //Boolean to return Portal type
  Boolean Portal_type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_portal);
        ButterKnife.bind(this);

       Portal.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               //A function to set the Portal type and to dynamically change the button look
               change(1, Portal);
           }
       });

       Portal_user.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               //A function to set the Portal type and to dynamically change the button look
               change(0,Portal_user);

           }
       });
    }

  public void change(int i, Button button){
        if(i ==0){
            Portal_type=true;
        }else{
            Portal_type=false;
        }

        button.setBackgroundColor(getResources().getColor(R.color.monsoon));
  }
}
