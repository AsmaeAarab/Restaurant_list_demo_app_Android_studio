package com.example.tprestaurant;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Login_page extends AppCompatActivity {
    @BindView(R.id.login)
    EditText login;

    @BindView(R.id.password)
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        ButterKnife.bind(this);
        RestaurantDbHelper db = new RestaurantDbHelper(this);
        db.createDfaultAuthentification();
    }
    @OnClick(R.id.submit)
    public void onSubmit(){
        RestaurantDbHelper db=new RestaurantDbHelper(this);
        Authentification auth=db.getAuth(login.getText().toString(),password.getText().toString());
    if(auth!=null){
        Toast.makeText(getApplicationContext(),"authentification reussie",Toast.LENGTH_LONG).show();
    }
    else
        Toast.makeText(getApplicationContext(),"failed authentification",Toast.LENGTH_LONG).show();
    }

}