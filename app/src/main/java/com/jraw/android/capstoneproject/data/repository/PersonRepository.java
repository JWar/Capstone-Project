package com.jraw.android.capstoneproject.data.repository;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.CursorLoader;

import com.jraw.android.capstoneproject.data.model.Person;
import com.jraw.android.capstoneproject.data.source.local.PersonLocalDataSource;
import com.jraw.android.capstoneproject.data.source.remote.PersonRemoteDataSource;
import com.jraw.android.capstoneproject.data.source.remote.ResponseServerPersonSave;

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
    public CursorLoader getPersons(Context aContext) {
        return mPersonLocalDataSource.getPersons(aContext);
    }
    public Person getPerson(Context aContext, int aPersonId) {
        return mPersonLocalDataSource.getPerson(aContext, aPersonId);
    }
    public Person getPerson(Context aContext, String aPersonTel) {
        return mPersonLocalDataSource.getPerson(aContext, aPersonTel);
    }
    public long savePersonLocal(Context aContext, Person aPerson) {
        return mPersonLocalDataSource.savePerson(aContext, aPerson);
    }
    public ResponseServerPersonSave savePersonRemote(Person aPerson) {
        return mPersonRemoteDataSource.savePerson(aPerson);
    }
    public List<Person> getAddedPersons() {
        return mAddedPersons;
    }
    public void saveAddedPerson(Person aPerson) {
        if (!mAddedPersons.contains(aPerson)) {//Only add if not already in
            mAddedPersons.add(aPerson);
        }
    }
    public void removeAddedPerson(Person aPerson) {
        mAddedPersons.remove(aPerson);
    }
    public void clearAddedPersons() {mAddedPersons.clear();}
}
