package com.jraw.android.capstoneproject.data.source.local;

import android.database.Cursor;
import com.jraw.android.capstoneproject.data.model.Person;

import java.util.List;

public interface PersonLocalDataSource {
    //Gets all Contacts...
    List<Person> getPersons();
    //For PersonDetail
    Person getPerson(int aPersonId);
    long savePerson(Person aPerson);
}
