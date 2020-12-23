package com.example.tprestaurant.Model;

import java.io.Serializable;

public class Restaurant implements Serializable {
    private Integer idRestaurant;
    private String nomRestaurant;
    private String statutRestaurant; //fermé ou ouvert
    private String distance;
    private Integer idCategory;
    private double longitude;
    private double latitude;
    public Restaurant(String nomRestaurant, String statutRestaurant, String distance,Integer idCategory,double latitude,double longitude) {
        this.nomRestaurant = nomRestaurant;
        this.statutRestaurant = statutRestaurant;
        this.distance = distance;
        this.idCategory = idCategory;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Integer getIdRestaurant() {
        return idRestaurant;
    }

    public void setIdRestaurant(Integer idRestaurant) {
        this.idRestaurant = idRestaurant;
    }

    public String getNomRestaurant() {
        return nomRestaurant;
    }

    public void setNomRestaurant(String nomRestaurant) {
        this.nomRestaurant = nomRestaurant;
    }

    public String getStatutRestaurant() {
        return statutRestaurant;
    }

    public void setStatutRestaurant(String statutRestaurant) {
        this.statutRestaurant = statutRestaurant;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public Integer getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(Integer idCategory) {
        this.idCategory = idCategory;
    }
}
