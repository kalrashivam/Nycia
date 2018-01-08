package com.example.designcut.nycia.Salon;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import com.example.designcut.nycia.R;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

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

private final int Image_Req = 1234;
private Bitmap bitmap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_addimage_);
        ButterKnife.bind(this);


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
