package ru.bmstu.iu3.totodo.utils;

import android.content.Context;
import android.util.Log;

import ru.bmstu.iu3.totodo.R;

/**
 * Created by Icetroid on 03.12.2017.
 */

public class ArrayUtils
{
    private static final String TAG = "ArrayUtils";
    public static int getMainVpBackgroundColor(int position, Context context)
    {
        int[] colors = context.getResources().getIntArray(R.array.main_vp_background_color);
        Log.i(TAG, "colors " + colors[0]);
        return colors[position];
    }
}
