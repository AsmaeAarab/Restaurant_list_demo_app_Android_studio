package com.example.tprestaurant.Activities;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.GridView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.tprestaurant.Adapters.CategoryAdapter;
import com.example.tprestaurant.DB_Restaurant.RestaurantDbHelper;
import com.example.tprestaurant.DialogBox.DialogGpsMsg;
import com.example.tprestaurant.DialogBox.DialogWifiMsg;
import com.example.tprestaurant.Model.Category;
import com.example.tprestaurant.R;
import com.example.tprestaurant.SharedPrefs.Authentification_Shared_Preferences;
import com.example.tprestaurant.fragments.Menubar;

import java.io.Serializable;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
        RestaurantDbHelper db = new RestaurantDbHelper(this);

        try {
            categories = db.getAllCategories();
            adapter = new CategoryAdapter(getApplicationContext(), R.layout.category_item, categories);
        } catch (Exception e) {
            Log.i("exception", "something wrong");
        }
        //CategoryDbHelper categoryDbHelper = new CategoryDbHelper(getApplicationContext());
        Categories_List.setAdapter(adapter);
        Menubar newFragmentDynamic = new Menubar();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.menu_bar,newFragmentDynamic).commit();
    }

    @Override
    protected void onStart() {
        super.onStart();

        // VerifyInternet();
        //VerifyGPS();
        //if(isNetworkConnected()){
        //  VerifyGPS();
        //}
        //else{
        //  Toast.makeText(getApplicationContext(),"turn on you wifi please",Toast.LENGTH_LONG).show();
        //VerifyInternet();
        //}
    }

    public boolean VerifyGPS(){
        final LocationManager manager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );
        DialogGpsMsg msgGpsError= new DialogGpsMsg(getApplicationContext());
        if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            //Afficher erreur ou config GPS
            msgGpsError.show(getSupportFragmentManager(),"dialog GPS message");
            return false;
        }
        else
        {
            msgGpsError.addNotification();
            return true;
        }

    }

    public void VerifyInternet(){
        final WifiManager wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        DialogWifiMsg msgWifiError = new DialogWifiMsg();
        if (!wifiManager.isWifiEnabled()) {
            msgWifiError.show(getSupportFragmentManager(),"dialog wifi message");
        }
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }
    @Override
    public void onDestroy() {
        Log.i("message","on destroy");
        super.onDestroy();
    }

    @OnItemClick(R.id.Categories_List)
    public void OnItemCtegoryClick(int position){
         if(isNetworkConnected()){
              if(VerifyGPS()){
        RestaurantDbHelper db = new RestaurantDbHelper(getApplicationContext());
        Category Clicked_category = (Category) Categories_List.getItemAtPosition(position);
        Clicked_category = db.getCategory(Clicked_category.getTitre());
        //Toast.makeText(getApplicationContext(),"coucou item "+Clicked_category.getTitre() , Toast.LENGTH_LONG).show();
        Intent restaurant_list_intent = new Intent(AcceuilActivity.this, Restaurant_Liste_Activity.class);
        restaurant_list_intent.putExtra("Clicked_category", (Serializable) Clicked_category);
        //restaurant_list_intent.putExtra("currentLocation",(Serializable)currentLocation);
        startActivity(restaurant_list_intent);
        this.finish();
         }else {
         Toast.makeText(getApplicationContext(),"turn on your GPS please",Toast.LENGTH_LONG).show();
           }
        }
         else{
          Toast.makeText(getApplicationContext(),"turn on your wifi please",Toast.LENGTH_LONG).show();
        VerifyInternet();
    }

}
}
