package com.example.designcut.nycia;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.designcut.nycia.user.AccountFragment;
import com.example.designcut.nycia.user.HomeFragment;
import com.example.designcut.nycia.user.SearchFragment;

import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {

   String test;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentManager manager =getSupportFragmentManager();

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    manager.beginTransaction().replace(R.id.master,new HomeFragment()).commit();
                    return true;
                case R.id.navigation_search:
                    manager.beginTransaction().replace(R.id.master,new SearchFragment()).commit();
                    return true;
                case R.id.navigation_account:
                    manager.beginTransaction().replace(R.id.master,new AccountFragment()).commit();
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent get = getIntent();
        test =get.getStringExtra("Login");

        try {
            JSONObject js =new JSONObject(test);
            String name =js.getString("name");
            String email =js.getString("email");

            SharedPreferences sharedPref = getSharedPreferences("Userinfo", Context.MODE_PRIVATE);

            SharedPreferences.Editor editor= sharedPref.edit();
            editor.putString("name",name);
            editor.putString("email",email);
            editor.apply();



        } catch (JSONException e) {
            e.printStackTrace();
        }




        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

            FragmentManager manager= getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.master,new HomeFragment()).commit();
    }

}
