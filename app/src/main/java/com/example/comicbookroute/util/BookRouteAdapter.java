package com.example.comicbookroute.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.comicbookroute.R;
import com.example.comicbookroute.model.BookRoute;

import java.util.ArrayList;
import java.util.List;

public class BookRouteAdapter extends RecyclerView.Adapter<BookRouteAdapter.BookRouteRowViewHolder> {

    private ArrayList<BookRoute> items;

    public BookRouteAdapter(ArrayList<BookRoute> items) {
        this.items = items;

    }

    @NonNull
    @Override
    public BookRouteRowViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View rowView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.bookroute_row, viewGroup, false);
        BookRouteRowViewHolder holder = new BookRouteRowViewHolder(rowView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull BookRouteRowViewHolder bookRouteRowViewHolder, int i) {
        BookRoute currentBookRoute = items.get(i);
        bookRouteRowViewHolder.tvPersonnage.setText(currentBookRoute.getPersonnage());



    }

    @Override
    public int getItemCount() {
        return items.size();
    }
    public void setItems(ArrayList<BookRoute> bookRoutes){items.addAll(bookRoutes);}

    public static class BookRouteRowViewHolder extends RecyclerView.ViewHolder{

        private ImageView image;
        private TextView tvPersonnage;
        private ImageView favorite;



        public BookRouteRowViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.iv_row);
            tvPersonnage = itemView.findViewById(R.id.tv_row_personnage);
            favorite = itemView.findViewById(R.id.iv_row_botton);

        }
    }
}
