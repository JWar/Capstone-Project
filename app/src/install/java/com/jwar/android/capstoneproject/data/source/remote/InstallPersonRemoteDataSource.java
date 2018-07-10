package com.jwar.android.capstoneproject.data.source.remote;

import android.support.annotation.NonNull;

import com.jraw.android.capstoneproject.data.model.Person;
import com.jraw.android.capstoneproject.data.source.remote.BackendApi;
import com.jraw.android.capstoneproject.data.source.remote.PersonRemoteDataSource;
import com.jraw.android.capstoneproject.data.source.remote.ResponseServerPersonSave;
import com.jraw.android.capstoneproject.utils.Utils;

public class InstallPersonRemoteDataSource implements PersonRemoteDataSource {
    private static InstallPersonRemoteDataSource sInstance=null;
    private BackendApi mBackendApi;
    public static synchronized InstallPersonRemoteDataSource getInstance(@NonNull BackendApi aBackendApi) {
        if (sInstance==null) {
            sInstance=new InstallPersonRemoteDataSource(aBackendApi);
        }
        return sInstance;
    }
    private InstallPersonRemoteDataSource(@NonNull BackendApi aBackendApi) {
        mBackendApi=aBackendApi;
    }

    @Override
    public ResponseServerPersonSave savePerson(Person aPerson) {
        try {
            return mBackendApi.sendPerson(
                    Utils.URL,
                    aPerson);
        } catch (Exception e) {
            Utils.logDebug("InstallPersonRemoteDS.savePerson: "+e.getLocalizedMessage());
            return null;
        }
    }
}
