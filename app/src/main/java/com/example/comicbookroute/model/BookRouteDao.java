package com.example.comicbookroute.model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface BookRouteDao {

    @Insert
    void insertBookRoute(BookRoute nBookRoute);

    @Query("SELECT * FROM BookRoute")
    List<BookRoute> selectAllBookRoutes();
}
