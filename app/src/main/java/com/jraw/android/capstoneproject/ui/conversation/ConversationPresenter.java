package com.jraw.android.capstoneproject.ui.conversation;

import android.support.annotation.NonNull;

import com.jraw.android.capstoneproject.data.model.Conversation;
import com.jraw.android.capstoneproject.data.repository.ConversationRepository;

import java.util.List;

public class ConversationPresenter implements ConversationContract.PresenterConversations {

    private final ConversationRepository mConversationRepository;

    //Redundant with Loader in View?
    private ConversationContract.ViewConversations mViewConversations;

    public ConversationPresenter(@NonNull ConversationRepository aConversationRepository,
                                 @NonNull ConversationContract.ViewConversations aViewConversations) {
        mConversationRepository = aConversationRepository;
        mViewConversations = aViewConversations;
        mViewConversations.setPresenter(this);
    }

    @Override
    public List<Conversation> getConversations() {
        return mConversationRepository.getConversations();
    }

    @Override
    public List<Conversation> getConversationsViaTitle(String aTitle) {
        return mConversationRepository.getConversationsViaTitle(aTitle);
    }
}
