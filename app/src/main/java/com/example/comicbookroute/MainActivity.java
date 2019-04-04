package com.example.comicbookroute;

import android.app.ProgressDialog;
import android.os.Message;
import android.support.design.widget.TabLayout;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.widget.Toolbar;


import com.example.comicbookroute.fragment.AboutFragment;
import com.example.comicbookroute.fragment.FavoriteFragment;
import com.example.comicbookroute.fragment.HomeFragment;
import com.example.comicbookroute.fragment.MapFragment;

import com.example.comicbookroute.util.BookRouteHandler;
import com.example.comicbookroute.util.ViewPagerAdapter;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;
    private BookRouteHandler mBookRouteHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.main_toolbar);
        tabLayout = findViewById(R.id.main_tabs);


        adapter = new ViewPagerAdapter(getSupportFragmentManager());
        HomeFragment homeFragment = new HomeFragment();
        adapter.AddFragment(homeFragment, "");
        adapter.AddFragment(new MapFragment(), "");
        adapter.AddFragment(new FavoriteFragment(), "");
        adapter.AddFragment(new AboutFragment(), "");

        viewPager = findViewById(R.id.main_viewpager);
        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_home);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_map);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_star);
        tabLayout.getTabAt(3).setIcon(R.drawable.ic_info);

        setSupportActionBar(toolbar);

        mBookRouteHandler = new BookRouteHandler(getApplicationContext());

        downloadData();

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


                        mBookRouteHandler.sendMessage(msg);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        });
        thread.start();

    }
}
