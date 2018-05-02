package com.jwar.android.capstoneproject.data.source.local;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.CursorLoader;
import com.jraw.android.capstoneproject.data.model.Msg;
import com.jraw.android.capstoneproject.data.source.local.MsgLocalDataSource;
import com.jraw.android.capstoneproject.database.DbSchema;
import com.jraw.android.capstoneproject.database.DbSchema.MsgTable;

import java.util.List;

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
    //This will need to handle NEW conversations AND update conversations to reflect changes in their msg lists.
    //So need to check for existence of COPublicId in Conversation List. If not existing then there needs to be
    //a new conversation with the title and Public Id (what about created by?).
    //Will also need to update any conversations with the msg snippet, time, and read status.
    //Sounds like bulk insert is a bad idea... Will have to go through each new msg and do checks/updates/inserts
    @Override
    public int saveMsg(Context aContext, List<Msg> aMsgList) {
        //This is far more complicated than it first appears.
        int numNewMsgs = -1;
        ContentValues[] contentValues = new ContentValues[aMsgList.size()];
        for (int i = 0; i < aMsgList.size(); i++) {
            contentValues[i] = aMsgList.get(i).toCV();
        }
        numNewMsgs = aContext.getContentResolver().bulkInsert(MsgTable.CONTENT_URI,contentValues);
        //Install lots of new things

        return numNewMsgs;
    }
}
