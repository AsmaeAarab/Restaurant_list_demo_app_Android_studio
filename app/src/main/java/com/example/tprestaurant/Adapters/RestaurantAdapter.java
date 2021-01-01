package com.example.tprestaurant.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.tprestaurant.Model.Restaurant;
import com.example.tprestaurant.R;

import java.util.ArrayList;

public class RestaurantAdapter extends ArrayAdapter<Restaurant> {
    private  ArrayList<Restaurant> Restaurants ;

    public RestaurantAdapter(@NonNull Context context, int resource, ArrayList<Restaurant> restaurants) {
        super(context, resource, restaurants);
        Restaurants = restaurants;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.restaurant_item,parent,false);

        TextView nomRestaurant = (TextView) convertView.findViewById(R.id.nomRestaurant);
        nomRestaurant.setText(Restaurants.get(position).getNomRestaurant());

        TextView statutRestaurant = (TextView) convertView.findViewById(R.id.statutRestaurant);
        statutRestaurant.setText(Restaurants.get(position).getStatutRestaurant());

        TextView distance = (TextView) convertView.findViewById(R.id.distance);
        distance.setText(Restaurants.get(position).getDistance());

        return convertView;
    }

}
