package com.example.comicbookroute.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.comicbookroute.R;
import com.example.comicbookroute.model.BookRouteDatabase;
import com.example.comicbookroute.util.FavoriteAdapter;
import com.example.comicbookroute.util.SwipeToDeleteUtil;

public class FavoriteFragment extends Fragment {

    View v;
    private RecyclerView recyclerView;
    private FavoriteAdapter favoriteAdapter;

    public FavoriteFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_favorite, container, false);
        setHasOptionsMenu(true);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = v.findViewById(R.id.rv_favourites);
        favoriteAdapter= new FavoriteAdapter(BookRouteDatabase.getInstance(v.getContext()).getBookRouteDAO().selectAllFavoriteBookRoutes(true));
        recyclerView.setAdapter(favoriteAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteUtil(favoriteAdapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
    }
}





