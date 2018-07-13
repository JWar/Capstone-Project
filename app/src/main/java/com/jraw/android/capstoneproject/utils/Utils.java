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

    public static final String URL = "http://51.7.163.33:10000/JonsAppServer/";//Dummy url

    public static final String SHAR_PREFS = "capstonePrefs";

    public static final String CHANNEL_ID = "capChanId";

    //This is for dummy data...
    public static final String USER_A="1234";
    public static final String USER_B="2345";
    public static final String USER_C="3456";
    public static final String USER_D="4567";

    //Dummy value for user id. Usually acquired from srver and stored in sharPref
    public static final int THIS_USER_ID=1;
    public static final String THIS_USER_TEL="01234567890";
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
    public static String makePlaceholders(int len) {
        if (len < 1) {
            // It will lead to an invalid query anyway ..
            throw new RuntimeException("No placeholders");
        } else {
            StringBuilder sb = new StringBuilder(len * 2 - 1);
            sb.append("?");
            for (int i = 1; i < len; i++) {
                sb.append(",?");
            }
            return sb.toString();
        }
    }
}
