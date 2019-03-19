package com.example.comicbookroute.model;



public class BookRoute {
    private String photo;
    private String personnage;
    private String auteur;
    private String annee;

    public BookRoute() {
    }

    public BookRoute(String photo, String personnage, String auteur, String annee) {
        this.photo = photo;
        this.personnage = personnage;
        this.auteur = auteur;
        this.annee = annee;
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
                "photo='" + photo + '\'' +
                ", personnage='" + personnage + '\'' +
                ", auteur='" + auteur + '\'' +
                ", annee='" + annee + '\'' +
                '}';
    }
}

