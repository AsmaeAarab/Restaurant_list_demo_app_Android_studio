package com.example.tprestaurant.DialogBox;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class DialogReductionMsg extends AppCompatDialogFragment {
    private int reduction;

    public DialogReductionMsg(int reduction) {
        //super();
        this.reduction = reduction;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Reduction")
                .setMessage("vous benificiez d'une reduction de "+reduction+" dh")
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //dismiss dialog
                    }
                });
        return builder.create();
    }
}
