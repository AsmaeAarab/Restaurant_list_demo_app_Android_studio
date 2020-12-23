package com.example.tprestaurant.Activities;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.tprestaurant.Adapters.CategoryAdapter;
import com.example.tprestaurant.DB_Restaurant.RestaurantDbHelper;
import com.example.tprestaurant.Model.Category;
import com.example.tprestaurant.R;

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

        ArrayList<Category> categories=null;
        CategoryAdapter adapter = null;
        RestaurantDbHelper db = new RestaurantDbHelper(this);

        try {
            categories = db.getAllCategories();
             adapter = new CategoryAdapter(getApplicationContext(),R.layout.category_item,categories);
        }catch (Exception e){
            Log.i("exception","something wrong");
        }
        //CategoryDbHelper categoryDbHelper = new CategoryDbHelper(getApplicationContext());
        Categories_List.setAdapter(adapter);
        addNotification();
    }

    @OnItemClick(R.id.Categories_List)
    public void OnItemCtegoryClick(int position){
        //String.valueOf(RestaurantList.getTouchables().get(position))

        RestaurantDbHelper db = new RestaurantDbHelper(getApplicationContext());
        Category Clicked_category = (Category) Categories_List.getItemAtPosition(position);
        Clicked_category = db.getCategory(Clicked_category.getTitre());
        Toast.makeText(getApplicationContext(),"coucou item "+Clicked_category.getTitre() , Toast.LENGTH_LONG).show();
        Intent restaurant_list_intent = new Intent(AcceuilActivity.this, Restaurant_Liste_Activity.class);
        restaurant_list_intent.putExtra("Clicked_category", (Serializable) Clicked_category);
        startActivity(restaurant_list_intent);
        //this.finish();

    }

   /* if(Build.VERSION.SDK_INT >=Build.VERSION_CODES.O){
        CharSequence name = "yog";
        String description = "yog";
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel("yog",name,importance);
        channel.setDescription(description);
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }*/


    private void addNotification(){

        Intent intent = new Intent(this, AlertDialog.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,intent,0);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.android_platform)
                .setContentTitle("My NOTIF")
                .setContentText("coucou mora !! ")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat
                .from(this);
        notificationManager.notify(1,mBuilder.build());
    }
}