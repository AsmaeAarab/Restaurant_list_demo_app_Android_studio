package com.example.tprestaurant.DialogBox;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.example.tprestaurant.Activities.AcceuilActivity;
import com.example.tprestaurant.Activities.LoginActivity;
import com.example.tprestaurant.R;

public class DialogGpsMsg extends AppCompatDialogFragment {
    Context context;

    public  DialogGpsMsg(Context context){
        this.context=context;
    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("GPS disabled")
                .setMessage("Vous devez activer votre gps pour continuer")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //dismiss dialog
                        Intent i = new Intent( android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS) ;
                        startActivityForResult(i, 1);
                    }
                });
        return builder.create();
    }

/*    public void addNotification(){

        Intent intent = new Intent(context, androidx.appcompat.app.AlertDialog.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,0,intent,0);

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.google_maps_48)
                .setContentTitle("GPS INFO")
                .setContentText("Votre GPS est Activé ")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        NotificationManagerCompat notificationManager = NotificationManagerCompat
                .from(context);
        notificationManager.notify(1,mBuilder.build());
    }*/
}
