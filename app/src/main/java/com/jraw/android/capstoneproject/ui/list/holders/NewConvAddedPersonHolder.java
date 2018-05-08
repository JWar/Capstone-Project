package com.jraw.android.capstoneproject.ui.list.holders;

import android.view.View;
import android.widget.TextView;

import com.jraw.android.capstoneproject.data.model.Person;

public class NewConvAddedPersonHolder extends AbstractHolder {
    private View mView;
    private TextView mFnameTV;
    private TextView mSnameTV;
    private Person mPerson;

    public NewConvAddedPersonHolder(View view) {
        super(view);
        mView = view;
        mFnameTV = view.findViewById(R.id.list_item_new_conv_added_fname);
        mSnameTV = view.findViewById(R.id.list_item_new_conv_added_sname);
    }
    public Person bindData(Person aPerson, int aPos) {
        return setViews(aPerson,aPos);
    }
    private Person setViews(Person aPerson, int aPos) {
        mPerson = aPerson;
        mFnameTV.setText(mPerson.getPEFname());
        mSnameTV.setText(mPerson.getPESname());
        return mPerson;
    }

    @Override
    public void setListener(View.OnClickListener aListener) {
        mView.setOnClickListener(aListener);
    }
    @Override
    public String toString() {
        return super.toString() + " '" + mFnameTV.getText()
                + " " + mSnameTV.getText()
                + " '";
    }
}
