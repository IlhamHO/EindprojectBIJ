package com.example.comicbookroute.model;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(version = 1, entities = {BookRoute.class}, exportSchema = false)
public abstract class FavoriteDataBase extends RoomDatabase {

    private static FavoriteDataBase ourInstance;

    public static FavoriteDataBase getInstance(Context context) {
        if (ourInstance == null) {
            ourInstance = createDatabase(context);
        }
        return ourInstance;
    }

    private static FavoriteDataBase createDatabase(Context context) {
        return Room.databaseBuilder(
                context,
                FavoriteDataBase.class,
                "favorites.db").allowMainThreadQueries().build();
    }

    public abstract FavoriteDao getFavoriteDAO();
}

