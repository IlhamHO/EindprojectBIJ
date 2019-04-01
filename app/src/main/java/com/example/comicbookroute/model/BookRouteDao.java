package com.example.comicbookroute.model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface BookRouteDao {

    @Insert
    void insertBookRoute(BookRoute nBookRoute);

    @Delete
    void deleteBookRoute(BookRoute nBookRoute);

    @Query("SELECT * FROM BookRoute")
    List<BookRoute> selectAllBookRoutes();

    @Query("SELECT * FROM BookRoute WHERE id = :id")
    BookRoute selectBookRouteByID(long id);


}
