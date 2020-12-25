package com.example.tprestaurant.Menu_Data;

import java.util.HashMap;

public class MenuData {
    public static HashMap MenuFastfood(int idRestaurant)
    {
        HashMap<String, String> menu=new HashMap<String, String>();
        menu.put("id",new Integer(idRestaurant).toString());
        menu.put("Zinger Classic Meal","47 dh");
        menu.put("Zinger Supreme","42 dh");
        menu.put("Light Crispy Strips","35 dh");
        return  menu;
    }
    public static HashMap MenuPizza(int idRestaurant)
    {
        HashMap<String, String> menu=new HashMap<String, String>();
        menu.put("id",new Integer(idRestaurant).toString());
        menu.put("Margherita pizza","42 dh");
        menu.put("BEEFY pizza","49 dh");
        menu.put("Poulet Sauce BBQ pizza","49 dh");
        return  menu;
    }

    public static HashMap MenuTacos(int idRestaurant)
    {
        HashMap<String, String> menu=new HashMap<String, String>();
        menu.put("id",new Integer(idRestaurant).toString());
        menu.put("Tacos nuggets","42 dh");
        menu.put("Tacos poulet","49 dh");
        menu.put("Tacos swiss","49 dh");
        return  menu;
    }

    public static HashMap MenuMarocain(int idRestaurant)
    {
        HashMap<String, String> menu=new HashMap<String, String>();
        menu.put("id",new Integer(idRestaurant).toString());
        menu.put("Couscous","42 dh");
        menu.put("brauchette viande","49 dh");
        menu.put("soupe marocaine (hrira)","49 dh");
        return  menu;
    }

}
