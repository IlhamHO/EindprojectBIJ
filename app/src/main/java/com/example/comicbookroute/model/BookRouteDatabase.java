package com.example.comicbookroute.model;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(version = 1, entities = {BookRoute.class}, exportSchema = false)
public abstract class BookRouteDatabase extends RoomDatabase {

    private static BookRouteDatabase ourInstance;

    public static BookRouteDatabase getInstance(Context context) {

        return ourInstance;
    }

    private static BookRouteDatabase createDatabase(Context context) {
        return Room.databaseBuilder(context, BookRouteDatabase.class, "bookRoutes.db").allowMainThreadQueries().build();
    }

    public abstract BookRouteDao getBookRouteDAO();
}



