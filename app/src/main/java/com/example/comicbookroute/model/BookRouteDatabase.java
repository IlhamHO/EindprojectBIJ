package com.example.comicbookroute.model;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(version = 1, entities = {BookRoute.class, StreetArt.class, Restaurant.class}, exportSchema = false)
public abstract class BookRouteDatabase extends RoomDatabase {

    private static BookRouteDatabase ourInstance;

    public static BookRouteDatabase getInstance(Context context) {
        if(ourInstance == null){
            ourInstance = createDatabase(context);
        }
        return ourInstance;
    }

    private static BookRouteDatabase createDatabase(Context context) {
        return Room.databaseBuilder(
                context,
                BookRouteDatabase.class,
                "bookroutes.db").allowMainThreadQueries().build();
    }

    public abstract BookRouteDao getBookRouteDAO();
    public abstract StreetArtDao getStreetArtDAO();
    public abstract RestaurantDao getRestaurantDAO();
}



