package com.example.comicbookroute.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.comicbookroute.R;

public class AboutFragment extends Fragment {

    View v;
    public ImageView logo1, logo2;
    public TextView part, naam1, naam2, naam3, dev;

    public AboutFragment() {
    }

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_about, container, false);

        part = v.findViewById(R.id.tv_partners);
        logo1 = v.findViewById(R.id.iv_eraslogo);
        logo2 = v.findViewById(R.id.iv_vdablogo);
        dev = v.findViewById(R.id.tv_dev);
        naam1 = v.findViewById(R.id.tv_naam1);
        naam2 = v.findViewById(R.id.tv_naam2);
        naam3 = v.findViewById(R.id.tv_naam3);

        logo1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://www.erasmushogeschool.be/nl"));
                startActivity(intent);
            }
        });
        logo2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://www.vdab.be/opleidingen/aanbod/750417/cursus/148076/Ontwikkelaar_mobiele_applicaties-ANDERLECHT"));
                startActivity(intent);
            }
        });
        naam1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://github.com/gloin666"));
                startActivity(intent);
            }
        });
        naam2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://github.com/brechtgorissen"));
                startActivity(intent);
            }
        });
        naam3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse("https://github.com/mobapp10"));
                startActivity(intent);
            }
        });
        return v;
    }
}
