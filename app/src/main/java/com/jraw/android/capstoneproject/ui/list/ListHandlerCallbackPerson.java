package com.jraw.android.capstoneproject.ui.list;

import android.view.MotionEvent;
import android.view.View;

import com.jraw.android.capstoneproject.data.model.Person;

public interface ListHandlerCallbackPerson {
    void onListClick(int aPos, Person aPerson);
    void onListTouch(View aView, MotionEvent aMotionEvent);
}
