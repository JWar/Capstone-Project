package com.jraw.android.capstoneproject.ui;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import com.jraw.android.capstoneproject.ui.install.InstallContract;

import java.lang.ref.WeakReference;

//This is for Install routine
public class IntegerAsyncTaskLoader extends AsyncTaskLoader<Integer> {
    private final WeakReference<Activity> mActivity;
    private final WeakReference<InstallContract.PresenterInstall> mInstallPresenter;
    private String mFirstname;
    private String mSurname;
    private String mTel;

    public IntegerAsyncTaskLoader(Activity activity,
                                  InstallContract.PresenterInstall aPresenterInstall,
                                  String aFirstname,
                                  String aSurname,
                                  String aTel) {
        super(activity);
        mActivity = new WeakReference<>(activity);
        mInstallPresenter=new WeakReference<>(aPresenterInstall);
        mFirstname=aFirstname;
        mSurname=aSurname;
        mTel=aTel;
    }

    @Nullable
    @Override
    public Integer loadInBackground() {
        return mInstallPresenter.get().onSave(mFirstname,mSurname,mTel);
    }
}
