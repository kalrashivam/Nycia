package com.example.designcut.nycia.Salon;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.designcut.nycia.R;

import java.util.List;

/**
 * Created by hp on 15-01-2018.
 */

public class BookingsAdaptor extends ArrayAdapter<Bookings> {

  public BookingsAdaptor(Context context, ArrayAdapter<Bookings> bookings){
      super(context,0, (List<Bookings>) bookings);
  }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if an existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.owner_bookings_item, parent, false);
        }


        Bookings currentBooking = getItem(position);

        TextView email =(TextView) listItemView.findViewById(R.id.set_email);
        email.setText(currentBooking.getUser_email());

        TextView phone =(TextView) listItemView.findViewById(R.id.set_Phone);
        phone.setText(currentBooking.getUser_phone());

        TextView Service =(TextView) listItemView.findViewById(R.id.set_Service);
        Service.setText(currentBooking.getService());

        TextView amount =(TextView) listItemView.findViewById(R.id.set_amount);
        amount.setText(currentBooking.getAmount());

        TextView date =(TextView) listItemView.findViewById(R.id.date);
        date.setText(currentBooking.getDate());

        TextView status =(TextView) listItemView.findViewById(R.id.Status);
        status.setText(currentBooking.getStatus());

        return listItemView;
    }
}
