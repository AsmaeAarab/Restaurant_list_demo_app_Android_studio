package com.example.tprestaurant;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CategoryDbHelper extends SQLiteOpenHelper {

    public static  final int Db_Version=1;
    public static final String Table_Categories="categories";
    public static final String TAG ="categories table";
    public static  final String idCategory_column = "IdCategory";
    public static  final String imageCategory_column = "ImageCategoryID";
    public static  final String titleCategory_column = "TitleCategory";
    public static  final String Db_Name = "Restaurant";

    /* Statement to create the table categories */
    private static final String SQL_Create_Entries=
            "CREATE TABLE "+Table_Categories+" ("+idCategory_column+" INTEGER PRIMARY KEY AUTOINCREMENT, "+ imageCategory_column +" INTEGER, "
                    +titleCategory_column +" TEXT)";
    /* contructors */
    public CategoryDbHelper(Context context){
        super(context, Db_Name,null, Db_Version);
    }

    public CategoryDbHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(SQL_Create_Entries);
        this.createDfaultcategories();
        Log.d(TAG,"database created");
    }

    private void createDfaultcategories() {
        int count = this.getCountLines();
        if(count==0)
        {
            addCategory( new Category(R.drawable.pizza,"Pizza"));
            addCategory( new Category(R.drawable.fast_food,"Fast Food"));
            addCategory( new Category(R.drawable.tacos,"Tacos"));
            addCategory( new Category(R.drawable.marocain_food,"Marocain"));
        }
    }

    private int getCountLines() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query="SELECT * FROM "+Table_Categories;
        Cursor cursor = db.rawQuery(query,null);
        int count =cursor.getCount();
        cursor.close();
        return count;
    }

    private void addCategory(Category category) {
        Log.i(TAG, "MyDatabaseHelper.addNote ... " + category.getTitre());

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(imageCategory_column, category.getIdImage());
        values.put(titleCategory_column, category.getTitre());
        // Inserting Row
        db.insert(Table_Categories, null, values);
        // Closing database connection
        db.close();
    }
    public ArrayList<Category> getAllCategories(){
        ArrayList<Category> categories = new ArrayList<Category>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM "+Table_Categories;
        Cursor cursor = db.rawQuery(query,null);
       try{
           if (cursor.moveToFirst()){
               do {
                   Category category = new Category(cursor.getInt(1),cursor.getString(2));
                   categories.add(category);
               }while (cursor.moveToNext());
           }
       }
       catch (Exception e){
           cursor.close();
           return null;
       }finally {
           if (!cursor.isClosed() && cursor != null)
                cursor.close();
       }

        return categories;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }
}
