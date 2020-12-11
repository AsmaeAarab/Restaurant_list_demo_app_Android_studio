package com.example.tprestaurant;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;
// hello git
public class RestaurantDbHelper extends SQLiteOpenHelper {
public static  final int Db_Version=1;
public static final String Table_Name="authentification";
public static final String TAG ="authentification table";
public static  final String Login_column = "Login";
public static  final String Pswd_column = "Password";
public static  final String Db_Name = "Restaurant";

    /* Statement to create the table authentification */
    private static final String SQL_Create_Entries=
            "CREATE TABLE "+Table_Name+" ("+Login_column+" TEXT,"
            +Pswd_column +" TEXT)";
    /* contructors */
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
        Log.d(TAG,"database created");
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
            auth = new Authentification("bouchra", "123456");
            this.addAuthentification(auth);
        }
    }

    private void addAuthentification(Authentification auth) {
        Log.i(TAG, "MyDatabaseHelper.addNote ... " + auth.getLogin());

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
        Authentification auth=null;
        Cursor cursor = db.query(Table_Name, new String[] { Login_column,
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

    }
