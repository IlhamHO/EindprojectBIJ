package com.example.comicbookroute.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.example.comicbookroute.model.BookRoute;
import com.example.comicbookroute.model.BookRouteDatabase;

public class SwipeToDeleteUtil extends ItemTouchHelper.SimpleCallback {

    private FavoriteAdapter favoriteAdapter;
    private Context context;
    private Drawable icon;
    private final ColorDrawable background;

    public SwipeToDeleteUtil(FavoriteAdapter favoriteAdapter) {
        super(0, ItemTouchHelper.LEFT);
        this.favoriteAdapter = favoriteAdapter;
        icon = ContextCompat.getDrawable(favoriteAdapter.getContext(), R.drawable.ic_delete_sweep_black_24dp);
        background = new ColorDrawable(Color.RED);
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        View itemView = viewHolder.itemView;
        int backgroundCornerOffset = 20;
        int iconMargin = 15;
        int iconTop = itemView.getTop() + iconMargin;
        int iconBottom = itemView.getBottom() - iconMargin;
        if (dX < 0) {
            int iconLeft = itemView.getRight() + (int)dX;
            int iconRight = itemView.getRight() + icon.getIntrinsicWidth()*2 + (int)dX;
            icon.setBounds(iconLeft, iconTop, iconRight, iconBottom);
            background.setBounds(itemView.getRight() + ((int) dX) - backgroundCornerOffset, itemView.getTop(), itemView.getRight(), itemView.getBottom());
        } else {
            background.setBounds(0, 0, 0, 0);
        }
        background.draw(c);
        icon.draw(c);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        final int position = viewHolder.getAdapterPosition();
        final BookRoute delBookRoute = favoriteAdapter.getItems().get(position);
        delBookRoute.setFavorite(false);
        BookRouteDatabase.getInstance(context).getBookRouteDAO().updateBookRoute(delBookRoute);
        favoriteAdapter.getItems().remove(delBookRoute);
        favoriteAdapter.notifyItemRemoved(position);
        final Snackbar snackbar = Snackbar.make(viewHolder.itemView, "Removed from favourites", Snackbar.LENGTH_LONG);
        snackbar.setAction("UNDO", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delBookRoute.setFavorite(true);
                BookRouteDatabase.getInstance(context).getBookRouteDAO().updateBookRoute(delBookRoute);
                favoriteAdapter.getItems().add(position, delBookRoute);
                favoriteAdapter.notifyItemInserted(position);
            }
        });
        snackbar.setActionTextColor(Color.YELLOW);
        snackbar.show();
    }
}
