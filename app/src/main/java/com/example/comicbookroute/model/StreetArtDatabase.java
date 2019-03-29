package com.example.comicbookroute.model;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
@Database(version = 1, entities = {StreetArt.class}, exportSchema = false)
public abstract class StreetArtDatabase extends RoomDatabase {
    private static StreetArtDatabase myInstance;

    public static StreetArtDatabase getInstance(Context context) {
        if(myInstance == null){
            myInstance = createDatabase(context);
        }
        return myInstance;
    }

    private static StreetArtDatabase createDatabase(Context context) {
        return Room.databaseBuilder(
                context,
               StreetArtDatabase.class,
                "streetarts.db").allowMainThreadQueries().build();
    }

    public abstract StreetArtDao getStreetArtDAO();


}

