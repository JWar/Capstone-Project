package com.jraw.android.capstoneproject.ui.install;

import android.support.annotation.NonNull;

public class InstallPresenter implements InstallContract.PresenterInstall {
    private InstallContract.ViewInstall mViewInstall;
    public InstallPresenter(@NonNull InstallContract.ViewInstall aViewInstall) {
        mViewInstall = aViewInstall;
    }

    @Override
    public void onSave(String aFirstName, String aSurname, String aTelNum) {

    }
}
