package com.jraw.android.capstoneproject.data.source.remote;

import com.jraw.android.capstoneproject.data.model.Person;

//Needed for adding user details
public interface PersonRemoteDataSource {
    ResponseServerPerson savePerson(Person aPerson);
}
