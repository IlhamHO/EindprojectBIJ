package com.example.comicbookroute.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.comicbookroute.R;
import com.example.comicbookroute.model.BookRoute;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BookRouteAdapter extends RecyclerView.Adapter<BookRouteAdapter.BookRouteRowViewHolder> implements Filterable {

    ArrayList<BookRoute> items;
    ArrayList<BookRoute> filteredItems;
    CustomFilter filter;

    public BookRouteAdapter( ArrayList<BookRoute> items) {

        this.items = items;
        filteredItems = items;
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
        BookRoute currentBookRoute = filteredItems.get(i);
        bookRouteRowViewHolder.tvPersonnage.setText(currentBookRoute.getPersonnage());
       /* bookRouteRowViewHolder.image.setImageResource(currentBookRoute.getPhoto());*/
       // Picasso.get().load(currentBookRoute.getPhoto()).into(bookRouteRowViewHolder.image);

    }
    @Override
    public int getItemCount() {
        return filteredItems.size();
    }
    public void setItems(ArrayList<BookRoute> bookRoutes){items.addAll(bookRoutes);}

    @Override
    public Filter getFilter() {
        if (filter == null){
            filter = new CustomFilter();
        }
        return filter;
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
                    if (items.get(i).getAuteur().toUpperCase().contains(constraint)) {
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
        private ImageView icon;



        public BookRouteRowViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.iv_row);
            tvPersonnage = itemView.findViewById(R.id.tv_row_personnage);
            icon = itemView.findViewById(R.id.iv_row_icon);

        }
    }
}
