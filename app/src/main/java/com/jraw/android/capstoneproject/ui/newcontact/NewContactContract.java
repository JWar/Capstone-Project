package com.jraw.android.capstoneproject.ui.newcontact;

import android.content.Context;

public interface NewContactContract {

    interface ViewNewContact {
        void saveSuccessful();
        void problemSaving();
        void setPresenter(PresenterNewContact aPresenter);
    }

    interface PresenterNewContact {
        void onSave(Context aContext, String aFirstname, String aSurname, String aTelNum);
    }
}
