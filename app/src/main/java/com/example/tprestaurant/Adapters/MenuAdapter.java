package com.example.tprestaurant.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.tprestaurant.Model.Menu;
import com.example.tprestaurant.Model.Restaurant;
import com.example.tprestaurant.R;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class MenuAdapter extends ArrayAdapter<Menu> {
    ArrayList <Menu> menuResource;

    public MenuAdapter(@NonNull Context context, int resource, ArrayList<Menu> menuResource) {
        super(context, resource, menuResource);
        this.menuResource = menuResource;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.menu_item,parent,false);

        TextView Meal_name = (TextView) convertView.findViewById(R.id.Meal_name);
        Meal_name.setText(menuResource.get(position).getMeal_Name());

        TextView Meal_prix = (TextView) convertView.findViewById(R.id.Meal_prix);
        Meal_prix.setText(menuResource.get(position).getMeal_prix());

        return convertView;
    }
}
