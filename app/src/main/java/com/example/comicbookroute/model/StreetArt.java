package com.example.comicbookroute.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class StreetArt implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String kunstenaar;
    private String photo;
    private Double latitude;
    private Double longitude;

    public StreetArt() {
    }

    @Ignore
    public StreetArt(String kunstenaar, String photo, Double latitude, Double longitude) {
        this.kunstenaar = kunstenaar;
        this.photo = photo;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getKunstenaar() {
        return kunstenaar;
    }

    public void setKunstenaar(String kunstenaar) {
        this.kunstenaar = kunstenaar;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
