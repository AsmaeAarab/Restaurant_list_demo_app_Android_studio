package com.example.tprestaurant.DB_Restaurant;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.tprestaurant.Model.Category;
import com.example.tprestaurant.R;

import java.util.ArrayList;

public class CategoryTable {
    public static final String TAG = "category table";
    public static final String Table_Categories = "categories";
    public static final String idCategory_column = "IdCategory";
    public static final String imageCategory_column = "ImageCategoryID";
    public static final String titleCategory_column = "TitleCategory";
    private static final String SQL_Create_Categories =
            "CREATE TABLE " + Table_Categories + " (" + idCategory_column + " INTEGER PRIMARY KEY AUTOINCREMENT, " + imageCategory_column + " INTEGER, "
                    + titleCategory_column + " TEXT)";

    public static String getTable_Categories() {
        return Table_Categories;
    }

    public static String getIdCategory_column() {
        return idCategory_column;
    }

    public static String getImageCategory_column() {
        return imageCategory_column;
    }

    public static String getTitleCategory_column() {
        return titleCategory_column;
    }

    public static String getSQL_Create_Categories() {
        return SQL_Create_Categories;
    }

    public static void createDfaultcategories(SQLiteDatabase db) {
        int count = getCountLines(db);
        if (count == 0) {

            addCategory(db, new Category(R.drawable.pizza, "Pizza"));
            addCategory(db, new Category(R.drawable.fast_food, "Fast Food"));
            addCategory(db, new Category(R.drawable.tacos, "Tacos"));
            addCategory(db, new Category(R.drawable.marocain_food, "Marocain"));
        }
    }

    private static int getCountLines(SQLiteDatabase db) {
        //SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + Table_Categories;
        Cursor cursor = db.rawQuery(query, null);
        int count = -1;
        if (cursor != null && !cursor.isClosed()) {
            count = cursor.getCount();
            // cursor.close();
        }
        return count;
    }

    private static void addCategory(SQLiteDatabase db, Category category) {
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

    public static ArrayList<Category> getAllCategories( SQLiteDatabase db) {
        ArrayList<Category> categories = new ArrayList<Category>();

        String query = "SELECT * FROM " + Table_Categories;
        Cursor cursor = db.rawQuery(query, null);
        try {
            if (cursor.moveToFirst()) {
                do {
//                    int ImageId = R.drawable.android_platform;
  //                  Log.i("image id", String.valueOf(ImageId));
                    // DrawableRes monDrawble = ContextCompat.getDrawable(, ImageId);
                    Category category = new Category(cursor.getInt(1), cursor.getString(2));// cursor.getInt(1)
                    categories.add(category);
                } while (cursor.moveToNext());
            }
        } catch (Exception e) {
            return null;
        } finally {
            if (!cursor.isClosed() && cursor != null) {
            }

        }
        return categories;
    }

    public static Category getCategory(SQLiteDatabase db, String TitleCategory) {
        Cursor cursor = db.query(Table_Categories,
                new String[]{idCategory_column, imageCategory_column, titleCategory_column},
                titleCategory_column + "=?",
                new String[]{TitleCategory},
                null, null, null, null);
        Category category = null;
        try {
            if (cursor != null)
                cursor.moveToFirst();
            category = new Category(cursor.getInt(1), cursor.getString(2));
            category.setId(cursor.getInt(0));
        } catch (Exception e) {
            return null;
        } finally {
            cursor.close();
        }
        return category;
    }
}
