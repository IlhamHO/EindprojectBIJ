package com.example.comicbookroute;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.comicbookroute.model.BookRoute;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class DetailActivity extends AppCompatActivity {

    private TextView tvNameDetail, tvPersonageDetail, tvYearDetail, tvAddressDetail;
    private ImageView ivPhotoDetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        BookRoute item = (BookRoute) intent.getExtras().getSerializable("item");
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());;
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(item.getLatitude(), item.getLongitude(), 1);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String address = addresses.get(0).getAddressLine(0);

        tvNameDetail = findViewById(R.id.tv_naam_detail);
        tvPersonageDetail = findViewById(R.id.tv_personage_detail);
        tvYearDetail = findViewById(R.id.tv_jaar_detail);
        tvAddressDetail = findViewById(R.id.tv_adres_detail);
        ivPhotoDetail = findViewById(R.id.iv_photo_detail);

        tvNameDetail.setText(item.getAuteur());
        tvPersonageDetail.setText(item.getPersonnage());
        tvYearDetail.setText(item.getAnnee());
        tvAddressDetail.setText(address);
        try {
            FileInputStream fis = openFileInput(item.getPhoto());
            Bitmap bitmap = BitmapFactory.decodeStream(fis);
            ivPhotoDetail.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
