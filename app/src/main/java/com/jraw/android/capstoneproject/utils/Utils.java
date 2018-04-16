package com.jraw.android.capstoneproject.utils;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.util.Log;

/**
 * Created by JonGaming on 16/04/2018.
 *
 */

public class Utils {
    private static final String LOG_TAG = "CapstoneProject";
    private static boolean LOG_DEBUG = true;
    public static void logDebug(String aLog) {if (LOG_DEBUG) {
        Log.i(LOG_TAG, aLog);}
    }
    public static void closeCursor(Cursor aCursor) {
        if (aCursor!=null&&!aCursor.isClosed()) {
            aCursor.close();
        }
    }
    public static int pixelsToDp(@NonNull Context aContext, float aDp) {
        //Calculates pixels to dp.
        DisplayMetrics metrics = aContext.getResources().getDisplayMetrics();
        float fpixels = metrics.density * aDp;
        return(int) (fpixels + 0.5f);
    }
}
