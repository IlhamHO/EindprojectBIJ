package com.example.comicbookroute;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.comicbookroute.util.ProgressBarAnimation;

import java.util.concurrent.RunnableFuture;

import static java.lang.Thread.sleep;

public class SplashScreenActivity extends AppCompatActivity {

    AnimationDrawable rocketAnimation;
    private ProgressBar progressBar;
    private TextView textView;

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

        progressBar.setMax(100);
        progressBar.setScaleY(3f);

        progressAnimation();

    }

    private void progressAnimation() {
        ProgressBarAnimation anim = new ProgressBarAnimation(this, progressBar, textView, 0f, 100f);
        anim.setDuration(10000);
        progressBar.setAnimation(anim);
    }

    Thread myThread = new Thread(new Runnable() {
        @Override
        public void run() {
            try{
                sleep(3000);
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        }

    });

}




