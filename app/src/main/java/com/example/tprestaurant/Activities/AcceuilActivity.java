package com.example.tprestaurant.Activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.tprestaurant.Adapters.CategoryAdapter;
import com.example.tprestaurant.Cheked_permession_fonction.Permission_fonction;
import com.example.tprestaurant.DB_Restaurant.GlobalDbHelper;
import com.example.tprestaurant.Model.Category;
import com.example.tprestaurant.R;
import com.example.tprestaurant.fragments.Toolbar;

import java.io.Serializable;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnItemClick;

@RequiresApi(api = Build.VERSION_CODES.O)
public class AcceuilActivity extends AppCompatActivity {
    @BindView(R.id.Categories_List)
    GridView Categories_List;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceuil);
        ButterKnife.bind(this);

        ArrayList<Category> categories = null;
        CategoryAdapter adapter = null;
        GlobalDbHelper db = new GlobalDbHelper(this);

        try {
            categories = db.getAllCategories();
            adapter = new CategoryAdapter(getApplicationContext(), R.layout.category_item, categories);
        } catch (Exception e) {
            Log.i("exception", "something wrong");
        }
        Categories_List.setAdapter(adapter);

        // toolbar fragment
        Toolbar newFragmentDynamic = new Toolbar();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.menu_bar,newFragmentDynamic).commit();
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onDestroy() {
        Log.i("message","on destroy");
        super.onDestroy();
    }

    @OnItemClick(R.id.Categories_List)
    public void OnItemCtegoryClick(int position){
         if(Permission_fonction.isNetworkConnected(this)){
              if(Permission_fonction.VerifyGPS(this)){
        GlobalDbHelper db = new GlobalDbHelper(getApplicationContext());
        Category Clicked_category = (Category) Categories_List.getItemAtPosition(position);
        Clicked_category = db.getCategory(Clicked_category.getTitre());
        Intent restaurant_list_intent = new Intent(AcceuilActivity.this, Restaurant_Liste_Activity.class);
        restaurant_list_intent.putExtra("Clicked_category", (Serializable) Clicked_category);
        startActivity(restaurant_list_intent);
         }else {
         Toast.makeText(getApplicationContext(),"turn on your GPS please",Toast.LENGTH_LONG).show();
           }
        }
         else{
          Toast.makeText(getApplicationContext(),"turn on your wifi please",Toast.LENGTH_LONG).show();
             Permission_fonction.VerifyInternet(getApplicationContext());
    }
}

}
