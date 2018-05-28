package com.jwar.android.capstoneproject.data.source.remote;

import android.support.annotation.NonNull;

import com.jraw.android.capstoneproject.data.model.Person;
import com.jraw.android.capstoneproject.data.source.remote.BackendApi;
import com.jraw.android.capstoneproject.data.source.remote.PersonRemoteDataSource;
import com.jraw.android.capstoneproject.data.source.remote.ResponseServerPersonSave;

public class ProdPersonRemoteDataSource implements PersonRemoteDataSource {
    private static ProdPersonRemoteDataSource sInstance=null;
    private BackendApi mBackendApi;
    public static synchronized ProdPersonRemoteDataSource getInstance(@NonNull BackendApi aBackendApi) {
        if (sInstance==null) {
            sInstance=new ProdPersonRemoteDataSource(aBackendApi);
        }
        return sInstance;
    }
    private ProdPersonRemoteDataSource(@NonNull BackendApi aBackendApi) {
        mBackendApi=aBackendApi;
    }

    @Override
    public ResponseServerPersonSave savePerson(Person aPerson) {
        return mBackendApi.sendPerson("",aPerson);
    }
}
