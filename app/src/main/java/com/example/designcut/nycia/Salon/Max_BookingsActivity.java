package com.example.designcut.nycia.Salon;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.designcut.nycia.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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
    static int Value_hold;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_max__bookings);
        ButterKnife.bind(this);

        Value_hold =0;

        Value.setText("" +Value_hold);

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


}
