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

    public static final String SHAR_PREFS = "capstonePrefs";

    //This is for dummy data...
    public static final int USER_A=1;
    public static final int USER_B=2;
    public static final int USER_C=3;
    public static final int USER_D=4;
    public static final int USER_E=5;
    public static final int USER_F=6;

    //Dummy value for user id. Usually acquired from srver and stored in sharPref
    public static final int THIS_USER_ID=1;
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
    //Prints all columns and values in a cursor.
    public static void dumpContent(Cursor aCur) {
        if (aCur != null) {
            Utils.logDebug("Size: " + aCur.getCount());
            while (aCur.moveToNext()) {
                String msgInfo = "";
                for (int i = 0; i < aCur.getColumnCount(); i++) {
                    msgInfo += " " + aCur.getColumnName(i) + ":" + aCur.getString(i);
                }
                Utils.logDebug(msgInfo);//
            }
        }
    }
}
