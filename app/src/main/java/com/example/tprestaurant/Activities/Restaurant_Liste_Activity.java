package com.example.tprestaurant.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import com.example.tprestaurant.Adapters.RestaurantAdapter;
import com.example.tprestaurant.DB_Restaurant.RestaurantDbHelper;
import com.example.tprestaurant.Model.Category;
import com.example.tprestaurant.Model.Restaurant;
import com.example.tprestaurant.R;

import java.io.Serializable;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

public class Restaurant_Liste_Activity extends AppCompatActivity {
@BindView(R.id.Restaurant_list)
    ListView Restaurant_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_liste);
        ButterKnife.bind(this);

        Category category =  (Category)(getIntent().getSerializableExtra("Clicked_category"));
        ArrayList<Restaurant> restaurants = null;
       // restaurants.add(new Restaurant("rest01","open","500",1));
      //  restaurants.add(new Restaurant("rest02","closed","500",1));
        RestaurantDbHelper db = new RestaurantDbHelper(getApplicationContext());
        restaurants= db.getRestaurantByCathegory(category.getId());
        RestaurantAdapter adapter = new RestaurantAdapter(getApplicationContext(),R.layout.restaurant_item,restaurants);
        Restaurant_list.setAdapter(adapter);
        //message.setText(category.getId().toString());
    }
    @OnItemClick(R.id.Restaurant_list)
    public void OnRestaurantItemClick(int position){
        Toast.makeText(getApplicationContext(),"coucou from item "+position,Toast.LENGTH_LONG).show();
        Intent restaurantDetailsMaps = new Intent(Restaurant_Liste_Activity.this, MapsActivity.class);
        Restaurant selectedRestaurant = (Restaurant) Restaurant_list.getItemAtPosition(position);
        restaurantDetailsMaps.putExtra("SelectedRestaurant",(Serializable)selectedRestaurant);
        startActivity(restaurantDetailsMaps);
    }
}