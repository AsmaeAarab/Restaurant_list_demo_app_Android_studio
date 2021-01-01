package com.example.tprestaurant.Model;

import java.io.Serializable;

public class MenuOfRestaurant implements Serializable {
    private String meal_Name;
    private String meal_prix;
    private Integer idRestaurant;

    public String getMeal_Name() {
        return meal_Name;
    }

    public void setMeal_Name(String meal_Name) {
        this.meal_Name = meal_Name;
    }

    public String getMeal_prix() {
        return meal_prix;
    }

    public void setMeal_prix(String meal_prix) {
        this.meal_prix = meal_prix;
    }

    public Integer getIdRestaurant() {
        return idRestaurant;
    }

    public void setIdRestaurant(Integer idRestaurant) {
        this.idRestaurant = idRestaurant;
    }

    public MenuOfRestaurant(String meal_Name, String meal_prix, Integer idRestaurant) {
        this.meal_Name = meal_Name;
        this.meal_prix = meal_prix;
        this.idRestaurant = idRestaurant;
    }
}
