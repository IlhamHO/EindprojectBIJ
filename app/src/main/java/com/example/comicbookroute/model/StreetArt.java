package com.example.comicbookroute.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;
@Entity
public class StreetArt implements Serializable {
    @PrimaryKey(autoGenerate = true)
    private long id;
    private String werkNaam;
    private String kunstenaar;
    private String adres;
    private String photo;
    private Double latitude;
    private Double longitude;

    public StreetArt() {
    }

    @Ignore
    public StreetArt(String werkNaam, String kunstenaar, String adres, String photo, Double latitude, Double longitude) {
        this.werkNaam = werkNaam;
        this.kunstenaar = kunstenaar;
        this.adres = adres;
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

    public String getWerkNaam() {
        return werkNaam;
    }

    public void setWerkNaam(String werkNaam) {
        this.werkNaam = werkNaam;
    }

    public String getKunstenaar() {
        return kunstenaar;
    }

    public void setKunstenaar(String kunstenaar) {
        this.kunstenaar = kunstenaar;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
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