package com.example.tprestaurant.DB_Restaurant;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

import com.example.tprestaurant.Model.Restaurant;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class RestaurantTable implements LocationListener {
    public static CategoryTable categoryTableClass = new CategoryTable();
    static Location currentLocation;

    private static final String Table_Restaurant = "restaurants";
    private static final String idRestaurant_column = "IdRestaurant";
    private static final String nameRestaurant_column = "NameRestaurant";
    private static final String telRestaurant_column = "TelRestaurant";
    private static final String statutRestaurant_column = "StatutRestaurant";
    private static final String iDCategoryRestaurant_column = "IDCategoryRestaurant";
    private static final String latitudeRestaurant_column = "LatitudeRestaurant_column";
    private static final String longitudeRestaurant_column = "LongitudeRestaurant_column";
    private static final String SQL_Create_Restaurant =
            "CREATE TABLE " + Table_Restaurant + " (" + idRestaurant_column + " INTEGER PRIMARY KEY AUTOINCREMENT, " + nameRestaurant_column + " TEXT, "
                    + statutRestaurant_column + " TEXT, " + telRestaurant_column + " TEXT, " + iDCategoryRestaurant_column + " INTEGER, " +
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
        return telRestaurant_column;
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
            Restaurant restaurant = new Restaurant("IL Forno - Restaurant italien & Pizzeria", "ouvert", "500", 1, 34.25531, -6.58355,"0600000001");
            addRestaurant(db, restaurant, context);
            //restaurant.setIdRestaurant(55);

            addRestaurant(db, new Restaurant("Domino's Pizza", "fermé", "500", 1, 34.25363, -6.58066,"0600000002"),context);
            addRestaurant(db, new Restaurant("So Pizza Kenitra", "ouvert", "500", 1, 34.26198, -6.58406,"0600000003"),context);

            addRestaurant(db, new Restaurant("Espace Bahij Fast Food", "ouvert", "500", 2, 34.26108, -6.58610,"0600000004"),context);
            addRestaurant(db, new Restaurant("McDonald's kénitra", "ouvert", "500", 2, 34.25041, -6.57263,"0600000005"),context);
            addRestaurant(db, new Restaurant("KFC", "ouvert", "500", 2, 34.26057, -6.58289,"0600000006"),context);

            addRestaurant(db, new Restaurant("Kwik One Tacos", "ouvert", "500", 3, 34.25986, -6.58812,"0600000007"),context);
            addRestaurant(db, new Restaurant("Anzi Tacos", "ouvert", "500", 3, 34.25958, -6.58539,"0600000008"),context);
            addRestaurant(db, new Restaurant("Step Burger", "ouvert", "500", 3, 34.26188, -6.58440,"0600000009"),context);

            addRestaurant(db, new Restaurant("Chhiwate Ryad Naji", "ouvert", "500", 4, 34.26109, -6.57949,"0600000010"),context);
            addRestaurant(db, new Restaurant("Dar Tajine", "ouvert", "500", 4, 34.26289, -6.59243,"0600000011"),context);
            addRestaurant(db, new Restaurant("City Poisson", "ouvert", "500", 4, 34.26412, -6.58097,"0600000012"),context);

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
        values.put(nameRestaurant_column, restaurant.getNomRestaurant());
        values.put(statutRestaurant_column, restaurant.getStatutRestaurant());
        values.put(iDCategoryRestaurant_column, restaurant.getIdCategory());
        values.put(latitudeRestaurant_column, restaurant.getLatitude());
        values.put(longitudeRestaurant_column, restaurant.getLongitude());
        values.put(telRestaurant_column, restaurant.getTel());
        db.insert(Table_Restaurant, null, values);
    }
    public static ArrayList<Restaurant> getRestaurantByCathegory(SQLiteDatabase db, Integer idCategory){
        ArrayList<Restaurant> restaurants = new ArrayList<Restaurant>();
        String query = "SELECT * FROM "+Table_Restaurant +" WHERE "+iDCategoryRestaurant_column+"="+idCategory;
        Cursor cursor = db.rawQuery(query,null);
        try {
            if (cursor.moveToFirst()) {
                do {
                    Restaurant restaurant_item= new Restaurant(cursor.getString(1),cursor.getString(2),"",cursor.getInt(4),cursor.getDouble(5), cursor.getDouble(6),cursor.getString(3));
                    restaurant_item.setIdRestaurant(cursor.getInt(0));
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

    public static Restaurant getRestaurantById(SQLiteDatabase db,int id){
        Restaurant restaurant = null;
        String query = "SELECT * FROM "+Table_Restaurant +" WHERE "+idRestaurant_column+"="+id;
        Cursor cursor = db.rawQuery(query,null);
        try {
            if (cursor != null)
                cursor.moveToFirst();
            restaurant = new Restaurant(cursor.getString(1),cursor.getString(2),"",cursor.getInt(4),cursor.getDouble(5), cursor.getDouble(6),cursor.getString(3));
            restaurant.setIdRestaurant(cursor.getInt(0));
        } catch (Exception e) {
            return null;
        } finally {
            cursor.close();
        }
        return restaurant;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static float getDistance(Context context, double latitudeGoal, double longitudeGoal) {
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return 0;
        }
        LocationManager lm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
         currentLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        Location goalLocation = new Location("goalLocation");
        if (currentLocation != null) {
            goalLocation.setLatitude(latitudeGoal);
            goalLocation.setLongitude(longitudeGoal);
            float distance = currentLocation.distanceTo(goalLocation);
            return distance;
        }
        else
            return 0;
    }
    @Override
    public void onLocationChanged(@NonNull Location location) {
        currentLocation.setLatitude(location.getLatitude());
        currentLocation.setLongitude(location.getLongitude());
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static boolean getTime(){
        Date currentTime = Calendar.getInstance().getTime();
        if(currentTime.getHours()>=9 && currentTime.getHours()<=23)
            return true; //open
        return false;
    }

}
