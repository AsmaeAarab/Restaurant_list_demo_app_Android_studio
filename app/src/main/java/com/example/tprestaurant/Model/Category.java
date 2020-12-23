package com.example.tprestaurant.Model;

import java.io.Serializable;

public class Category implements Serializable {
    private Integer Id;
    private Integer idImage;
    private String titre;

    public Category(Integer idImage, String titre) {
        this.idImage = idImage;
        this.titre = titre;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public Integer getIdImage() {
        return idImage;
    }

    public void setIdImage(Integer idImage) {
        this.idImage = idImage;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }
}
