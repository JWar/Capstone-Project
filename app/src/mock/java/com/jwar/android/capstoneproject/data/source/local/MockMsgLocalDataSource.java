package com.jwar.android.capstoneproject.data.source.local;

import android.content.ContentUris;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.CursorLoader;
import com.jraw.android.capstoneproject.data.model.Msg;
import com.jraw.android.capstoneproject.data.source.local.MsgLocalDataSource;
import com.jraw.android.capstoneproject.database.DbSchema.MsgTable;

/**
 * Created by JonGaming on 16/04/2018.
 */

public class MockMsgLocalDataSource implements MsgLocalDataSource {
    private static MockMsgLocalDataSource sInstance=null;
    //Param redundant?
    public static synchronized MockMsgLocalDataSource getInstance(@NonNull Context aContext) {
        if (sInstance==null) {
            sInstance = new MockMsgLocalDataSource(aContext.getApplicationContext());
        }
        return sInstance;
    }
    private MockMsgLocalDataSource(@NonNull Context aContext) {
    }

    @Override
    public CursorLoader getMsgs(Context aContext, long aConversationPublicId) {
        return new CursorLoader(aContext,
                MsgTable.CONTENT_URI,
                null,
                MsgTable.Cols.COPUBLICID + "=?",
                new String[] {aConversationPublicId+""},
                MsgTable.Cols.EVENTDATE + " DESC");
    }

    @Override
    public long saveMsg(Context aContext, Msg aMsg) {
        return ContentUris.parseId(aContext.getContentResolver().insert(
                MsgTable.CONTENT_URI,
                aMsg.toCV()
        ));
    }
}
