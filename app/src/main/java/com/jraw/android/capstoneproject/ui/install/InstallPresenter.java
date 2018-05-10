package com.jraw.android.capstoneproject.ui.install;

import android.support.annotation.NonNull;

public class InstallPresenter implements InstallContract.PresenterInstall {
    private InstallContract.ViewInstall mViewInstall;
    private InstallContract.ActivityInstall mActivityInstall;
    public InstallPresenter(@NonNull InstallContract.ViewInstall aViewInstall,
                            @NonNull InstallContract.ActivityInstall aActivityInstall) {
        mViewInstall = aViewInstall;
        mActivityInstall = aActivityInstall;
    }

    @Override
    public void onSave(String aFirstName, String aSurname, String aTelNum) {
        //This will need to be async. Need to get firebase token, call server with these details
        //and return with the response letting know success/failure.
        //If success then onInstalled needs to be called. Else, notify user of failure.

        onInstalled();
        onFailure();
    }

    @Override
    public void onInstalled() {mActivityInstall.onInstalled();}

    @Override
    public void onFailure() {
        mViewInstall.onFailure();
    }
}
