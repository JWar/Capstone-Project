package com.jraw.android.capstoneproject.ui.install;

import android.support.annotation.NonNull;

public class InstallPresenter implements InstallContract.PresenterInstall {
    private InstallContract.ViewInstall mViewInstall;
    private InstallContract.ActivityInstall mActivityInstall;
    public InstallPresenter(@NonNull InstallContract.ViewInstall aViewInstall,
                            @NonNull InstallContract.ActivityInstall aActivityInstall) {
        mViewInstall = aViewInstall;
        mActivityInstall = aActivityInstall;
        mViewInstall.setPresenter(this);
    }

    @Override
    public int onSave(String aFirstName, String aSurname, String aTelNum) {
        //This will need to be async. Need to get firebase token, call server with these details
        //and return with the response letting know success/failure.
        //If success then onInstalled needs to be called. Else, notify user of failure.


    }

    @Override
    public void onInstalled() {mActivityInstall.onInstalled();}
}
