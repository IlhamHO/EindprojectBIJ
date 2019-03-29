package com.example.comicbookroute;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.comicbookroute.model.BookRoute;


import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class DetailActivity extends AppCompatActivity {
    private TextView tvnaamdetail,tvpersonagedetail,tvjaardetail;
    private ImageView ivphotodetail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Intent intent = getIntent();
        BookRoute item = (BookRoute) intent.getExtras().getSerializable("item");

        tvnaamdetail = findViewById(R.id.tv_naam_detail);
        tvpersonagedetail = findViewById(R.id.tv_personage_detail);
        tvjaardetail = findViewById(R.id.tv_jaar_detail);
        ivphotodetail = findViewById(R.id.iv_photo_detail);

        tvpersonagedetail.setText(item.getPersonnage());
        tvnaamdetail.setText(item.getAuteur());
        tvjaardetail.setText(item.getAnnee());
        try {
            FileInputStream fis = openFileInput(item.getPhoto());
            Bitmap bitmap = BitmapFactory.decodeStream(fis);
            ivphotodetail.setImageBitmap(bitmap);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
