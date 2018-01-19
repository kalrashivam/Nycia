package com.example.designcut.nycia.user;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.designcut.nycia.R;
import com.example.designcut.nycia.Salon.Bookings;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hp on 19-01-2018.
 */

public class SearchAdaptor extends ArrayAdapter<SearchResults> {

    public SearchAdaptor(Context context, ArrayList<SearchResults> lists){
        super(context,0,  lists);
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.user_search_listitem, parent, false);
        }


        SearchResults currentBooking = getItem(position);

        TextView email =(TextView) listItemView.findViewById(R.id.Search_name);
        email.setText(currentBooking.getName());

        TextView phone =(TextView) listItemView.findViewById(R.id.Search_address);
        phone.setText(currentBooking.getAddress());


        return listItemView;
    }
}
