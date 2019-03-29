package com.example.comicbookroute.util;

import android.os.Handler;
import android.os.Message;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class StreetArtHandler extends Handler {
    public StreetArtHandler() {
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
                JSONObject currentRecords = (JSONObject) rootObj.getJSONObject(String.valueOf(index));
                JSONObject fields = currentRecords.getJSONObject("fields");


                String werkNaam = (fields.has("name_of_the_work")) ? fields.getString("name_of_the_work") : "geen werk naam";
                String kunstenaar = fields.getString(" kunstenaar");
                String adres = fields.getString("adres");
                String photo = fields.getJSONObject("photo").getString("id");
                String pictureURL = "https://bruxellesdata.opendatasoft.com/explore/dataset/"
                        + currentRecords.getString("datasetid")
                        + "/images/"
                        + photo
                        + "/300";
                JSONArray locationJSON_Array = fields.getJSONArray("geocoordinates");
                Double latitude = locationJSON_Array.getDouble(0);
                Double longitude = locationJSON_Array.getDouble(1);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

