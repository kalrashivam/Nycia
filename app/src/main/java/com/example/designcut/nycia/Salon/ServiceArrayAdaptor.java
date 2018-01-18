package com.example.designcut.nycia.Salon;

import android.app.Service;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.designcut.nycia.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp on 18-01-2018.
 */

public class ServiceArrayAdaptor extends ArrayAdapter<Services>{

    public ServiceArrayAdaptor(Context context, ArrayList<Services> Services){
        super(context,0, (List<Services>) Services);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.owner_service_layout, parent, false);
        }


        Services currentService = getItem(position);

        TextView email =(TextView) listItemView.findViewById(R.id.Service_holder);
        email.setText(currentService.getHeading());

        TextView phone =(TextView) listItemView.findViewById(R.id.Amount_holder);
        phone.setText(currentService.getSubheading());


        return listItemView;
    }
}
