package com.example.tprestaurant;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import butterknife.OnTouch;

public class Acceuil extends AppCompatActivity {
@BindView(R.id.RestaurantList)
GridView RestaurantList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceuil);
        ButterKnife.bind(this);

        ArrayList<Category> categories=null;
        CategoryAdapter adapter = null;
        RestaurantDbHelper db = new RestaurantDbHelper(this);

        try {
            categories = db.getAllCategories();
             adapter = new CategoryAdapter(getApplicationContext(),R.layout.category_item,categories);
        }catch (Exception e){
            Log.i("exception","something wrong");
        }
        //CategoryDbHelper categoryDbHelper = new CategoryDbHelper(getApplicationContext());
        RestaurantList.setAdapter(adapter);
    }

    @OnItemClick(R.id.RestaurantList)
    public void OnItemCtegoryClick(int position){
        //String.valueOf(RestaurantList.getTouchables().get(position))
        Category Clicked_category = (Category) RestaurantList.getItemAtPosition(position);
        Toast.makeText(getApplicationContext(),"coucou item "+Clicked_category.getTitre() , Toast.LENGTH_LONG).show();
        Intent restaurant_list = new Intent(Acceuil.this,Restaurant_Liste.class);
        startActivity(restaurant_list);
        this.finish();
    }
}