package com.example.comicbookroute.util;

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

public class BookRouteAdapter extends RecyclerView.Adapter<BookRouteAdapter.BookRouteRowViewHolder> implements Filterable {

    List<BookRoute> items;
    List<BookRoute> filteredItems;
    private int whichLayoutId;

    public BookRouteAdapter(List<BookRoute> items, int whichLayoutId) {

        this.items = items;
        filteredItems = items;
        this.whichLayoutId = whichLayoutId;
    }

    @NonNull
    @Override
    public BookRouteRowViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View rowView = LayoutInflater.from(viewGroup.getContext()).inflate(whichLayoutId, viewGroup, false);
        BookRouteRowViewHolder holder = new BookRouteRowViewHolder(rowView);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull BookRouteRowViewHolder bookRouteRowViewHolder, int i) {
        final BookRoute currentBookRoute = filteredItems.get(i);
        bookRouteRowViewHolder.btnFavorites.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentBookRoute.isFavorite()){
                    Toast.makeText(v.getContext(), "Comic was already added", Toast.LENGTH_LONG).show();
                }else {
                    currentBookRoute.setFavorite(true);
                    BookRouteDatabase.getInstance(v.getContext()).getBookRouteDAO().updateBookRoute(currentBookRoute);
                    Toast.makeText(v.getContext(), "Comic added to favourites", Toast.LENGTH_LONG).show();
                }
            }
        });
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
        return filteredItems.size();
    }

    @Override
    public Filter getFilter() {
        return new CustomFilter();
    }

    public List<BookRoute> getItems() {
        return items;
    }

    class CustomFilter extends Filter {

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            filteredItems = (ArrayList<BookRoute>) results.values;
            notifyDataSetChanged();

        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint != null && constraint.length() > 0) {
                constraint = constraint.toString().toUpperCase();

                ArrayList<BookRoute> filters = new ArrayList<>();


                for (int i = 0; i < items.size(); i++) {
                    if (items.get(i).getPersonnage().toUpperCase().contains(constraint)) {
                        BookRoute p = items.get(i);

                        filters.add(p);
                    }
                }

                results.count = filters.size();
                results.values = filters;

            } else {
                results.count = items.size();
                results.values = items;

            }

            return results;
        }
    }

    public static class BookRouteRowViewHolder extends RecyclerView.ViewHolder{

        private ImageView image;
        private TextView tvPersonnage;
        private ImageButton ibDetails;
        private Button btnFavorites;
        private BookRoute item;



        public BookRouteRowViewHolder(@NonNull final View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.iv_row);
            tvPersonnage = itemView.findViewById(R.id.tv_row_personnage);
            ibDetails = itemView.findViewById(R.id.ib_details);
            btnFavorites = itemView.findViewById(R.id.btn_favorites);
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
