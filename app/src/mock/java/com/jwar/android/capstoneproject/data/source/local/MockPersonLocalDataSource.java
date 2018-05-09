package com.jwar.android.capstoneproject.data.source.local;

import android.content.Context;
import android.support.v4.content.CursorLoader;

import com.jraw.android.capstoneproject.data.model.Person;
import com.jraw.android.capstoneproject.data.source.local.PersonLocalDataSource;

public class MockPersonLocalDataSource implements PersonLocalDataSource {
    private static MockPersonLocalDataSource sInstance=null;
    public static synchronized MockPersonLocalDataSource getInstance() {
        if (sInstance==null) {
            sInstance=new MockPersonLocalDataSource();
        }
        return sInstance;
    }
    private MockPersonLocalDataSource() {}

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
