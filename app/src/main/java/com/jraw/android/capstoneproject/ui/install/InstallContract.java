package com.jraw.android.capstoneproject.ui.install;

public interface InstallContract {
    interface ViewInstall {
        void setPresenter(PresenterInstall aPresenter);
    }
    interface PresenterInstall {
        void onSave(String aFirstName, String aSurname, String aTelNum);
    }
}