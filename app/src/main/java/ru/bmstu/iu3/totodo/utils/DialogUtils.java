package ru.bmstu.iu3.totodo.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import ru.bmstu.iu3.totodo.R;

/**
 * Created by Icetroid on 17.11.2017.
 */

public class DialogUtils {
    public static Dialog makeChoosePriorityDialog(Context context, DialogInterface.OnClickListener listener){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(R.string.choose_priority_dialog_title).setItems(R.array.priorities, listener);
        AlertDialog dialog = builder.create();
        return dialog;
    }
}
