package com.example.comicbookroute.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import com.example.comicbookroute.model.BookRouteDatabase;
import com.example.comicbookroute.model.StreetArt;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.URL;

public class StreetArtHandler extends Handler {

    private Context context;

    public StreetArtHandler(Context context) {
        this.context = context;
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        String data = (String) msg.obj;
        try {
            JSONObject rootObj = new JSONObject(data);
            JSONArray records = rootObj.getJSONArray("records");
            int nrOfRecords = records.length();
            int index = 0;

            while (index < nrOfRecords) {
                JSONObject currentRecords = records.getJSONObject(index);
                JSONObject fields = currentRecords.getJSONObject("fields");

                String kunstenaar = (fields.has("naam_van_de_kunstenaar")) ? fields.getString("naam_van_de_kunstenaar") : "geen kunstenaar";
                String photo = fields.getJSONObject("photo").getString("id");
                String pictureURL = "https://bruxellesdata.opendatasoft.com/explore/dataset/"
                        + currentRecords.getString("datasetid")
                        + "/files/"
                        + photo
                        + "/300";
                JSONArray locationJSON_Array = fields.getJSONArray("geocoordinates");
                Double latitude = locationJSON_Array.getDouble(0);
                Double longitude = locationJSON_Array.getDouble(1);

                StreetArtHandler.DownloadImageTask task = new DownloadImageTask(photo, context);
                task.execute(pictureURL);
                StreetArt currentStreetArt = new StreetArt(kunstenaar, photo + ".jpeg", latitude, longitude);
                BookRouteDatabase.getInstance(context).getStreetArtDAO().insertStreetArt(currentStreetArt);

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


