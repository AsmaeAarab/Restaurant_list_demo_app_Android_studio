package com.example.tprestaurant;

public class Category {
    private Integer idImage;
    private String titre;

    public Category(Integer idImage, String titre) {
        this.idImage = idImage;
        this.titre = titre;
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
