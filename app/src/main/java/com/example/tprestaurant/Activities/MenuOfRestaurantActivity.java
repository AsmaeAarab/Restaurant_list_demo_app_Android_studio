package com.example.tprestaurant.Activities;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tprestaurant.Adapters.MenuOfRestaurantAdapter;
import com.example.tprestaurant.DB_Restaurant.GlobalDbHelper;
import com.example.tprestaurant.Model.MenuOfRestaurant;
import com.example.tprestaurant.Model.Restaurant;
import com.example.tprestaurant.R;
import com.example.tprestaurant.fragments.Toolbar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

public class MenuOfRestaurantActivity extends AppCompatActivity {
    MenuOfRestaurantAdapter adapter = null;

    @BindView(R.id.list_menu)
        ListView list_menu;
    @BindView(R.id.textviewMenu)
        TextView textviewMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ButterKnife.bind(this);

        Restaurant restaurant = (Restaurant) getIntent().getSerializableExtra("Restaurant");
        textviewMenu.setText("Welcome to "+restaurant.getNomRestaurant());
        Toast.makeText(getApplicationContext(),"from restaurant id : "+restaurant.getIdRestaurant(),Toast.LENGTH_LONG).show();
        ArrayList<MenuOfRestaurant> menuOfRestaurant = new ArrayList<MenuOfRestaurant>();
        GlobalDbHelper db =new GlobalDbHelper(getApplicationContext());
        menuOfRestaurant = db.getMenutemsByRestaurant(restaurant.getIdRestaurant());
        if (menuOfRestaurant != null)
        {
            adapter = new MenuOfRestaurantAdapter(getApplicationContext(),R.layout.menu_item, menuOfRestaurant);
            list_menu.setAdapter(adapter);
        }

        Toolbar newFragmentDynamic = new Toolbar();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.menu_bar,newFragmentDynamic).commit();
    }
    @OnItemClick(R.id.list_menu)
    public void clickbtn(int position){
        Toast.makeText(getApplicationContext(),"position: "+position, Toast.LENGTH_LONG).show();
    }

}