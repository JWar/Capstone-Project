package com.jraw.android.capstoneproject.data.repository;

import android.support.annotation.NonNull;

import com.jraw.android.capstoneproject.data.model.Conversation;
import com.jraw.android.capstoneproject.data.source.local.ConversationLocalDataSource;

import java.util.List;

public class ConversationRepository {
    private static ConversationRepository sInstance=null;
    private ConversationLocalDataSource mConversationLocalDataSource;

    public static synchronized ConversationRepository getInstance(@NonNull ConversationLocalDataSource aConversationLocalDataSource) {
        if (sInstance==null) {
            sInstance = new ConversationRepository(aConversationLocalDataSource);
        }
        return sInstance;
    }
    private ConversationRepository(@NonNull ConversationLocalDataSource aConversationLocalDataSource) {
        mConversationLocalDataSource = aConversationLocalDataSource;
    }
    public void destroyInstance() {
        sInstance=null;
    }

    public List<Conversation> getConversations() {
        return mConversationLocalDataSource.getConversations();
    }
    public List<Conversation> getConversationsViaTitle(String aTitle) {
        return mConversationLocalDataSource.getConversationsViaTitle(aTitle);
    }
}
