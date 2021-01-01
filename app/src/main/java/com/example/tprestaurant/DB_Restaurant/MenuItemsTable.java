package com.example.tprestaurant.DB_Restaurant;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.tprestaurant.Model.MenuOfRestaurant;

import java.util.ArrayList;

public class MenuItemsTable {
    private static RestaurantTable restaurantTable = new RestaurantTable();
    public static final String TAG = "Menu table";
    public static final String Table_MenuItems = "menuItems";
    public static final String TitleMenuItem_column = "TitleMenuItem";
    public static final String PriceMenuItem_column = "PriceMenuItem";
    public static final String RestaurantId_column = "RestaurantId";
    private static final String SQL_Create_MenuItems=
            "CREATE TABLE " + Table_MenuItems + " (" + TitleMenuItem_column + " TEXT, "
                    + PriceMenuItem_column + " TEXT, "+RestaurantId_column+" INTEGER, "
                    +"FOREIGN KEY(" + RestaurantId_column + ") REFERENCES " + restaurantTable.getTable_Restaurant() + "(" + restaurantTable.getIdRestaurant_column() + "))";


    public static String getTable_MenuItems() {
        return Table_MenuItems;
    }

    public static String getTitleMenuItem_column() {
        return TitleMenuItem_column;
    }

    public static String getPriceMenuItem_column() {
        return PriceMenuItem_column;
    }

    public static String getSQL_Create_MenuItems() {
        return SQL_Create_MenuItems;
    }

    public static void createDfaultItems(SQLiteDatabase db) {
        int count = getCountLines(db);
        if (count == 0) {
            add_MenuItems(db,new MenuOfRestaurant("Margherita pizza","42 dh",1));
            add_MenuItems(db,new MenuOfRestaurant("BEEFY pizza","49 dh",1));
            add_MenuItems(db,new MenuOfRestaurant("Poulet Sauce BBQ pizza","49 dh",1));

            add_MenuItems(db,new MenuOfRestaurant("Hawaienne pizza","42 dh",2));
            add_MenuItems(db,new MenuOfRestaurant("Veggie pizza","49 dh",2));
            add_MenuItems(db,new MenuOfRestaurant("Pecheur pizza","49 dh",2));

            add_MenuItems(db,new MenuOfRestaurant("Tex-Mex pizza","42 dh",3));
            add_MenuItems(db,new MenuOfRestaurant("Tex-Mex Poulet pizza","49 dh",3));
            add_MenuItems(db,new MenuOfRestaurant("Américaine pizza","49 dh",3));
           /******************************/
            add_MenuItems(db,new MenuOfRestaurant("Zinger Classic Meal","47 dh",4));
            add_MenuItems(db,new MenuOfRestaurant("Zinger Supreme","42 dh",4));
            add_MenuItems(db,new MenuOfRestaurant("Light Crispy Strips","35 dh",4));

            add_MenuItems(db,new MenuOfRestaurant("Snack Box","47 dh",5));
            add_MenuItems(db,new MenuOfRestaurant("Rizo","42 dh",5));
            add_MenuItems(db,new MenuOfRestaurant("Roller","35 dh",5));

            add_MenuItems(db,new MenuOfRestaurant("Fries","47 dh",6));
            add_MenuItems(db,new MenuOfRestaurant("Coleslaw","42 dh",6));
            add_MenuItems(db,new MenuOfRestaurant("Dinner Box","35 dh",6));
            /******************************/
            add_MenuItems(db,new MenuOfRestaurant("Tacos nuggets","42 dh",7));
            add_MenuItems(db,new MenuOfRestaurant("Tacos poulet","49 dh",7));
            add_MenuItems(db,new MenuOfRestaurant("Tacos swiss","49 dh",7));

            add_MenuItems(db,new MenuOfRestaurant("Tacos mixte","42 dh",8));
            add_MenuItems(db,new MenuOfRestaurant("Tacos country","49 dh",8));
            add_MenuItems(db,new MenuOfRestaurant("Tacos Cordon Bleu","49 dh",8));

            add_MenuItems(db,new MenuOfRestaurant("Tacos Daily","42 dh",9));
            add_MenuItems(db,new MenuOfRestaurant("Tacos Viande hachée","49 dh",9));
            add_MenuItems(db,new MenuOfRestaurant("Tacos Extreme Tortilla","49 dh",9));
            /******************************/
            add_MenuItems(db,new MenuOfRestaurant("Couscous","42 dh",10));
            add_MenuItems(db,new MenuOfRestaurant("brauchette viande","49 dh",10));
            add_MenuItems(db,new MenuOfRestaurant("hrira","49 dh",10));

            add_MenuItems(db,new MenuOfRestaurant("Tajine","42 dh",11));
            add_MenuItems(db,new MenuOfRestaurant("Salade Marocaine","49 dh",11));
            add_MenuItems(db,new MenuOfRestaurant("Mrozia","49 dh",11));

            add_MenuItems(db,new MenuOfRestaurant("Tajine Poisson","42 dh",12));
            add_MenuItems(db,new MenuOfRestaurant("friture de poisson","49 dh",12));
            add_MenuItems(db,new MenuOfRestaurant("les huitres gratinées","49 dh",12));

        }
    }

    private static int getCountLines(SQLiteDatabase db) {
        String query = "SELECT * FROM " + Table_MenuItems;
        Cursor cursor = db.rawQuery(query, null);
        int count = -1;
        if (cursor != null && !cursor.isClosed()) {
            count = cursor.getCount();
            // cursor.close();
        }
        return count;
    }

    private static void add_MenuItems(SQLiteDatabase db, MenuOfRestaurant menuItem) {
        Log.i(TAG, "MyDatabaseHelper.addNote ... ");

        // SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TitleMenuItem_column, menuItem.getMeal_Name());
        values.put(PriceMenuItem_column, menuItem.getMeal_prix());
        values.put(RestaurantId_column, menuItem.getIdRestaurant());
        // Inserting Row
        db.insert(Table_MenuItems, null, values);
    }

    public static ArrayList<MenuOfRestaurant> getAllItemsByRestaurant(SQLiteDatabase db, int idRestauant){
        ArrayList<MenuOfRestaurant> menuOfRestaurant = new ArrayList<MenuOfRestaurant>();
        String query = "SELECT * FROM "+Table_MenuItems +" WHERE "+RestaurantId_column+"="+idRestauant;
        Cursor cursor = db.rawQuery(query,null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    MenuOfRestaurant menuOfRestaurantItem = new MenuOfRestaurant(cursor.getString(0),cursor.getString(1),cursor.getInt(2));
                    menuOfRestaurant.add(menuOfRestaurantItem);
                } while (cursor.moveToNext());
            }
        }
        catch (Exception e){
            Log.i("error","get menu by restaurant exception");
            return null;
        }

        return menuOfRestaurant;
    }

}
