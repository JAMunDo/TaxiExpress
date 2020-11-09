package com.example.taxiexpress;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class DialogMessage extends AppCompatDialogFragment {
    private DialogMessageListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Request A Taxi")
                .setMessage("Do you want to book this taxi?")
                .setNegativeButton("No", (dialog, which) -> {

                })
                .setPositiveButton("Yes", (dialog, which) -> {
                    listener.onYesClicked();
                });
        return builder.create();
    }

    public interface  DialogMessageListener{
        void onYesClicked();
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        try{
            listener = (DialogMessageListener)context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()+"Must implement DialogMessageListener");
        }
    }
}
