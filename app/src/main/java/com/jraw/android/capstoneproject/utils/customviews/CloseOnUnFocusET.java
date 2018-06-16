package com.jraw.android.capstoneproject.utils.customviews;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.jraw.android.capstoneproject.utils.Utils;

public class CloseOnUnFocusET extends AppCompatEditText implements View.OnFocusChangeListener {

    public CloseOnUnFocusET(Context context) {
        super(context);
        setOnFocusChangeListener(this);
    }

    public CloseOnUnFocusET(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnFocusChangeListener(this);
    }

    //This is an attempt to ensure the keyboard closes when edittext hasnt got focus.
    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        try {
            if (!hasFocus) {
                InputMethodManager keyboard = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                keyboard.hideSoftInputFromWindow(v.getWindowToken(), 0);
            }
        } catch (Exception e) {
            Utils.logDebug("Error in CloseET.onFocusChange: " + e.getMessage());
        }
    }
}
