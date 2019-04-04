package com.example.comicbookroute.model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface BookRouteDao {

    @Insert
    void insertBookRoute(BookRoute nBookRoute);

    @Update
    void updateBookRoute(BookRoute nBookRoute);

    @Query("SELECT * FROM BookRoute")
    List<BookRoute> selectAllBookRoutes();

    @Query("SELECT * FROM BookRoute WHERE isFavorite = :isFavorite")
    List<BookRoute> selectAllFavoriteBookRoutes(boolean isFavorite);
}
