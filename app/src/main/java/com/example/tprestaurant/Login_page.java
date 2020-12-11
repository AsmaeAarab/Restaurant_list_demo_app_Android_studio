package com.example.tprestaurant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;

public class Login_page extends AppCompatActivity {
    public static final String SHARED_PREFS = "sharedPrefs";
    public String rememberMe_login;
    public String rememberMe_password;
    public boolean rememberMe_checked;
    @BindView(R.id.login)
    EditText login;

    @BindView(R.id.password)
    EditText password;

    @BindView(R.id.remember_me)
    CheckBox remember_me;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        ButterKnife.bind(this);
        LoadData();
        updateViews();
    }

    @OnClick(R.id.submit)
    public void onSubmit(){
        RestaurantDbHelper db=new RestaurantDbHelper(this);
        Authentification auth=db.getAuth(login.getText().toString(),password.getText().toString());
        if(auth != null){
            Toast.makeText(getApplicationContext(),"authentification reussie",Toast.LENGTH_LONG).show();
            SaveLogin();
        }
        else
            Toast.makeText(getApplicationContext(),"failed authentification",Toast.LENGTH_LONG).show();
    }
   /* @OnCheckedChanged(R.id.remember_me)
    public void onRememberMeCheckedChange(boolean checked)
    {
        SaveLogin();
    }*/
    public void SaveLogin(){
        if(remember_me.isChecked()){
            SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("login",login.getText().toString());
            editor.putString("password",password.getText().toString());
            editor.putBoolean("remember_me", remember_me.isChecked());//true
            editor.apply();
            Toast.makeText(this,"votre login et password sont sauvegardé",Toast.LENGTH_LONG).show();
        }
    }
    public void LoadData(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        rememberMe_login = sharedPreferences.getString("login","");
        rememberMe_password = sharedPreferences.getString("password","");
        rememberMe_checked = sharedPreferences.getBoolean("remember_me",false);
    }
    public void updateViews(){
        login.setText(rememberMe_login);
        password.setText(rememberMe_password);
       // remember_me.setChecked(rememberMe_checked);
    }

    @Override
    protected void onDestroy() {
        Log.i("message","on destroy");
        super.onDestroy();
    }
    public void DestroyPrefs(){
        SharedPreferences sharedPreferences = this.getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
        LoadData();
        updateViews();
        Toast.makeText(this,"Preferences ares destroyed",Toast.LENGTH_LONG).show();
    }
}