package com.example.tprestaurant.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.tprestaurant.Model.MenuOfRestaurant;
import com.example.tprestaurant.R;

import java.util.ArrayList;

public class MenuOfRestaurantAdapter extends ArrayAdapter<MenuOfRestaurant> {
    ArrayList <MenuOfRestaurant> menuOfRestaurantResource;

    public MenuOfRestaurantAdapter(@NonNull Context context, int resource, ArrayList<MenuOfRestaurant> menuOfRestaurantResource) {
        super(context, resource, menuOfRestaurantResource);
        this.menuOfRestaurantResource = menuOfRestaurantResource;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.menu_item,parent,false);

        TextView Meal_name = (TextView) convertView.findViewById(R.id.Meal_name);
        Meal_name.setText(menuOfRestaurantResource.get(position).getMeal_Name());

        TextView Meal_prix = (TextView) convertView.findViewById(R.id.Meal_prix);
        Meal_prix.setText(menuOfRestaurantResource.get(position).getMeal_prix());

        return convertView;
    }
}
