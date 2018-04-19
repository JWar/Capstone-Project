package com.jraw.android.capstoneproject.ui.conversation;

import android.support.annotation.NonNull;

import com.jraw.android.capstoneproject.data.repository.ConversationRepository;

public class ConversationPresenter implements ConversationContract.PresenterConversations {

    private final ConversationRepository mConversationRepository;


    private ConversationContract.ViewConversations mViewConversations;

    public ConversationPresenter(@NonNull ConversationRepository aConversationRepository,
                                 @NonNull ConversationContract.ViewConversations aViewConversations) {
        mConversationRepository = aConversationRepository;
        mViewConversations = aViewConversations;
        mViewConversations.setPresenter(this);
    }

    @Override
    public void getConversations() {

    }

    @Override
    public void getConversationsViaTitle(String aTitle) {

    }
}
