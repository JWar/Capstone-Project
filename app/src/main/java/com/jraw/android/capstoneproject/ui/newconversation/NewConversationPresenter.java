package com.jraw.android.capstoneproject.ui.newconversation;


import android.support.annotation.NonNull;
import android.support.v4.content.CursorLoader;

import com.jraw.android.capstoneproject.data.repository.PersonRepository;

/**
 * Created by JonGaming on 19/07/2017.
 *
 */

public class NewConversationPresenter {

    private final PersonRepository mPersonRepository;

    public NewConversationPresenter(@NonNull PersonRepository aPersonRepository) {
        mPersonRepository = aPersonRepository;
    }
    //Gets all persons for user to select
    public CursorLoader getPersons() {
        return mPersonRepository.getPersons();
    }
}
