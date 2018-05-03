package com.jwar.android.capstoneproject.data.source.local;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v4.content.CursorLoader;

import com.jraw.android.capstoneproject.data.model.Conversation;
import com.jraw.android.capstoneproject.data.source.local.ConversationLocalDataSource;

import java.util.List;

/**
 * Created by JonGaming on 16/04/2018.
 */

public class ProdConversationLocalDataSource implements ConversationLocalDataSource {
    private static ProdConversationLocalDataSource sInstance=null;
    public static synchronized ProdConversationLocalDataSource getInstance() {
        if (sInstance==null) {
            sInstance = new ProdConversationLocalDataSource();
        }
        return sInstance;
    }
    private ProdConversationLocalDataSource() {}

    @Override
    public CursorLoader getConversations(Context aContext) {
        return null;
    }

    @Override
    public CursorLoader getConversationsViaTitle(Context aContext, String aTitle) {
        return null;
    }

    @Override
    public Cursor getConversationViaPublicId(Context aContext, int aCOPublicId) {
        return null;
    }

    @Override
    public long saveConversation(Context aContext, Conversation aConversation) {
        return 0;
    }

    @Override
    public int updateConversation(Context aContext, Conversation aConversation) {
        return 0;
    }
}
