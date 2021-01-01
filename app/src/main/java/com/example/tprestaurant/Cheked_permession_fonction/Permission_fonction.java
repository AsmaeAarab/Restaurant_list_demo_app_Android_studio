package com.example.tprestaurant.Cheked_permession_fonction;

import android.content.Context;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;

import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.example.tprestaurant.DialogBox.DialogGpsMsg;
import com.example.tprestaurant.DialogBox.DialogWifiMsg;

public class Permission_fonction {

    public static void VerifyInternet(Context context){
        final WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
        DialogWifiMsg msgWifiError = new DialogWifiMsg();
        if (!wifiManager.isWifiEnabled()) {
            msgWifiError.show(((FragmentActivity)context).getSupportFragmentManager(),"dialog wifi message");
        }
    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    public static boolean VerifyGPS(Context context){
        final LocationManager manager = (LocationManager) context.getSystemService( Context.LOCATION_SERVICE );
        DialogGpsMsg msgGpsError= new DialogGpsMsg(context);
        if ( !manager.isProviderEnabled( LocationManager.GPS_PROVIDER ) ) {
            //Afficher erreur ou config GPS
            msgGpsError.show(((FragmentActivity)context).getSupportFragmentManager(),"dialog GPS message");
            return false;
        }
        else
        {
          //  msgGpsError.addNotification();
            return true;
        }

    }
}
