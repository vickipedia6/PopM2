package com.popularmovies2.vignesh.popm2;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;


public class AlertDialogFragment extends DialogFragment {
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Context context = getActivity();
        AlertDialog.Builder builder = new AlertDialog.Builder(context)
                .setTitle(R.string.error_title)
                .setMessage(R.string.error_dialog_message)
                .setPositiveButton(R.string.error_positive_button_text, null);

        AlertDialog dialog = builder.create();
        return dialog;
    }
}
