package com.example.designcut.nycia.user;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.designcut.nycia.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class State_Search_Activity extends AppCompatActivity {

    @BindView(R.id.State)
    Spinner StateSpinner;

    ArrayAdapter<CharSequence> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_state__search_);

        ButterKnife.bind(this);
        adapter =adapter.createFromResource(this, R.array.States,android.R.layout.simple_spinner_dropdown_item);

        StateSpinner.setAdapter(adapter);

       StateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
           @Override
           public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
               String Type=(String) adapterView.getItemAtPosition(i);
               Log.e("StateActivity","check"+Type);

               SharedPreferences sharedPref = getSharedPreferences("Userinfo", Context.MODE_PRIVATE);

               SharedPreferences.Editor editor = sharedPref.edit();

               editor.putString("state",Type);
               editor.apply();
           }

           @Override
           public void onNothingSelected(AdapterView<?> adapterView) {

           }
       });
    }



}
