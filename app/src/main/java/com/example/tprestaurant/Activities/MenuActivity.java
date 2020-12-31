package com.example.tprestaurant.Activities;

import androidx.appcompat.app.AppCompatActivity;


import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tprestaurant.Adapters.MenuAdapter;
import com.example.tprestaurant.Model.MenuRestaurant;
import com.example.tprestaurant.Model.Restaurant;
import com.example.tprestaurant.R;
import com.example.tprestaurant.fragments.Menubar;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

public class MenuActivity extends AppCompatActivity {
    MenuAdapter adapter = null;
    ArrayList<MenuRestaurant> menuRestaurantOfPizaa = new ArrayList<MenuRestaurant>();
    ArrayList<MenuRestaurant> menuRestaurantOfFastFood = new ArrayList<MenuRestaurant>();
    ArrayList<MenuRestaurant> menuRestaurantOfTacos = new ArrayList<MenuRestaurant>();
    ArrayList<MenuRestaurant> menuRestaurantOfMorocain = new ArrayList<MenuRestaurant>();
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

        menuRestaurantOfPizaa.add(new MenuRestaurant("Margherita pizza","42 dh",1));
        menuRestaurantOfPizaa.add(new MenuRestaurant("BEEFY pizza","49 dh",1));
        menuRestaurantOfPizaa.add(new MenuRestaurant("Poulet Sauce BBQ pizza","49 dh",1));

        menuRestaurantOfFastFood.add(new MenuRestaurant("Zinger Classic Meal","47 dh",2));
        menuRestaurantOfFastFood.add(new MenuRestaurant("Zinger Supreme","42 dh",2));
        menuRestaurantOfFastFood.add(new MenuRestaurant("Light Crispy Strips","35 dh",2));

        menuRestaurantOfTacos.add(new MenuRestaurant("Tacos nuggets","42 dh",3));
        menuRestaurantOfTacos.add(new MenuRestaurant("Tacos poulet","49 dh",3));
        menuRestaurantOfTacos.add(new MenuRestaurant("Tacos swiss","49 dh",3));

        menuRestaurantOfMorocain.add(new MenuRestaurant("Couscous","42 dh",4));
        menuRestaurantOfMorocain.add(new MenuRestaurant("brauchette viande","49 dh",4));
        menuRestaurantOfMorocain.add(new MenuRestaurant("soupe marocaine (hrira)","49 dh",4));

        if(restaurant.getIdCategory()==1){
            adapter = new MenuAdapter(getApplicationContext(),R.layout.menu_item, menuRestaurantOfPizaa);
        }
        if(restaurant.getIdCategory()==2){
            adapter = new MenuAdapter(getApplicationContext(),R.layout.menu_item, menuRestaurantOfFastFood);
        }
        if(restaurant.getIdCategory()==3){
            adapter = new MenuAdapter(getApplicationContext(),R.layout.menu_item, menuRestaurantOfTacos);
        }
        if(restaurant.getIdCategory()==4){
            adapter = new MenuAdapter(getApplicationContext(),R.layout.menu_item, menuRestaurantOfMorocain);
        }

    // menu.setText(MenuOfMorocain.get(0).getMeal_Name());
       // adapter = new MenuAdapter(getApplicationContext(),R.layout.menu_item,MenuOfMorocain);
        list_menu.setAdapter(adapter);

        Menubar newFragmentDynamic = new Menubar();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.menu_bar,newFragmentDynamic).commit();
    }
    @OnItemClick(R.id.list_menu)
    public void clickbtn(int position){
        Toast.makeText(getApplicationContext(),"position: "+position, Toast.LENGTH_LONG).show();
    }


}