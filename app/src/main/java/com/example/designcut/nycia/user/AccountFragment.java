package com.example.designcut.nycia.user;

import android.content.Context;
import android.content.Intent;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.designcut.nycia.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class AccountFragment extends Fragment {

    @BindView(R.id.Change_state)
    TextView Change;

    @BindView(R.id.Your_orders)
    TextView Orders;
    public int Current_state;
    public AccountFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         View view =inflater.inflate(R.layout.fragment_account, container, false);
        ButterKnife.bind( this,view);

        return view;

    }

    @OnClick(R.id.Change_state)
    public void Change(){
        Intent myIntent = new Intent(getContext(), State_Search_Activity.class);
        startActivityForResult(myIntent, Current_state);
    }

    @OnClick(R.id.Your_orders)
    public void Orders(){
        Intent myIntent = new Intent(getContext(), Your_OrdersActivity.class);
        startActivity(myIntent);
    }







    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
