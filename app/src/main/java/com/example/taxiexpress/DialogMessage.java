package com.example.taxiexpress;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class DialogMessage extends AppCompatDialogFragment {


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Pop up")
                .setMessage("Welcome this is a test to see if the function works")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        return builder.create();
    }
    @NonNull
    public  Dialog onCreateTaxiRequest(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Request A Taxi")
                .setMessage("Do you want to request a taxi?")
                .setNegativeButton("No", (dialog, which) -> {

                })
                .setPositiveButton("Yes", (dialog, which) -> {

                });
        return builder.create();
    }
}
