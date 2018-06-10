package com.jwar.android.capstoneproject.data.source.local;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.CursorLoader;
import com.jraw.android.capstoneproject.data.model.Conversation;
import com.jraw.android.capstoneproject.data.model.cursorwrappers.ConversationCursorWrapper;
import com.jraw.android.capstoneproject.data.source.local.ConversationLocalDataSource;
import com.jraw.android.capstoneproject.database.DbSchema;
import com.jraw.android.capstoneproject.database.DbSchema.ConversationTable;
import com.jraw.android.capstoneproject.utils.Utils;

/**
 * Created by JonGaming on 16/04/2018.
 */

public class InstallConversationLocalDataSource implements ConversationLocalDataSource {
    private static InstallConversationLocalDataSource sInstance=null;
    public static synchronized InstallConversationLocalDataSource getInstance() {
        if (sInstance==null) {
            sInstance = new InstallConversationLocalDataSource();
        }
        return sInstance;
    }
    private InstallConversationLocalDataSource() {}

    @Override
    public CursorLoader getConversations(Context aContext) {
        return new CursorLoader(
                aContext,
                ConversationTable.CONTENT_URI,
                null,
                null,
                null,
                ConversationTable.Cols.DATELASTMSG + " DESC"//Order by newest message
        );
    }

    @Override
    public CursorLoader getConversationsViaTitle(Context aContext, String aTitle) {
        return new CursorLoader(
                aContext,
                ConversationTable.CONTENT_URI,
                null,
                ConversationTable.Cols.TITLE + " LIKE ?",
                new String[] {"%"+aTitle+"%"},
                ConversationTable.Cols.DATELASTMSG + " DESC"
        );
    }

    @Override
    public Cursor getConversationViaPublicId(Context aContext, long aCOPublicId) {
        return aContext.getContentResolver().query(
                ConversationTable.CONTENT_URI,
                null,
                ConversationTable.Cols.PUBLICID + "=?",
                new String[] {aCOPublicId+""},
                null
        );
    }

    //Gets top two conversations in terms of count. Adds them to conversation list.
    @Override
    public Conversation[] getConversationsTopTwo(Context aContext) {
        ConversationCursorWrapper cursorTT=null;
        Conversation[] conversations= new Conversation[2];
        try {
            cursorTT = new ConversationCursorWrapper(aContext.getContentResolver().query(
                    DbSchema.ConversationTable.CONTENT_URI,
                    null,
                    null,
                    null,
                    DbSchema.ConversationTable.Cols.COUNT + " DESC" +
                            " LIMIT 2"
            ));
            int count = cursorTT.getCount();
            if (count>0) {
                for (int i=0;i<count;i++) {
                    conversations[i]=cursorTT.getConversation();
                }
            }
        } finally {
            Utils.closeCursor(cursorTT);
        }
        return conversations;
    }

    //If unread msgs great than 0..
    @Override
    public Cursor getAllUnreadConversations(Context aContext) {
        return aContext.getContentResolver().query(
                DbSchema.ConversationTable.CONTENT_URI,
                null,
                DbSchema.ConversationTable.Cols.UNREAD + ">0",
                null,
                null
        );
    }

    //This will need to return id which is then used to get the conversations publicid
    //Though I suppose its generated in Conversation creation so maybe can just get publicid that way...
    @Override
    public long saveConversation(Context aContext, Conversation aConversation) {
        return ContentUris.parseId(aContext.getContentResolver().insert(
                ConversationTable.CONTENT_URI,
                aConversation.toCV()
        ));
    }

    @Override
    public int updateConversation(Context aContext, Conversation aConversation) {
        return aContext.getContentResolver().update(
                ConversationTable.CONTENT_URI,
                aConversation.toCV(),
                ConversationTable.Cols.PUBLICID + "=?",
                new String[]{aConversation.getCOPublicId()+""}
        );
    }
}
