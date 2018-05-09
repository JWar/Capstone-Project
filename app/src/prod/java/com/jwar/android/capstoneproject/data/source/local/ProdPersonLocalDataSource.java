package com.jwar.android.capstoneproject.data.source.local;

import android.content.Context;
import android.support.v4.content.CursorLoader;

import com.jraw.android.capstoneproject.data.model.Person;
import com.jraw.android.capstoneproject.data.source.local.PersonLocalDataSource;

public class ProdPersonLocalDataSource implements PersonLocalDataSource {
    private static ProdPersonLocalDataSource sInstance=null;
    public static synchronized ProdPersonLocalDataSource getInstance() {
        if (sInstance==null) {
            sInstance=new ProdPersonLocalDataSource();
        }
        return sInstance;
    }
    private ProdPersonLocalDataSource() {}

    @Override
    public CursorLoader getPersons(Context aContext) {
        return null;
    }

    @Override
    public Person getPerson(Context aContext, int aPersonId) {
        return null;
    }

    @Override
    public long savePerson(Context aContext, Person aPerson) {
        return 0;
    }
}
