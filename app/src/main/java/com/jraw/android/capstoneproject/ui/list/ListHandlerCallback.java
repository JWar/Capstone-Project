package com.jraw.android.capstoneproject.ui.list;

import android.view.MotionEvent;
import android.view.View;

public interface ListHandlerCallback {
    void onListClick(int aPosition, String aId);
    void onListTouch(View aView, MotionEvent aMotionEvent);
}