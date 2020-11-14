package dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatDialogFragment;

public class RequestMessage extends AppCompatDialogFragment {
    private DialogMessageListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Confirm Job")
                .setMessage("Do you want to accept this request?")
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
            listener = (RequestMessage.DialogMessageListener)context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()+"Must implement DialogMessageListener");
        }
    }
}