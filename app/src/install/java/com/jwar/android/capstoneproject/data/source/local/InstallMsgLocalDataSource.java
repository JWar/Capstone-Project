package com.jwar.android.capstoneproject.data.source.local;

import android.content.ContentUris;
import android.content.Context;
import android.support.v4.content.CursorLoader;

import com.jraw.android.capstoneproject.data.model.Msg;
import com.jraw.android.capstoneproject.data.source.local.MsgLocalDataSource;
import com.jraw.android.capstoneproject.database.DbSchema.MsgTable;

/**
 * Created by JonGaming on 16/04/2018.
 */

public class InstallMsgLocalDataSource implements MsgLocalDataSource {
    private static InstallMsgLocalDataSource sInstance=null;
    public static InstallMsgLocalDataSource getInstance() {
        if (sInstance==null) {
            sInstance = new InstallMsgLocalDataSource();
        }
        return sInstance;
    }
    private InstallMsgLocalDataSource() {}

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
    @Override
    public int deleteMsg(Msg aMsg) {
        return 0;
    }
}
