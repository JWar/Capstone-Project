package com.jraw.android.capstoneproject.data.repository;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.CursorLoader;

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

    public CursorLoader getConversations(Context aContext) {
        return mConversationLocalDataSource.getConversations(aContext);
    }
    public CursorLoader getConversationsViaTitle(Context aContext, String aTitle) {
        return mConversationLocalDataSource.getConversationsViaTitle(aContext, aTitle);
    }
}
