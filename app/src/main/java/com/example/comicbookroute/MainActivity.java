package com.example.comicbookroute;

import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.comicbookroute.R;
import com.example.comicbookroute.fragment.AboutFragment;
import com.example.comicbookroute.fragment.FavoriteFragment;
import com.example.comicbookroute.fragment.HomeFragment;
import com.example.comicbookroute.fragment.MapFragment;
import com.example.comicbookroute.util.ViewPagerAdapter;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        toolbar = findViewById(R.id.main_toolbar);
        tabLayout = findViewById(R.id.main_tabs);
        viewPager = findViewById(R.id.main_viewpager);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());

       setSupportActionBar(toolbar);



        //add fragment keys
        adapter.AddFragment(new HomeFragment(),"");
        adapter.AddFragment(new MapFragment(),"");
        adapter.AddFragment(new FavoriteFragment(),"");
        adapter.AddFragment(new AboutFragment(),"");


        viewPager.setAdapter(adapter);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_home);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_map);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_star);
        tabLayout.getTabAt(3).setIcon(R.drawable.ic_info);

        ActionBar actionBar =getSupportActionBar();
        actionBar.setElevation(0);
    }
}
