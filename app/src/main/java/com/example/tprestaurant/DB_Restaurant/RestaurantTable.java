package com.example.tprestaurant.DB_Restaurant;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationManager;
import android.util.Log;

import androidx.core.app.ActivityCompat;

import com.example.tprestaurant.Model.Restaurant;

import java.util.ArrayList;

public class RestaurantTable {
    public static CategoryTable categoryTableClass = new CategoryTable();

    private static final String Table_Restaurant = "restaurants";
    private static final String idRestaurant_column = "IdRestaurant";
    private static final String nameRestaurant_column = "NameRestaurant";
    private static final String distanceRestaurant_column = "DistanceRestaurant";
    private static final String statutRestaurant_column = "StatutRestaurant";
    private static final String iDCategoryRestaurant_column = "IDCategoryRestaurant";
    private static final String latitudeRestaurant_column = "LatitudeRestaurant_column";
    private static final String longitudeRestaurant_column = "LongitudeRestaurant_column";
    private static final String SQL_Create_Restaurant =
            "CREATE TABLE " + Table_Restaurant + " (" + idRestaurant_column + " INTEGER PRIMARY KEY AUTOINCREMENT, " + nameRestaurant_column + " TEXT, "
                    + statutRestaurant_column + " TEXT, " + distanceRestaurant_column + " TEXT, " + iDCategoryRestaurant_column + " INTEGER, " +
                    latitudeRestaurant_column + " float, " + longitudeRestaurant_column + " float, " +
                    "FOREIGN KEY(" + iDCategoryRestaurant_column + ") REFERENCES " + categoryTableClass.getTable_Categories() + "(" + categoryTableClass.getIdCategory_column() + "))";

    public static CategoryTable getCategoryTableClass() {
        return categoryTableClass;
    }

    public static String getTable_Restaurant() {
        return Table_Restaurant;
    }

    public static String getIdRestaurant_column() {
        return idRestaurant_column;
    }

    public static String getNameRestaurant_column() {
        return nameRestaurant_column;
    }

    public static String getDistanceRestaurant_column() {
        return distanceRestaurant_column;
    }

    public static String getStatutRestaurant_column() {
        return statutRestaurant_column;
    }

    public static String getiDCategoryRestaurant_column() {
        return iDCategoryRestaurant_column;
    }

    public static String getLatitudeRestaurant_column() {
        return latitudeRestaurant_column;
    }

    public static String getLongitudeRestaurant_column() {
        return longitudeRestaurant_column;
    }

    public static String getSQL_Create_Restaurant() {
        return SQL_Create_Restaurant;
    }

    public static void CreateDefaultRestaurants(SQLiteDatabase db,Context context) {
        int count = getCountRestaurants(db);
        if (count == 0) {
            addRestaurant(db, new Restaurant("IL Forno - Restaurant italien & Pizzeria", "ouvert", "500", 1, 34.25531, -6.58355),context);
            addRestaurant(db, new Restaurant("Domino's Pizza", "fermé", "500", 1, 34.25363, -6.58066),context);
            addRestaurant(db, new Restaurant("So Pizza Kenitra", "ouvert", "500", 1, 34.26198, -6.58406),context);

            addRestaurant(db, new Restaurant("Espace Bahij Fast Food", "ouvert", "500", 2, 34.26108, -6.58610),context);
            addRestaurant(db, new Restaurant("McDonald's kénitra", "ouvert", "500", 2, 34.25041, -6.57263),context);
            addRestaurant(db, new Restaurant("KFC", "ouvert", "500", 2, 34.26057, -6.58289),context);

            addRestaurant(db, new Restaurant("Kwik One Tacos", "ouvert", "500", 3, 34.25986, -6.58812),context);
            addRestaurant(db, new Restaurant("Anzi Tacos", "ouvert", "500", 3, 34.25958, -6.58539),context);
            addRestaurant(db, new Restaurant("Step Burger", "ouvert", "500", 3, 34.26188, -6.58440),context);

            addRestaurant(db, new Restaurant("Chhiwate Ryad Naji", "ouvert", "500", 4, 34.26109, -6.57949),context);
            addRestaurant(db, new Restaurant("Dar Tajine", "ouvert", "500", 4, 34.26289, -6.59243),context);
            addRestaurant(db, new Restaurant("City Poisson", "ouvert", "500", 4, 34.26412, -6.58097),context);

        }
    }

    private static int getCountRestaurants(SQLiteDatabase db) {
        int count = -1;
        String query = "SELECT * FROM " + Table_Restaurant;
        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null && !cursor.isClosed())
            count = cursor.getCount();
        return count;
    }

    public static void addRestaurant(SQLiteDatabase db, Restaurant restaurant, Context context) {
        ContentValues values = new ContentValues();
        values.put(idRestaurant_column, restaurant.getIdRestaurant());
        values.put(nameRestaurant_column, restaurant.getNomRestaurant());

        values.put(statutRestaurant_column, restaurant.getStatutRestaurant());
        values.put(iDCategoryRestaurant_column, restaurant.getIdCategory());
        values.put(latitudeRestaurant_column, restaurant.getLatitude());
        values.put(longitudeRestaurant_column, restaurant.getLongitude());

        LocationManager lm = (LocationManager) context.getSystemService(context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Location currentLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        Location goalLocation = new Location("goalLocation");
        float distance=currentLocation.distanceTo(goalLocation);
        restaurant.setDistance(distance+" m");
        values.put(distanceRestaurant_column, restaurant.getDistance());
        // Inserting Row
        db.insert(Table_Restaurant, null, values);
    }
    public static ArrayList<Restaurant> getRestaurantByCathegory(SQLiteDatabase db, Integer idCategory){
        ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();
        String query = "SELECT * FROM "+Table_Restaurant +" WHERE "+iDCategoryRestaurant_column+"="+idCategory;
        Cursor cursor = db.rawQuery(query,null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    Restaurant restaurant_item= new Restaurant(cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getInt(4),cursor.getDouble(5), cursor.getDouble(6));
                    restaurants.add(restaurant_item);
                } while (cursor.moveToNext());
            }
        }
        catch (Exception e){
            Log.i("error","get restaurants by category exception");
            return null;
        }

        return restaurants;
    }
}
