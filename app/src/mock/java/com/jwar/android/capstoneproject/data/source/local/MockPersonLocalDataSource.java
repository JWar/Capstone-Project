package com.jwar.android.capstoneproject.data.source.local;

import android.content.ContentUris;
import android.content.Context;
import android.support.v4.content.CursorLoader;

import com.jraw.android.capstoneproject.data.model.Person;
import com.jraw.android.capstoneproject.data.model.cursorwrappers.PersonCursorWrapper;
import com.jraw.android.capstoneproject.data.source.local.PersonLocalDataSource;
import com.jraw.android.capstoneproject.database.DbSchema.PersonTable;

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
        return new CursorLoader(aContext,
                PersonTable.CONTENT_URI,
                null,
                null,
                null,
                PersonTable.Cols.FIRSTNAME + " ASC"
        );
    }

    @Override
    public Person getPerson(Context aContext, int aPersonId) {
        PersonCursorWrapper personCursorWrapper = new PersonCursorWrapper(
                aContext.getContentResolver().query(
                        PersonTable.CONTENT_URI,
                        null,
                        PersonTable.Cols.ID + "="+aPersonId,
                        null,
                        null
                )
        );
        Person person = personCursorWrapper.getPerson();
        personCursorWrapper.close();
        return person;
    }

    @Override
    public long savePerson(Context aContext, Person aPerson) {
        return ContentUris.parseId(
                aContext.getContentResolver().insert(
                        PersonTable.CONTENT_URI,
                        aPerson.toCV()
                )
        );
    }
}
