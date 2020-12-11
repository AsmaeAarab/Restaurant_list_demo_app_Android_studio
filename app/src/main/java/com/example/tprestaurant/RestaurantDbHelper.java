package com.example.tprestaurant;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

public class RestaurantDbHelper extends SQLiteOpenHelper {
public static  final int Db_Version=1;
public static final String Table_Name="authentification";
public static final String ID ="id";
public static  final String Login_column = "Login";
public static  final String Pswd_column = "password";
public static  final String Db_Name = "Restaurant";
private static final String SQL_Create_Entries=
        "CREATE TABLE "+Table_Name+" ("+Login_column+" TEXT,"
        +Pswd_column +" TEXT)";
public RestaurantDbHelper(Context context){
    super(context, Db_Name,null, Db_Version);
}

    public RestaurantDbHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_Create_Entries);
        createDfaultAuthentification();
        Log.d("Restaurant","database created");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
    public void createDfaultAuthentification() {
        int count = this.getAuthCount();
        Authentification auth;
        if (count == 0) {
            auth = new Authentification("asmae", "123456");
            this.addAuthentification(auth);
        }
    }

    private void addAuthentification(Authentification auth) {
        Log.i("Authentification", "MyDatabaseHelper.addNote ... " + auth.getLogin());

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(Login_column, auth.getLogin());
        values.put(Pswd_column, auth.getPswd());

        // Inserting Row
        db.insert(Table_Name, null, values);
        // Closing database connection
        db.close();
    }

    private int getAuthCount() {
        Log.i("Auth", "MyDatabaseHelper.getNotesCount ... ");

        String countQuery = "SELECT  * FROM " + Table_Name;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);

        int count = cursor.getCount();

        cursor.close();

        // return count
        return count;
    }
    public Authentification getAuth(String login,String pswd){
        Log.i("Auth item", "MyDatabaseHelper.getAuthentification ... " );

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Table_Name, new String[] { Login_column,
                        Pswd_column }, Login_column+"=? and "+Pswd_column+"=?",
                new String[] { String.valueOf(login), String.valueOf(pswd)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        Authentification auth = new Authentification(cursor.getString(0),cursor.getString(1));
        cursor.close();
        return auth;
    }

    }
