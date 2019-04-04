package com.example.comicbookroute.model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface StreetArtDao {

    @Insert
    void insertStreetArt(StreetArt nStreetArt);

    @Query("SELECT * FROM StreetArt")
    List<StreetArt> selectAllStreetArts();

}


