package com.example.designcut.nycia.Salon;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;

import com.example.designcut.nycia.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class OwnerADDIMage_Activity extends AppCompatActivity {
@BindView(R.id.add_image)
    Button Add_Image;
@BindView(R.id.add_Logo)Button Add_Logo;
@BindView(R.id.Upload_Image)Button Upload_image;
@BindView(R.id.Upload_Logo) Button Upload_Logo;
@BindView(R.id.Logo)
    ImageView Logo;
@BindView(R.id.Image) ImageView Image;

int Buttonselector =0;

    private String Pics_Url = "http://ec2-18-217-140-197.us-east-2.compute.amazonaws.com:8080/uploadPics";

    private String Logo_Url = "http://ec2-18-217-140-197.us-east-2.compute.amazonaws.com:8080/uploadLogo";
    // Setting Mediatype For Okhttp
    public static final MediaType JSON
            = MediaType.parse("application/json; charset=utf-8");

    private final int Image_Req = 1234;

    String test;

    private Bitmap bitmap;

    String email;

    int Image_counter=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_addimage_);
        ButterKnife.bind(this);

        Intent getintent = getIntent();
        email =getintent.getStringExtra("Email");




    }

    @OnClick(R.id.Upload_Logo)
    public void Upload_image() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();

                    MultipartBody.Builder builder = new MultipartBody.Builder()

                            .setType(MultipartBody.FORM);

                    final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();

                    bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);

                    final byte[] bitmapdata = stream.toByteArray();

                    builder.addFormDataPart("email", email);
                    builder.addFormDataPart("logo", "Logo", RequestBody.create(MEDIA_TYPE_PNG, bitmapdata));

                    RequestBody body = builder.build();

                    Request request = new Request.Builder()
                            .url(Logo_Url)
                            .post(body)
                            .build();

                    Response response = client.newCall(request).execute();

                    Log.e("check","test"+response.body().string());

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

        @OnClick(R.id.Upload_Image)
        public void upload_image () {

            Image_counter++;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        OkHttpClient client = new OkHttpClient();

                        MultipartBody.Builder builder = new MultipartBody.Builder()

                                .setType(MultipartBody.FORM);

                        final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();

                        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);

                        final byte[] bitmapdata = stream.toByteArray();

                        builder.addFormDataPart("email", email);
                        builder.addFormDataPart("image", "Logo" + Image_counter, RequestBody.create(MEDIA_TYPE_PNG, bitmapdata));

                        RequestBody body = builder.build();

                        Request request = new Request.Builder()
                                .url(Pics_Url)
                                .post(body)
                                .build();

                        Response response = client.newCall(request).execute();

                        Log.e("check","test"+response.body().string());


                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();


        }


    @OnClick(R.id.add_Logo)
    public void Button_Clicked(){
        Buttonselector =0;
        SelectImage();
    }


    @OnClick(R.id.add_image)
    public void Button_click(){
        Buttonselector=1;
        SelectImage();
    }


    private void SelectImage(){
        Intent selectImage = new Intent();
        selectImage.setType("image/*");
        selectImage.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(selectImage, Image_Req);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==Image_Req && resultCode==RESULT_OK && data!=null){
            Uri path = data.getData();

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),path);
                if(Buttonselector ==0){
                    Logo.setImageBitmap(bitmap);
                }else{
                    Image.setImageBitmap(bitmap);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


}
