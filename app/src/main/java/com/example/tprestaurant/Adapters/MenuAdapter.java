package com.example.tprestaurant.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.tprestaurant.Model.MenuRestaurant;
import com.example.tprestaurant.R;

import java.util.ArrayList;

public class MenuAdapter extends ArrayAdapter<MenuRestaurant> {
    ArrayList <MenuRestaurant> menuRestaurantResource;

    public MenuAdapter(@NonNull Context context, int resource, ArrayList<MenuRestaurant> menuRestaurantResource) {
        super(context, resource, menuRestaurantResource);
        this.menuRestaurantResource = menuRestaurantResource;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.menu_item,parent,false);

        TextView Meal_name = (TextView) convertView.findViewById(R.id.Meal_name);
        Meal_name.setText(menuRestaurantResource.get(position).getMeal_Name());

        TextView Meal_prix = (TextView) convertView.findViewById(R.id.Meal_prix);
        Meal_prix.setText(menuRestaurantResource.get(position).getMeal_prix());

        return convertView;
    }
}
