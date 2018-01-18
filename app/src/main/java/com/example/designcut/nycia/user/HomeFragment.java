package com.example.designcut.nycia.user;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.designcut.nycia.ConnectionDetector;
import com.example.designcut.nycia.R;
import com.example.designcut.nycia.Salon_SignupActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class HomeFragment extends Fragment {

    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    String Url ="http://ec2-18-217-140-197.us-east-2.compute.amazonaws.com:8080/getStateSaloons";

    String test;

    ConnectionDetector cd;


    private RecyclerView.Adapter adapter;
    @BindView(R.id.Recycler) RecyclerView recyclerView;
    private List<Data> list;
    public HomeFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =inflater.inflate(R.layout.fragment_home, container, false);

        ButterKnife.bind(this,view );

        cd = new ConnectionDetector(getContext());

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        final ProgressDialog progressDialog = new ProgressDialog(getContext(),
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Finding Saloons...");
        progressDialog.show();

        final JSONObject Body = new JSONObject();
        try {
            Body.put("state","delhi");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        list = new ArrayList<Data>();

        new Thread(new Runnable() {
            @Override
            public void run() {


                try {


                    test = post(Url, Body.toString());
                    Log.e("HomeFragment", "check  "+test);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();


             new android.os.Handler().postDelayed(
                new Runnable() {
                public void run() {

                    if(test.equals("0")){
                        Toast.makeText(getContext(),"can't  find any Salons in ur state",Toast.LENGTH_LONG);
                    }else if(cd.isConnected()) {
                        Toast.makeText(getContext(),"Salons in ur state",Toast.LENGTH_LONG);
                        try {
                            JSONArray jr =new JSONArray(test);
                            for(int i=0;i<jr.length();i++){
                                JSONObject js= jr.getJSONObject(i);
                                String name =js.getString("name");
                                String address =js.getString("address");
                                list.add(new Data(name,address));
                                adapter = new Recycler_adapter(list, getContext());

                                recyclerView.setAdapter(adapter);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    progressDialog.dismiss();
                }
            }, 3000);






        return view;

    }

    String post(String url,  String json) throws IOException {
        OkHttpClient client =new OkHttpClient();

        RequestBody body = RequestBody.create(JSON, json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        Response response = client.newCall(request).execute();

        return response.body().string();
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
