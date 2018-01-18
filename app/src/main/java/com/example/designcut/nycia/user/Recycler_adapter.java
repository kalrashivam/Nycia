package com.example.designcut.nycia.user;


import android.app.LauncherActivity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.designcut.nycia.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static java.security.AccessController.getContext;


/**
 * Created by hp on 03-01-2018.
 */

public class Recycler_adapter extends RecyclerView.Adapter<Recycler_adapter.ViewHolder>{
    private List<Data> list;
    Context context;

    Recycler_adapter(List<Data> list, Context context){
        this.list =list;
        this.context =context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_list_item,parent,false);

        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        Data listItem = list.get(position);

        holder.head.setText(listItem.getHeading());
        holder.sub.setText(listItem.getSubheading());
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myintent = new Intent(context, Salon_Details.class);


                myintent.putExtras();

                context.startActivity(myintent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder{

           public TextView head;
           public TextView sub;
           public Button button;

        public ViewHolder(View itemView) {
            super(itemView);

            head =(TextView) itemView.findViewById(R.id.Heading);
            sub =(TextView) itemView.findViewById(R.id.Subheading);
            button =(Button) itemView.findViewById(R.id.Make_Booking);
        }


    }


}
