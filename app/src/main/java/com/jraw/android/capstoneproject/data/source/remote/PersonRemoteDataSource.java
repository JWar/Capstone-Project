package com.jraw.android.capstoneproject.data.source.remote;

import com.jraw.android.capstoneproject.data.model.Person;

import retrofit2.Call;

//Needed for adding user details
public interface PersonRemoteDataSource {
    Call<ResponseServerPersonSave> savePerson(Person aPerson);
}
