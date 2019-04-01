package com.example.comicbookroute.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.comicbookroute.DetailActivity;
import com.example.comicbookroute.R;
import com.example.comicbookroute.model.BookRoute;
import com.example.comicbookroute.model.BookRouteDatabase;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.BookRouteRowViewHolder>{

    List<BookRoute> items;

    public FavoriteAdapter(List<BookRoute> items) {

        this.items = items;
    }

    @NonNull
    @Override
    public BookRouteRowViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        Context context = viewGroup.getContext();
        View favoriteRowView = LayoutInflater.from(context).inflate(R.layout.bookroute_row_favorite, viewGroup, false);
        return new BookRouteRowViewHolder(favoriteRowView);
    }

    @Override
    public void onBindViewHolder(@NonNull BookRouteRowViewHolder bookRouteRowViewHolder, int i) {
        final BookRoute currentBookRoute = items.get(i);
        bookRouteRowViewHolder.tvPersonnage.setText(currentBookRoute.getPersonnage());
        try {
            FileInputStream fis = bookRouteRowViewHolder.itemView.getContext().openFileInput(currentBookRoute.getPhoto());
            Bitmap bitmap = BitmapFactory.decodeStream(fis);
            bookRouteRowViewHolder.image.setImageBitmap(bitmap);
            bookRouteRowViewHolder.item = currentBookRoute;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public List<BookRoute> getItems() {
        return items;
    }

    public static class BookRouteRowViewHolder extends RecyclerView.ViewHolder{

        private ImageView image;
        private TextView tvPersonnage;
        private ImageButton ibDetails;
        private BookRoute item;

        public BookRouteRowViewHolder(@NonNull final View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.iv_row);
            tvPersonnage = itemView.findViewById(R.id.tv_row_personnage);
            ibDetails = itemView.findViewById(R.id.ib_details);
            ibDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), DetailActivity.class);
                    intent.putExtra("item",item);
                    v.getContext().startActivity(intent);
                }
            });
        }
    }
}
