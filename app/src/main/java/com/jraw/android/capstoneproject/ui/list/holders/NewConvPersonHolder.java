package com.jraw.android.capstoneproject.ui.list.holders;

import android.view.View;
import android.widget.TextView;

import com.jraw.android.capstoneproject.R;
import com.jraw.android.capstoneproject.data.model.Person;

public class NewConvPersonHolder extends AbstractHolder {
    private View mView;
    private TextView mFullNameTV;
    private Person mPerson;

    public NewConvPersonHolder(View view) {
        super(view);
        mView = view;
        mFullNameTV = view.findViewById(R.id.list_item_new_conv_added_fullname);

    }
    public Person bindData(Person aPerson, int aPos) {
        return setViews(aPerson,aPos);
    }
    private Person setViews(Person aPerson, int aPos) {
        mPerson = aPerson;
        mFullNameTV.setText(mPerson.getFullName());
        return mPerson;
    }

    @Override
    public void setListener(View.OnClickListener aListener) {
        mView.setOnClickListener(aListener);
    }
    @Override
    public String toString() {
        return super.toString() + " '" + mFullNameTV.getText()
                + " '";
    }
}
