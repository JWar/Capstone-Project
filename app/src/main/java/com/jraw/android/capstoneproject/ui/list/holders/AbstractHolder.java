package com.jraw.android.capstoneproject.ui.list.holders;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class AbstractHolder extends RecyclerView.ViewHolder {
    public AbstractHolder(View itemView) {
        super(itemView);
    }
    public abstract void setListener(View.OnClickListener aListener);
}