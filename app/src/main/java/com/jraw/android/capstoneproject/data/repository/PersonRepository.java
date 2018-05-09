package com.jraw.android.capstoneproject.data.repository;

import android.support.annotation.NonNull;
import android.support.v4.content.CursorLoader;

import com.jraw.android.capstoneproject.data.model.Person;
import com.jraw.android.capstoneproject.data.source.local.PersonLocalDataSource;
import com.jraw.android.capstoneproject.data.source.remote.PersonRemoteDataSource;

import java.util.ArrayList;
import java.util.List;

public class PersonRepository {

    private static PersonRepository sInstance = null;
    private PersonLocalDataSource mPersonLocalDataSource;
    private PersonRemoteDataSource mPersonRemoteDataSource;

    //Person list for new conversation of added people
    private List<Person> mAddedPersons;

    public static synchronized PersonRepository getInstance(@NonNull PersonLocalDataSource aPersonLocalDataSource,
                                                            @NonNull PersonRemoteDataSource aPersonRemoteDataSource) {
        if (sInstance==null) {
            sInstance = new PersonRepository(aPersonLocalDataSource,aPersonRemoteDataSource);
        }
        return sInstance;
    }
    private PersonRepository(@NonNull PersonLocalDataSource aPersonLocalDataSource,
                             @NonNull PersonRemoteDataSource aPersonRemoteDataSource) {
        mPersonLocalDataSource = aPersonLocalDataSource;
        mPersonRemoteDataSource = aPersonRemoteDataSource;
        mAddedPersons = new ArrayList<>();
    }
    public CursorLoader getPersons() {
        return mPersonLocalDataSource.getPersons();
    }
    public Person getPerson(int aPersonId) {
        return mPersonLocalDataSource.getPerson(aPersonId);
    }
    //Not sure best way to do this... save person to database first? Callback?
    public long savePerson(Person aPerson) {
        mPersonRemoteDataSource.savePerson(aPerson);
        return mPersonLocalDataSource.savePerson(aPerson);
    }
    public List<Person> getAddedPersons() {
        return mAddedPersons;
    }
    public void saveAddedPerson(Person aPerson) {
        mAddedPersons.add(aPerson);
    }
    public void removeAddedPerson(Person aPerson) {
        mAddedPersons.remove(aPerson);
    }
}
