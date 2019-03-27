package com.example.comicbookroute.util;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.comicbookroute.DetailActivity;
import com.example.comicbookroute.MainActivity;
import com.example.comicbookroute.R;
import com.example.comicbookroute.fragment.HomeFragment;
import com.example.comicbookroute.model.BookRoute;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class BookRouteAdapter extends RecyclerView.Adapter<BookRouteAdapter.BookRouteRowViewHolder> implements Filterable {

    ArrayList<BookRoute> items;
    ArrayList<BookRoute> filteredItems;
    private int whichLayoutId;

    public BookRouteAdapter( ArrayList<BookRoute> items, int whichLayoutId) {

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
        BookRoute currentBookRoute = filteredItems.get(i);
        bookRouteRowViewHolder.tvPersonnage.setText(currentBookRoute.getPersonnage());
        String imageUrl = String.format("https://bruxellesdata.opendatasoft.com/explore/dataset/comic-book-route/files/%s", currentBookRoute.getPhoto())+"/300/";
        Picasso.get().load(imageUrl).into(bookRouteRowViewHolder.image);
        bookRouteRowViewHolder.item = currentBookRoute;
    }
    @Override
    public int getItemCount() {
        return filteredItems.size();
    }

    @Override
    public Filter getFilter() {
        return new CustomFilter();
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
        private ImageView icon;
        private ImageButton ib;
        private BookRoute item;



        public BookRouteRowViewHolder(@NonNull final View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.iv_row);
            tvPersonnage = itemView.findViewById(R.id.tv_row_personnage);
            icon = itemView.findViewById(R.id.iv_row_icon);
            ib = itemView.findViewById(R.id.ib_details);

            ib.setOnClickListener (new View.OnClickListener() {
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
