package com.example.tprestaurant.Activities;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tprestaurant.Adapters.CategoryAdapter;
import com.example.tprestaurant.Adapters.MenuAdapter;
import com.example.tprestaurant.Model.Menu;
import com.example.tprestaurant.Model.Restaurant;
import com.example.tprestaurant.R;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

public class MenuActivity extends AppCompatActivity {
    MenuAdapter adapter = null;
    ArrayList<Menu> MenuOfPizaa = new ArrayList<Menu>();
    ArrayList<Menu> MenuOfFastFood = new ArrayList<Menu>();
    ArrayList<Menu> MenuOfTacos = new ArrayList<Menu>();
    ArrayList<Menu> MenuOfMorocain = new ArrayList<Menu>();
//    @BindView(R.id.menu)
//        TextView menu;
    @BindView(R.id.list_menu)
        ListView list_menu;
    @BindView(R.id.textviewMenu)
        TextView textviewMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        ButterKnife.bind(this);

     //   HashMap<String, String> menuSelected= (HashMap) getIntent().getSerializableExtra("menu");
      //  menu.setText(menuSelected.get("Margherita pizza"));
        Restaurant restaurant = (Restaurant) getIntent().getSerializableExtra("Restaurant");
        textviewMenu.setText("Welcome to "+restaurant.getNomRestaurant());

        MenuOfPizaa.add(new Menu("Margherita pizza","42 dh",1));
        MenuOfPizaa.add(new Menu("BEEFY pizza","49 dh",1));
        MenuOfPizaa.add(new Menu("Poulet Sauce BBQ pizza","49 dh",1));

        MenuOfFastFood.add(new Menu("Zinger Classic Meal","47 dh",2));
        MenuOfFastFood.add(new Menu("Zinger Supreme","42 dh",2));
        MenuOfFastFood.add(new Menu("Light Crispy Strips","35 dh",2));

        MenuOfTacos.add(new Menu("Tacos nuggets","42 dh",3));
        MenuOfTacos.add(new Menu("Tacos poulet","49 dh",3));
        MenuOfTacos.add(new Menu("Tacos swiss","49 dh",3));

        MenuOfMorocain.add(new Menu("Couscous","42 dh",4));
        MenuOfMorocain.add(new Menu("brauchette viande","49 dh",4));
        MenuOfMorocain.add(new Menu("soupe marocaine (hrira)","49 dh",4));

        if(restaurant.getIdCategory()==1){
            adapter = new MenuAdapter(getApplicationContext(),R.layout.menu_item,MenuOfPizaa);
        }
        if(restaurant.getIdCategory()==2){
            adapter = new MenuAdapter(getApplicationContext(),R.layout.menu_item,MenuOfFastFood);
        }
        if(restaurant.getIdCategory()==3){
            adapter = new MenuAdapter(getApplicationContext(),R.layout.menu_item,MenuOfTacos);
        }
        if(restaurant.getIdCategory()==4){
            adapter = new MenuAdapter(getApplicationContext(),R.layout.menu_item,MenuOfMorocain);
        }

    // menu.setText(MenuOfMorocain.get(0).getMeal_Name());
       // adapter = new MenuAdapter(getApplicationContext(),R.layout.menu_item,MenuOfMorocain);
        list_menu.setAdapter(adapter);
    }
    @OnItemClick(R.id.list_menu)
    public void clickbtn(int position){
        Toast.makeText(getApplicationContext(),"position: "+position, Toast.LENGTH_LONG).show();
    }


}