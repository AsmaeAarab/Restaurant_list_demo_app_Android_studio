package com.example.tprestaurant.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.tprestaurant.DB_Restaurant.GlobalDbHelper;
import com.example.tprestaurant.Model.Authentification;
import com.example.tprestaurant.R;
import com.example.tprestaurant.SharedPrefs.Authentification_Shared_Preferences;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.login)
    EditText login;

    @BindView(R.id.password)
    EditText password;

    @BindView(R.id.remember_me)
    CheckBox remember_me;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(!Authentification_Shared_Preferences.TesterUserSharedPrefPourRedirection(getApplicationContext())) {
                setContentView(R.layout.login_page);
                ButterKnife.bind(this);
                updateViews();
            }
        }


    @Override
    public void onResume() {
        if(Authentification_Shared_Preferences.TesterUserSharedPrefPourRedirection(getApplicationContext())){
            Intent acceuil = new Intent(LoginActivity.this,AcceuilActivity.class);
            startActivity(acceuil);
            finish();
        }
        super.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onDestroy() {
        Log.i("message","on destroy");
        super.onDestroy();
    }

    @OnClick(R.id.submit)
    public void onSubmit(){
        GlobalDbHelper db = new GlobalDbHelper(this);
        Authentification auth = db.getAuth(login.getText().toString(), password.getText().toString());
        if(auth != null){
            Toast.makeText(getApplicationContext(),"authentification réussie",Toast.LENGTH_LONG).show();
            SaveLogin();//save at  shared pref
            Intent acceuil = new Intent(LoginActivity.this,AcceuilActivity.class);
            startActivity(acceuil);
            finish();
        }
        else
            Toast.makeText(getApplicationContext(),"failed authentification",Toast.LENGTH_LONG).show();
    }

    public void SaveLogin(){
        Authentification_Shared_Preferences.SaveAuthentification(this,remember_me,login,password);
    }

    public void updateViews(){
        String rememberMe_login = Authentification_Shared_Preferences.LoadLogin_value(this);
        String rememberMe_password = Authentification_Shared_Preferences.LoadPassword_value(this);
        login.setText(rememberMe_login);
        password.setText(rememberMe_password);
        // remember_me.setChecked(rememberMe_checked);
    }

    public void DestroyPrefs(){
        Authentification_Shared_Preferences.DestroyPrefs(this);
    }


}