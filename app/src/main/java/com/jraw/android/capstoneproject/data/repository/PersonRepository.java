package com.jraw.android.capstoneproject.data.repository;

import android.support.annotation.NonNull;

import com.jraw.android.capstoneproject.data.model.Person;
import com.jraw.android.capstoneproject.data.source.local.PersonLocalDataSource;
import com.jraw.android.capstoneproject.data.source.remote.PersonRemoteDataSource;

import java.util.List;

public class PersonRepository {

    private static PersonRepository sInstance = null;
    private PersonLocalDataSource mPersonLocalDataSource;
    private PersonRemoteDataSource mPersonRemoteDataSource;

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
    }
    public List<Person> getPersons() {
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
}
