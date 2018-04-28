package com.jwar.android.capstoneproject.data.source.local;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.CursorLoader;
import com.jraw.android.capstoneproject.data.source.local.ConversationLocalDataSource;
import com.jraw.android.capstoneproject.database.DbSchema;

/**
 * Created by JonGaming on 16/04/2018.
 */

public class MockConversationLocalDataSource  implements ConversationLocalDataSource {
    private static MockConversationLocalDataSource sInstance=null;
    //Param redundant??
    public static synchronized MockConversationLocalDataSource getInstance(@NonNull Context aContext) {
        if (sInstance==null) {
            sInstance = new MockConversationLocalDataSource(aContext.getApplicationContext());
        }
        return sInstance;
    }
    private MockConversationLocalDataSource(@NonNull Context aContext) {

    }

    @Override
    public CursorLoader getConversations(Context aContext) {
        CursorLoader cursorLoader = new CursorLoader(
                aContext,
                DbSchema.ConversationTable.CONTENT_URI,
                null,
                null,
                null,
                null
        );
        return cursorLoader;
    }

    @Override
    public CursorLoader getConversationsViaTitle(Context aContext, String aTitle) {
        return null;
    }
}
