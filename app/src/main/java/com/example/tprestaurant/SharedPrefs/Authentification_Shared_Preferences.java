package com.example.tprestaurant.SharedPrefs;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class Authentification_Shared_Preferences {
    public static final String SHARED_PREFS = "sharedPrefs";
    public static String rememberMe_login;
    public static String rememberMe_password;
    public static boolean rememberMe_checked;

    public static void SaveAuthentification(Context context, CheckBox remember_me, EditText login, EditText password){
        if(remember_me.isChecked()){
            SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS,context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("login",login.getText().toString());
            editor.putString("password",password.getText().toString());
            editor.putBoolean("remember_me", remember_me.isChecked());//true
            editor.apply();
            Toast.makeText(context,"votre login et password sont sauvegardé",Toast.LENGTH_LONG).show();
        }
    }

    public static String LoadLogin_value(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS,context.MODE_PRIVATE);
        return sharedPreferences.getString("login","");
    }
    public static String LoadPassword_value(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS,context.MODE_PRIVATE);
        return sharedPreferences.getString("password","");
    }
    public static Boolean LoadCheckBox_value(Context context){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS,context.MODE_PRIVATE);
        return sharedPreferences.getBoolean("remember_me",false);
    }

    public static void updateViews(EditText login, EditText password, String loginValue, String passwordValue){
        login.setText(loginValue);
        password.setText(passwordValue);
        // remember_me.setChecked(rememberMe_checked);
    }
    public static void DestroyPrefs(Context context,EditText login, EditText password){
        SharedPreferences sharedPreferences = context.getSharedPreferences(SHARED_PREFS,context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        updateViews(login,password,LoadLogin_value(context),LoadPassword_value(context));
        Toast.makeText(context,"Preferences ares destroyed",Toast.LENGTH_LONG).show();
    }

}
