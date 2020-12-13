package com.example.tprestaurant;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class RestaurantDbHelper extends SQLiteOpenHelper {
    public static  final int Db_Version=2;
    public static final String TAG ="Database Restaurant";
    public static  final String Db_Name = "Restaurant";

    /*Authentification table*/
    public static final String Table_Authentification="authentification";

    public static  final String Login_column = "Login";
    public static  final String Pswd_column = "Password";
    private static final String SQL_Create_Authentification=
            "CREATE TABLE "+Table_Authentification+" ("+Login_column+" TEXT,"
            +Pswd_column +" TEXT)";
    /*Categories table*/
    public static final String Table_Categories="categories";
    public static  final String idCategory_column = "IdCategory";
    public static  final String imageCategory_column = "ImageCategoryID";
    public static  final String titleCategory_column = "TitleCategory";
    private static final String SQL_Create_Categories=
            "CREATE TABLE "+Table_Categories+" ("+idCategory_column+" INTEGER PRIMARY KEY AUTOINCREMENT, "+ imageCategory_column +" INTEGER, "
                    +titleCategory_column +" TEXT)";

    /* contructors */
    public RestaurantDbHelper(Context context){
        super(context, Db_Name,null, Db_Version);
    }

    public RestaurantDbHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    /* Called when the database is created for the FIRST time.
     If a database already exists on disk with the same DATABASE_NAME, this method will NOT be called.*/
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_Create_Authentification);
        db.execSQL(SQL_Create_Categories);
       createDfaultAuthentification(db);
       createDfaultcategories(db);
        Log.d(TAG,"database created");
    }

    // Called when the database needs to be upgraded.
    // This method will only be called if a database already exists on disk with the same DATABASE_NAME,
    // but the DATABASE_VERSION is different than the version of the database that exists on disk.
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            // Simplest implementation is to drop all old tables and recreate them
            db.execSQL("DROP TABLE IF EXISTS " + Table_Authentification);
            db.execSQL("DROP TABLE IF EXISTS " + Table_Categories);
            onCreate(db);
        }
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
    public void createDfaultvalues(SQLiteDatabase db){
        int count = this.getAuthCount(db);
    }
/*Authentification table fonctions*/
    public void createDfaultAuthentification(SQLiteDatabase db) {

        int count = this.getAuthCount(db);
        Authentification auth;
        if (count == 0) {
            auth = new Authentification("asmae", "123456");
            this.addAuthentification(db,auth);
        }
    }

    private void addAuthentification(SQLiteDatabase db,Authentification auth) {
        Log.i(TAG, "MyDatabaseHelper.addNote ... " + auth.getLogin());

        //SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(Login_column, auth.getLogin());
        values.put(Pswd_column, auth.getPswd());
        // Inserting Row
        db.insert(Table_Authentification, null, values);
        // Closing database connection
        //db.close();
    }

    private int getAuthCount(SQLiteDatabase db) {
        Log.i("Auth", "MyDatabaseHelper.getNotesCount ... ");
        int count = -1;
        String countQuery = "SELECT  * FROM " + Table_Authentification;
      //  SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        if(cursor != null && !cursor.isClosed()){
            count = cursor.getCount();
         //   cursor.close();
        }
        return count;
    }
    public Authentification getAuth(String login,String pswd){
        Log.i("Auth item", "MyDatabaseHelper.getAuthentification ... " );

        SQLiteDatabase db = this.getReadableDatabase();
        Authentification auth=null;
        Cursor cursor = db.query(Table_Authentification, new String[] { Login_column,
                        Pswd_column }, Login_column+"=? and "+Pswd_column+"=?",
                new String[] { String.valueOf(login), String.valueOf(pswd)}, null, null, null, null);
        try {
            if (cursor != null)
                cursor.moveToFirst();
                 auth = new Authentification(cursor.getString(0),cursor.getString(1));
        }
        catch (Exception e){
            return null;
        }
        finally {
            cursor.close();
        }

        return auth;
    }

/* categories table fonctions*/
    private void createDfaultcategories(SQLiteDatabase db) {
        int count = this.getCountLines(db);
        if(count==0)
        {

            addCategory(db, new Category(R.drawable.pizza,"Pizza"));
            addCategory(db, new Category(R.drawable.fast_food,"Fast Food"));
            addCategory(db, new Category(R.drawable.tacos,"Tacos"));
            addCategory(db, new Category(R.drawable.marocain_food,"Marocain"));
        }
    }

    private int getCountLines(SQLiteDatabase db) {
        //SQLiteDatabase db = this.getReadableDatabase();
        String query="SELECT * FROM "+Table_Categories;
        Cursor cursor = db.rawQuery(query,null);
        int count = -1;
        if(cursor != null && !cursor.isClosed()){
            count = cursor.getCount();
           // cursor.close();
        }
        return count;
    }

    private void addCategory(SQLiteDatabase db, Category category) {
        Log.i(TAG, "MyDatabaseHelper.addNote ... " + category.getTitre());

       // SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(imageCategory_column, category.getIdImage());
        values.put(titleCategory_column, category.getTitre());
        // Inserting Row
        db.insert(Table_Categories, null, values);
        // Closing database connection
        //db.close();
    }
    public ArrayList<Category> getAllCategories(){
        ArrayList<Category> categories = new ArrayList<Category>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM "+Table_Categories;
        Cursor cursor = db.rawQuery(query,null);
        try{
            if (cursor.moveToFirst()){
                do {
                  int ImageId = R.drawable.android_platform;
                  Log.i("image id", String.valueOf(ImageId));
                  // DrawableRes monDrawble = ContextCompat.getDrawable(, ImageId);
                    Category category = new Category(cursor.getInt(1),cursor.getString(2));// cursor.getInt(1)
                    categories.add(category);
                }while (cursor.moveToNext());
            }
        }
        catch (Exception e){
           // cursor.close();
            return null;
        }finally {
            if (!cursor.isClosed() && cursor != null)
            {
                // cursor.close();
            }

        }

        return categories;
    }

    }
