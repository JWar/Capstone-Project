package com.jwar.android.capstoneproject.data.source.local;

import android.content.Context;
import android.support.annotation.NonNull;

import com.jraw.android.capstoneproject.data.model.Conversation;
import com.jraw.android.capstoneproject.data.source.local.ConversationLocalDataSource;

import java.util.List;

/**
 * Created by JonGaming on 16/04/2018.
 */

public class ProdConversationLocalDataSource implements ConversationLocalDataSource {
    private static ProdConversationLocalDataSource sInstance=null;
    public static synchronized ProdConversationLocalDataSource getInstance(@NonNull Context aContext) {
        if (sInstance==null) {
            sInstance = new ProdConversationLocalDataSource(aContext);
        }
        return sInstance;
    }
    private ProdConversationLocalDataSource(@NonNull Context aContext) {

    }

    @Override
    public List<Conversation> getConversations() {
        return null;
    }

    @Override
    public List<Conversation> getConversationsViaTitle(String aTitle) {
        return null;
    }
}
