package com.jraw.android.capstoneproject.ui.list.holders;

import android.view.View;
import android.widget.TextView;

import com.jraw.android.capstoneproject.R;
import com.jraw.android.capstoneproject.data.model.Conversation;

public class ConvHolder extends AbstractHolder {
    private View mView;
    private TextView mTitleTV;

    public ConvHolder(View view) {
        super(view);
        mView = view;
        mTitleTV = view.findViewById(R.id.list_item_convs_title);
    }
    public String bindData(Conversation aConversation, int aPos) {
        return setViews(aConversation,aPos);
    }
    private String setViews(Conversation aConv, int aPos) {
        mTitleTV.setText(aConv.getCOTitle());
        return aConv.getId()+"";
    }

    @Override
    public void setListener(View.OnClickListener aListener) {
        mView.setOnClickListener(aListener);
    }
    @Override
    public String toString() {
        return super.toString() + " '" + mTitleTV.getText() + " '";
    }
}