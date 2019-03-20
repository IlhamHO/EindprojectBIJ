package com.example.comicbookroute.model;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

@Entity
public class BookRoute implements Serializable {
@PrimaryKey(autoGenerate = true)
    private long id;
    private String photo;
    private String personnage;
    private String auteur;
    private Double lat;
    private Double lng;
    private String annee;

    public BookRoute() {
    }
    @Ignore
    public BookRoute(String photo, String personnage, String auteur, String annee) {
        this.photo = photo;
        this.personnage = personnage;
        this.auteur = auteur;
        this.annee = annee;
    }


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getPersonnage() {
        return personnage;
    }

    public void setPersonnage(String personnage) {
        this.personnage = personnage;
    }

    public String getAuteur() {
        return auteur;
    }

    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }

    public String getAnnee() {
        return annee;
    }

    public void setAnnee(String annee) {
        this.annee = annee;
    }

    @Override
    public String toString() {
        return "BookRoute{" +
                "id=" + id +
                ", photo='" + photo + '\'' +
                ", personnage='" + personnage + '\'' +
                ", auteur='" + auteur + '\'' +
                ", lat=" + lat +
                ", lng=" + lng +
                ", annee='" + annee + '\'' +
                '}';
    }
}


