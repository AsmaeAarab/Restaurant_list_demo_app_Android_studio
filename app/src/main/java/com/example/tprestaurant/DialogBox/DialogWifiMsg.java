package com.example.tprestaurant.DialogBox;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatDialogFragment;

public class DialogWifiMsg extends AppCompatDialogFragment {

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("WIFI disabled")
                .setMessage("Vous devez activer votre wifi pour continuer la navigation")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //dismiss dialog
                        Intent i = new Intent( Settings.ACTION_WIFI_SETTINGS) ;//ACTION_LOCATION_SOURCE_SETTINGS
                        startActivityForResult(i, 1);
                    }
                });
        return builder.create();

    }
}
