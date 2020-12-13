package com.example.tprestaurant;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

import butterknife.BindView;

public class CategoryAdapter extends ArrayAdapter <Category> {
    private ArrayList<Category> categories;

    public CategoryAdapter(@NonNull Context context, int resource,ArrayList<Category> categories) {
        super(context, resource, categories);
        this.categories=categories;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
       // return super.getView(position, convertView, parent);
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = inflater.inflate(R.layout.category_item,parent,false);

        ImageView imageCategory = (ImageView)convertView.findViewById(R.id.image_Category);
       // imageCategory.setBackgroundResource(categories.get(position).getIdImage());

        /* recuperer l'image */
             Resources resources = convertView.getContext().getResources();
            final int resourceId = resources.getIdentifier(String.valueOf(categories.get(position).getIdImage()), "drawable",
                    convertView.getContext().getPackageName());
             imageCategory.setImageResource(resourceId);

        TextView titleCategory = (TextView) convertView.findViewById(R.id.title_Category);
        titleCategory.setText(categories.get(position).getTitre());
        return convertView;
    }
}
