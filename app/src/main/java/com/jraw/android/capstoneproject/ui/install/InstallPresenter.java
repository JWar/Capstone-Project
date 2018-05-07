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

    }

    @Override
    public void onInstalled() {mActivityInstall.onInstalled();}
}
