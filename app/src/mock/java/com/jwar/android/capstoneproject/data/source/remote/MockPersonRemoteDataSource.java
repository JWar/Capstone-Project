package com.jwar.android.capstoneproject.data.source.remote;

import android.support.annotation.NonNull;

import com.jraw.android.capstoneproject.data.model.Person;
import com.jraw.android.capstoneproject.data.source.remote.BackendApi;
import com.jraw.android.capstoneproject.data.source.remote.PersonRemoteDataSource;
import com.jraw.android.capstoneproject.data.source.remote.ResponseServerPersonSave;

public class MockPersonRemoteDataSource implements PersonRemoteDataSource {
    private static MockPersonRemoteDataSource sInstance=null;
    private BackendApi mBackendApi;
    public static synchronized MockPersonRemoteDataSource getInstance(@NonNull BackendApi aBackendApi) {
        if (sInstance==null) {
            sInstance = new MockPersonRemoteDataSource(aBackendApi);
        }
        return sInstance;
    }
    private MockPersonRemoteDataSource(@NonNull BackendApi aBackendApi) {
        mBackendApi=aBackendApi;
    }

    @Override
    public ResponseServerPersonSave savePerson(Person aPerson) throws Exception {
        return mBackendApi.sendPerson(aPerson).execute().body();
    }
}
