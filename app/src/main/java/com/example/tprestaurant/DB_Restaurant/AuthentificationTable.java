package com.example.tprestaurant.DB_Restaurant;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.tprestaurant.Model.Authentification;

public class AuthentificationTable {
    private static final String TAG = "authentification table";
    private static final String Table_Authentification = "authentification";
    private static final String Login_column = "Login";
    private static final String Pswd_column = "Password";
    private static final String SQL_Create_Authentification =
            "CREATE TABLE " + Table_Authentification + " (" + Login_column + " TEXT,"
                    + Pswd_column + " TEXT)";

    public static String getTAG() {
        return TAG;
    }

    public static String getTable_Authentification() {
        return Table_Authentification;
    }

    public static String getLogin_column() {
        return Login_column;
    }

    public static String getPswd_column() {
        return Pswd_column;
    }

    public static String getSQL_Create_Authentification() {
        return SQL_Create_Authentification;
    }

    public static void createDfaultAuthentification(SQLiteDatabase db) {
        int count = getAuthCount(db);
        if (count == 0) {
            Authentification auth01 = new Authentification("asmae", "123456");
            Authentification auth02 = new Authentification("admin", "admin");
            addAuthentification(db, auth01);
            addAuthentification(db, auth02);
        }
    }

    private static void addAuthentification(SQLiteDatabase db, Authentification auth) {
        Log.i(TAG, "MyDatabaseHelper.addNote ... " + auth.getLogin());
        ContentValues values = new ContentValues();
        values.put(Login_column, auth.getLogin());
        values.put(Pswd_column, auth.getPswd());
        // Inserting Row
        db.insert(Table_Authentification, null, values);
    }

    private static int getAuthCount(SQLiteDatabase db) {
        Log.i("Auth", "MyDatabaseHelper.getNotesCount ... ");
        int count = -1;
        String countQuery = "SELECT  * FROM " + Table_Authentification;
        Cursor cursor = db.rawQuery(countQuery, null);
        if (cursor != null && !cursor.isClosed()) {
            count = cursor.getCount();
        }
        return count;
    }

    public static Authentification getAuth( SQLiteDatabase db, String login, String pswd) {
        Log.i("Auth item", "MyDatabaseHelper.getAuthentification ... ");
        Authentification auth = null;
        Cursor cursor = db.query(Table_Authentification, new String[]{Login_column,
                        Pswd_column}, Login_column + "=? and " + Pswd_column + "=?",
                new String[]{String.valueOf(login), String.valueOf(pswd)}, null, null, null, null);
        try {
            if (cursor != null)
                cursor.moveToFirst();
            auth = new Authentification(cursor.getString(0), cursor.getString(1));
        } catch (Exception e) {
            return null;
        } finally {
            cursor.close();
        }

        return auth;
    }
}
