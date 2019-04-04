package com.example.comicbookroute.fragment;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.SearchView;

import com.example.comicbookroute.R;
import com.example.comicbookroute.model.BookRouteDatabase;
import com.example.comicbookroute.util.BookRouteAdapter;

public class HomeFragment extends Fragment {

    View v;
    private RecyclerView recyclerView;

    private BookRouteAdapter mBookRouteAdapter;
    private FloatingActionButton fabSwitcher;


    private boolean isList = true;
    int icon;

    private View.OnClickListener switchClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
           isList = !isList;
           if(isList){
               LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
               mBookRouteAdapter = new BookRouteAdapter(BookRouteDatabase.getInstance(getActivity()).getBookRouteDAO().selectAllBookRoutes(), R.layout.bookroute_row);
               recyclerView.setAdapter(mBookRouteAdapter);
               recyclerView.setLayoutManager(linearLayoutManager);
               isList = true;
               fabSwitcher.setImageDrawable(getResources().getDrawable(R.drawable.ic_grid));

           }else{
               //tis een grid
               GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
               //layout grid meegeven
               mBookRouteAdapter = new BookRouteAdapter(BookRouteDatabase.getInstance(getActivity()).getBookRouteDAO().selectAllBookRoutes(), R.layout.bookroute_row_grid);
               recyclerView.setAdapter(mBookRouteAdapter);
               recyclerView.setLayoutManager(gridLayoutManager);
               isList = false;
               fabSwitcher.setImageDrawable(getResources().getDrawable(R.drawable.ic_list));
               
           }
        }
    };


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

        setHasOptionsMenu(true);

        return v;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //button details///////




        recyclerView = v.findViewById(R.id.rv_bookroute);
        fabSwitcher = v.findViewById(R.id.fabSwitch);
        fabSwitcher.setOnClickListener(switchClick);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        mBookRouteAdapter = new BookRouteAdapter(BookRouteDatabase.getInstance(getActivity()).getBookRouteDAO().selectAllBookRoutes(), R.layout.bookroute_row);
        recyclerView.setAdapter(mBookRouteAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);





    }





    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.list_menu, menu);

        SearchView sv = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();

        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                mBookRouteAdapter.getFilter().filter(s);
                return false;
            }
        });

    }



}
