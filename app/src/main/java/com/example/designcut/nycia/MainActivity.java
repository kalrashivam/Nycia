package com.example.designcut.nycia;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.designcut.nycia.user.AccountFragment;
import com.example.designcut.nycia.user.HomeFragment;
import com.example.designcut.nycia.user.SearchFragment;

public class MainActivity extends AppCompatActivity {




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


        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

            FragmentManager manager= getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.master,new HomeFragment()).commit();
    }

}
