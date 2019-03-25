package com.example.comicbookroute.model;

import java.util.ArrayList;

public class BookRouteDataSource {

    private static final BookRouteDataSource ourInstance = new BookRouteDataSource();

    public static BookRouteDataSource getInstance() {
        return ourInstance;
    }

    private BookRouteDataSource() {
    }

    private ArrayList<BookRoute> bookRoutes = new ArrayList<>();

    public ArrayList<BookRoute> getBookRoutes() {
        return bookRoutes;
    }

    public void addBookRoute(BookRoute nBookRoute) {

        bookRoutes.add(nBookRoute);
    }
}
