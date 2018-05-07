package com.jraw.android.capstoneproject.ui.install;

public interface InstallContract {
    interface ViewInstall {
        void setPresenter(PresenterInstall aPresenter);
    }
    interface PresenterInstall {
        void onSave(String aFirstName, String aSurname, String aTelNum);
        void onInstalled();//Called by Install when successfully installed
    }
    interface ActivityInstall {
        void onInstalled();//Triggered when install successful and want to go to Conversation Fragment
    }
}