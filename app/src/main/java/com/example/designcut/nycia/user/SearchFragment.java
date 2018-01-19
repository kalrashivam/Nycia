package com.example.designcut.nycia.user;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.designcut.nycia.ConnectionDetector;
import com.example.designcut.nycia.R;
import com.example.designcut.nycia.Salon.ServiceArrayAdaptor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


public class SearchFragment extends Fragment {
    @BindView(R.id.Enter_Locality)
    EditText Local;
    @BindView(R.id.Enter_Service)
    EditText Ser;




    @BindView(R.id.Search_list)
            ListView listView;

    @BindView(R.id.Search_Salon)
            Button Search;


    String test;

    ConnectionDetector cd;

    ArrayList<SearchResults> search;





    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    String Url ="http://ec2-18-217-140-197.us-east-2.compute.amazonaws.com:8080/getSaloons";

    public SearchFragment() {
        // Required empty public constructor
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_search, container, false);

        cd = new ConnectionDetector(getContext());

        ButterKnife.bind(this,view);





        return view;
    }


 @OnClick(R.id.Search_Salon)
    public void Search(){

        String text= Local.getText().toString().toLowerCase();
        String text2= Ser.getText().toString().toLowerCase();
        Log.e("testing", "check"+text+text2);
        final ProgressDialog progressDialog = new ProgressDialog(getContext(),
                R.style.AppTheme_Dark_Dialog);
        progressDialog.setIndeterminate(true);
        progressDialog.setMessage("Getting data...");
        progressDialog.show();

       search = new ArrayList<SearchResults>();

        final JSONObject Body = new JSONObject();
        try {
            Body.put("locality",text);
            Body.put("service_name",text2);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {


                    test = post(Url, Body.toString());
                    Log.e("SearchFragment", "check  "+test);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }).start();

        new android.os.Handler().postDelayed(
                new Runnable() {
                    public void run() {

                        if(test.equals("0")){
                            Toast.makeText(getContext(),"can't  find any Salons in ur state",Toast.LENGTH_LONG).show();
                        }else if(cd.isConnected()) {
                            Toast.makeText(getContext(),"Salons in ur state",Toast.LENGTH_LONG).show();
                            try {
                                JSONArray jr =new JSONArray(test);
                                for(int i =0 ;i<jr.length();i++){
                                    JSONObject js= jr.getJSONObject(i);
                                    String name =js.getString("name");
                                    String address =js.getString("address");
                                    String email =js.getString("email");
                                    search.add(new SearchResults(name,email,address));
                                }
//
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        progressDialog.dismiss();
                    }
                }, 3000);




        SearchAdaptor adaptor = new SearchAdaptor(getContext(), search);

        listView.setAdapter(adaptor);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                SearchResults results = search.get(i);
                Intent myintent = new Intent(getContext(),Salon_Details.class);
                myintent.putExtra("email",results.getEmail());
                startActivity(myintent);
            }
        });


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
