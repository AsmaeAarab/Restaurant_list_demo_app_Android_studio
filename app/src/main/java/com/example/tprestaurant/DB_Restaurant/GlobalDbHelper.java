package com.example.tprestaurant.DB_Restaurant;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.example.tprestaurant.Model.Authentification;
import com.example.tprestaurant.Model.Category;
import com.example.tprestaurant.Model.MenuOfRestaurant;
import com.example.tprestaurant.Model.Restaurant;

import java.util.ArrayList;

public class GlobalDbHelper extends SQLiteOpenHelper {
    public Context context;
    public static final int Db_Version = 1;
    public static final String TAG = "Database Restaurant";
    public static final String Db_Name = "Restaurant";

    /* contructors */
    public GlobalDbHelper(Context context) {
        super(context, Db_Name, null, Db_Version);
        this.context=context;
    }

    public GlobalDbHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    /* Called when the database is created for the FIRST time.
     If a database already exists on disk with the same DATABASE_NAME, this method will NOT be called.*/
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(AuthentificationTable.getSQL_Create_Authentification());
        db.execSQL(CategoryTable.getSQL_Create_Categories());
        db.execSQL(RestaurantTable.getSQL_Create_Restaurant());
        db.execSQL(MenuItemsTable.getSQL_Create_MenuItems());
        AuthentificationTable.createDfaultAuthentification(db);
        CategoryTable.createDfaultcategories(db);
        RestaurantTable.CreateDefaultRestaurants(db,context);
        MenuItemsTable.createDfaultItems(db);
        Log.d(TAG, "database created");
    }

    // Called when the database needs to be upgraded.
    // This method will only be called if a database already exists on disk with the same DATABASE_NAME,
    // but the DATABASE_VERSION is different than the version of the database that exists on disk.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            // Simplest implementation is to drop all old tables and recreate them
            db.execSQL("DROP TABLE IF EXISTS " + AuthentificationTable.getTable_Authentification());
            db.execSQL("DROP TABLE IF EXISTS " + CategoryTable.getTable_Categories());
            db.execSQL("DROP TABLE IF EXISTS " + RestaurantTable.getTable_Restaurant());
            db.execSQL("DROP TABLE IF EXISTS "+MenuItemsTable.getTable_MenuItems());
            onCreate(db);
        }
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

    /*authentification table fonctions*/
    public Authentification getAuth(String login, String pswd){
        SQLiteDatabase db = this.getReadableDatabase();
        return AuthentificationTable.getAuth(db,login,pswd);
    }

    /*categories table fonctions*/

    public ArrayList<Category> getAllCategories(){
        SQLiteDatabase db = this.getReadableDatabase();
        return CategoryTable.getAllCategories(db);
    }
    public Category getCategory(String TitleCategory){
        SQLiteDatabase db = this.getReadableDatabase();
        return CategoryTable.getCategory(db,TitleCategory);
    }

    /*Restaurant table fonctions*/
    public ArrayList<Restaurant> getRestaurantByCathegory(Integer idCategory){
        SQLiteDatabase db = this.getReadableDatabase();
        return RestaurantTable.getRestaurantByCathegory(db,idCategory);
    }

    public Restaurant getRestaurantById(int id){
        SQLiteDatabase db=this.getReadableDatabase();
        return  RestaurantTable.getRestaurantById(db, id);
    }

    /* MenuItems table fonctions*/
    public ArrayList<MenuOfRestaurant> getMenutemsByRestaurant(int idRestauant){
        SQLiteDatabase db = this.getReadableDatabase();
        return MenuItemsTable.getAllItemsByRestaurant(db,idRestauant);
    }
}
