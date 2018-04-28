package com.jraw.android.capstoneproject.ui.conversation;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.CursorLoader;

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
    public CursorLoader getConversations(Context aContext) {
        return mConversationRepository.getConversations(aContext);
    }

    @Override
    public CursorLoader getConversationsViaTitle(Context aContext, String aTitle) {
        return mConversationRepository.getConversationsViaTitle(aContext, aTitle);
    }
}
