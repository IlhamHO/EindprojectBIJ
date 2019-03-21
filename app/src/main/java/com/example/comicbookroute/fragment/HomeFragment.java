package com.example.comicbookroute.fragment;


import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.comicbookroute.R;
import com.example.comicbookroute.model.BookRouteDataSource;
import com.example.comicbookroute.util.BookRouteAdapter;
import com.example.comicbookroute.util.BookRouteHandler;

import java.io.IOException;
import java.util.concurrent.RunnableFuture;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    View v;
    private RecyclerView recyclerView;
    BookRouteHandler mBookRouteHandler;


    public HomeFragment() {
        // Required empty public constructor
    }

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_home, container, false);


        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = v.findViewById(R.id.rv_bookroute);
        BookRouteAdapter mBookRouteAdapter = new BookRouteAdapter(BookRouteDataSource.getInstance().getBookRoutes());
        recyclerView.setAdapter(mBookRouteAdapter);

        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setHasFixedSize(true);

        mBookRouteHandler = new BookRouteHandler(mBookRouteAdapter);

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
