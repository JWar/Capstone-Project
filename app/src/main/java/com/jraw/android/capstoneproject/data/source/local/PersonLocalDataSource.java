package com.jraw.android.capstoneproject.data.source.local;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.CursorLoader;
import com.jraw.android.capstoneproject.data.model.Person;

public interface PersonLocalDataSource {
    //Gets all Contacts...
    CursorLoader getPersons(Context aContext);
    //For PersonDetail
    Person getPerson(Context aContext, int aPersonId);
    Person getPerson(Context aContext, String aPersonTel);
    Cursor getPersonsFromPeIds(Context aContext, String[] aPeIds);
    long savePerson(Context aContext, Person aPerson);
}
