package com.example.comicbookroute.util;

import android.os.Handler;
import android.os.Message;

import com.example.comicbookroute.model.BookRoute;
import com.example.comicbookroute.model.BookRouteDataSource;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class BookRouteHandler extends Handler {

   private BookRouteAdapter mBookRouteAdapter;

    public BookRouteHandler(BookRouteAdapter mBookRouteAdapter) {
        this.mBookRouteAdapter = mBookRouteAdapter;
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);

        String data = (String) msg.obj;
        try {
            JSONObject rootObject = new JSONObject(data);
            JSONArray records = rootObject.getJSONArray("records");
            int nrOfRecords = records.length();
            int index = 0;

            while (index < nrOfRecords) {
                JSONObject currentRecords = records.getJSONObject(index);
                JSONObject fields = currentRecords.getJSONObject("fields");


                String personnage = (fields.has("personnage_s")) ? fields.getString("personnage_s") : "geen personnage";
                String auteur = fields.getString("auteur_s");
                String annee = fields.getString("annee");
                String photo =  fields.getString("photo");

                BookRoute currentBookRoute = new BookRoute(photo, personnage, auteur, annee);
                BookRouteDataSource.getInstance().addBookRoute(currentBookRoute);

                index++;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        mBookRouteAdapter.setItems(BookRouteDataSource.getInstance().getBookRoutes());
        mBookRouteAdapter.notifyDataSetChanged();

    }
}
