package com.example.tprestaurant.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.os.Build;
import android.os.Bundle;
import android.widget.ListView;

import com.example.tprestaurant.Adapters.RestaurantAdapter;
import com.example.tprestaurant.DB_Restaurant.GlobalDbHelper;
import com.example.tprestaurant.DB_Restaurant.RestaurantTable;
import com.example.tprestaurant.Model.Category;
import com.example.tprestaurant.Model.Restaurant;
import com.example.tprestaurant.R;
import com.example.tprestaurant.fragments.Toolbar;

import java.io.Serializable;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

public class Restaurant_Liste_Activity extends AppCompatActivity implements LocationListener {
    static Location currentLocation;
@BindView(R.id.Restaurant_list)
    ListView Restaurant_list;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_liste);
        ButterKnife.bind(this);

        Category category =  (Category)(getIntent().getSerializableExtra("Clicked_category"));
        ArrayList<Restaurant> restaurants = null;
        GlobalDbHelper db = new GlobalDbHelper(getApplicationContext());
        restaurants= db.getRestaurantByCathegory(category.getId());

        for (Restaurant rst:restaurants) {
            Float dst = RestaurantTable.getDistance(getApplicationContext(),rst.getLatitude(),rst.getLongitude());
            rst.setDistance(Float.toString(dst/1000)+" Km");

            if(RestaurantTable.getTime())
                rst.setStatutRestaurant("ouvert");
            else
                rst.setStatutRestaurant("fermé");
        }
        RestaurantAdapter adapter = new RestaurantAdapter(getApplicationContext(),R.layout.restaurant_item,restaurants);
        Restaurant_list.setAdapter(adapter);
        Toolbar newFragmentDynamic = new Toolbar();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.menu_bar,newFragmentDynamic).commit();
    }
    @OnItemClick(R.id.Restaurant_list)
    public void OnRestaurantItemClick(int position){
        Intent restaurantDetailsMaps = new Intent(Restaurant_Liste_Activity.this, MapsActivity.class);
        GlobalDbHelper db = new GlobalDbHelper(getApplicationContext());
        Restaurant selectedRestaurant = (Restaurant) Restaurant_list.getItemAtPosition(position);
        restaurantDetailsMaps.putExtra("SelectedRestaurant",(Serializable)selectedRestaurant);
        startActivity(restaurantDetailsMaps);
    }
    @Override
    public void onLocationChanged(@NonNull Location location) {
        currentLocation.setLatitude(location.getLatitude());
        currentLocation.setLongitude(location.getLongitude());
    }
}