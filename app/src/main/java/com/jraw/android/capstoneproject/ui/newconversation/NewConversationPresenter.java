package com.jraw.android.capstoneproject.ui.newconversation;


import android.support.annotation.NonNull;
import android.support.v4.content.CursorLoader;

import com.jraw.android.capstoneproject.data.model.Person;
import com.jraw.android.capstoneproject.data.repository.PersonRepository;

import java.util.List;

/**
 * Created by JonGaming on 19/07/2017.
 *
 */

public class NewConversationPresenter implements NewConversationContract.PresenterNewConversation {

    private final PersonRepository mPersonRepository;

    public NewConversationPresenter(@NonNull PersonRepository aPersonRepository) {
        mPersonRepository = aPersonRepository;
    }
    //Gets all persons for user to select
    @Override
    public CursorLoader getPersons() {
        return mPersonRepository.getPersons();
    }

    @Override
    public List<Person> getAddedPersons() {
        return mPersonRepository.getAddedPersons();
    }

    @Override
    public void addAddedPerson(Person aPerson) {
        mPersonRepository.saveAddedPerson(aPerson);
    }

    @Override
    public void removeAddedPerson(Person aPerson) {
        mPersonRepository.removeAddedPerson(aPerson);
    }

    @Override
    public void onCreateConv() {
        //This will need to create a new conv with the people involved.
        //So PECO, CO needs to be inserted.
    }
}
