package com.jraw.android.capstoneproject.data.source.local;

import android.support.v4.content.CursorLoader;
import com.jraw.android.capstoneproject.data.model.Person;

public interface PersonLocalDataSource {
    //Gets all Contacts...
    CursorLoader getPersons();
    //For PersonDetail
    Person getPerson(int aPersonId);
    long savePerson(Person aPerson);
}
