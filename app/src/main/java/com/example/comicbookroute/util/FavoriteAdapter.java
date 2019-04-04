package com.example.comicbookroute.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.comicbookroute.DetailActivity;
import com.example.comicbookroute.R;
import com.example.comicbookroute.model.BookRoute;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.BookRouteRowViewHolder>{

    List<BookRoute> items;
    private Context context;

    public FavoriteAdapter(List<BookRoute> items, Context context) {
        this.items = items;
        this.context = context;
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
        bookRouteRowViewHolder.item = currentBookRoute;
        bookRouteRowViewHolder.tvPersonnage.setText(currentBookRoute.getPersonnage());
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public List<BookRoute> getItems() {
        return items;
    }

    public Context getContext() {
        return context;
    }

    public static class BookRouteRowViewHolder extends RecyclerView.ViewHolder{

        private TextView tvPersonnage;
        private CardView cardView;
        private BookRoute item;

        public BookRouteRowViewHolder(@NonNull final View itemView) {
            super(itemView);
            tvPersonnage = itemView.findViewById(R.id.tv_row_personnage);
            cardView = itemView.findViewById(R.id.favoriteCard);
            cardView.setOnClickListener(new View.OnClickListener() {
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
