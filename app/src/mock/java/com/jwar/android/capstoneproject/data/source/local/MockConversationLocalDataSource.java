package com.jwar.android.capstoneproject.data.source.local;

import android.content.Context;
import android.support.annotation.NonNull;

import com.jraw.android.capstoneproject.data.model.Conversation;
import com.jraw.android.capstoneproject.data.source.local.ConversationLocalDataSource;

import java.util.List;

/**
 * Created by JonGaming on 16/04/2018.
 */

public class MockConversationLocalDataSource  implements ConversationLocalDataSource {
    private static MockConversationLocalDataSource sInstance=null;
    public static synchronized MockConversationLocalDataSource getInstance(@NonNull Context aContext) {
        if (sInstance==null) {
            sInstance = new MockConversationLocalDataSource(aContext);
        }
        return sInstance;
    }
    private MockConversationLocalDataSource(@NonNull Context aContext) {

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
