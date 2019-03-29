package com.example.comicbookroute.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import com.example.comicbookroute.model.BookRoute;
import com.example.comicbookroute.model.BookRouteDataSource;
import com.google.android.gms.maps.model.LatLng;
import com.example.comicbookroute.model.BookRouteDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.concurrent.ExecutionException;

public class BookRouteHandler extends Handler {

    private Context context;

    public BookRouteHandler( Context context) {
        this.context = context;
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
                String photo = fields.getJSONObject("photo").getString("id");
                String pictureURL = "https://bruxellesdata.opendatasoft.com/explore/dataset/"
                        + currentRecords.getString("datasetid")
                        + "/images/"
                        + photo
                        + "/300";

                DownloadImageTask task = new DownloadImageTask(photo, context);
                task.execute(pictureURL);
                BookRoute currentBookRoute = new BookRoute(photo+".jpeg", personnage, auteur, annee);
                BookRouteDatabase.getInstance(context).getBookRouteDAO().insertBookRoute(currentBookRoute);
                index++;


               JSONArray locationJSON_Array = fields.getJSONArray("coordonnees_geographiques");
               Double latitude = locationJSON_Array.getDouble(0);
               Double longitude = locationJSON_Array.getDouble(1);

               LatLng coordinnee = new LatLng(latitude,longitude);



                BookRoute currentBookRoute = new BookRoute(photo, personnage, auteur, latitude, longitude, annee);
                BookRouteDataSource.getInstance().addBookRoute(currentBookRoute);

               index++;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    private static class DownloadImageTask extends AsyncTask<String, Void, String> {

        private String name;
        private WeakReference<Context> contextReference;

        DownloadImageTask(String name, Context context) {
            this.name = name;
            contextReference = new WeakReference<>(context);
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                InputStream inputStream = new URL(strings[0]).openStream();
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                inputStream.close();

                FileOutputStream foStream = contextReference.get().openFileOutput(name + ".jpeg", Context.MODE_PRIVATE);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, foStream);
                foStream.close();

                return name + ".jpeg";

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }
}
