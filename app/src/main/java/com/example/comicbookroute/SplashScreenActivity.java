package com.example.comicbookroute;

import android.graphics.drawable.AnimationDrawable;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.comicbookroute.util.BookRouteHandler;
import com.example.comicbookroute.util.ProgressBarAnimation;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SplashScreenActivity extends AppCompatActivity {

    AnimationDrawable rocketAnimation;
    private ProgressBar progressBar;
    private TextView textView;
    private BookRouteHandler bookRouteHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        ProgressBar rocketImage = findViewById(R.id.iv_animation);
        rocketImage.setBackgroundResource(R.drawable.my_progressbar_animation);
        rocketAnimation = (AnimationDrawable) rocketImage.getBackground();
        rocketAnimation.start();
        progressBar = findViewById(R.id.pb_loading);
        textView = findViewById(R.id.tv_progressloading);
        bookRouteHandler = new BookRouteHandler(getApplicationContext());
        progressBar.setMax(100);
        progressBar.setScaleY(3f);
        progressAnimation();
        downloadData();
    }

    private void progressAnimation() {
        ProgressBarAnimation anim = new ProgressBarAnimation(this, progressBar, textView, 0f, 100f);
        anim.setDuration(10000);
        progressBar.setAnimation(anim);
    }

    private void downloadData() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    OkHttpClient client = new OkHttpClient();
                    Request request = new Request.Builder()
                            .url("https://bruxellesdata.opendatasoft.com/api/records/1.0/search/?dataset=comic-book-route&rows=52")
                            .get()
                            .build();

                    Response response = client.newCall(request).execute();
                    if (response.body() != null) {
                        String responseBodyText = response.body().string();
                        Message msg = new Message();
                        msg.obj = responseBodyText;
                        bookRouteHandler.sendMessage(msg);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });
        thread.start();
    }
}




