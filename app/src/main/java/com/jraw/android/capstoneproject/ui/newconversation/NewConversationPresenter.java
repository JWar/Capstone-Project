package com.jraw.android.capstoneproject.ui.newconversation;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.CursorLoader;

import com.jraw.android.capstoneproject.data.model.Conversation;
import com.jraw.android.capstoneproject.data.model.Person;
import com.jraw.android.capstoneproject.data.repository.ConversationRepository;
import com.jraw.android.capstoneproject.data.repository.PeCoRepository;
import com.jraw.android.capstoneproject.data.repository.PersonRepository;

import java.util.List;

/**
 * Created by JonGaming on 19/07/2017.
 *
 */

public class NewConversationPresenter implements NewConversationContract.PresenterNewConversation {

    private final PersonRepository mPersonRepository;
    private final ConversationRepository mConversationRepository;
    private final PeCoRepository mPeCoRepository;

    public NewConversationPresenter(@NonNull PersonRepository aPersonRepository,
                                    @NonNull ConversationRepository aConversationRepository,
                                    @NonNull PeCoRepository aPeCoRepository) {
        mPersonRepository = aPersonRepository;
        mConversationRepository=aConversationRepository;
        mPeCoRepository=aPeCoRepository;
    }
    //Gets all persons for user to select
    @Override
    public CursorLoader getPersons(Context aContext) {
        return mPersonRepository.getPersons(aContext);
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
    public void onCreateConv(Context aContext, String aCoTitle) {
        //This will need to create a new conv with the people involved.
        //So PECO, CO needs to be inserted.
        List<Person> personList = mPersonRepository.getAddedPersons();
        Conversation newConversation = new Conversation();
        newConversation.setCOTitle(aCoTitle);
        //Just using this method for now. cba with bitwise ops
        newConversation.setCOPublicId(System.currentTimeMillis());

    }
}
