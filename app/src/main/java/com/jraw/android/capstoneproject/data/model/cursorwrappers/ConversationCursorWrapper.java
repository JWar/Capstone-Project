package com.jraw.android.capstoneproject.data.model.cursorwrappers;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.jraw.android.capstoneproject.data.model.Conversation;
import com.jraw.android.capstoneproject.database.DbSchema.ConversationTable;
import com.jraw.android.capstoneproject.utils.Utils;

/**
 * Created by JonGaming on 22/07/2017.
 *
 */

public class ConversationCursorWrapper extends CursorWrapper {
    public ConversationCursorWrapper(Cursor aCur) {
        super(aCur);
    }

    public Conversation getConversation() {
        try {
            Conversation con = new Conversation();
            con.setId(getInt(getColumnIndexOrThrow(ConversationTable.Cols.ID)));
            if (getColumnIndex(ConversationTable.Cols.PUBLICID) > -1) {
                if (!isNull(getColumnIndex(ConversationTable.Cols.PUBLICID))) {
                    con.setCOPublicId(getLong(getColumnIndex(ConversationTable.Cols.PUBLICID)));
                }
            }
            if (getColumnIndex(ConversationTable.Cols.TITLE) > -1) {
                if (!isNull(getColumnIndex(ConversationTable.Cols.TITLE))) {
                    con.setCOTitle(getString(getColumnIndex(ConversationTable.Cols.TITLE)));
                }
            }
            if (getColumnIndex(ConversationTable.Cols.CREATEDBY) > -1) {
                if (!isNull(getColumnIndex(ConversationTable.Cols.CREATEDBY))) {
                    con.setCOCreatedBy(getString(getColumnIndex(ConversationTable.Cols.CREATEDBY)));
                }
            }
            if (getColumnIndex(ConversationTable.Cols.DATECREATED) > -1) {
                if (!isNull(getColumnIndex(ConversationTable.Cols.DATECREATED))) {
                    con.setCODateCreated(getString(getColumnIndex(ConversationTable.Cols.DATECREATED)));
                }
            }
            if (getColumnIndex(ConversationTable.Cols.DATELASTMSG) > -1) {
                if (!isNull(getColumnIndex(ConversationTable.Cols.DATELASTMSG))) {
                    con.setCODateLastMsg(getString(getColumnIndex(ConversationTable.Cols.DATELASTMSG)));
                }
            }
            if (getColumnIndex(ConversationTable.Cols.SNIPPET) > -1) {
                if (!isNull(getColumnIndex(ConversationTable.Cols.SNIPPET))) {
                    con.setCOSnippet(getString(getColumnIndex(ConversationTable.Cols.SNIPPET)));
                }
            }
            if (getColumnIndex(ConversationTable.Cols.UNREAD) > -1) {
                if (!isNull(getColumnIndex(ConversationTable.Cols.UNREAD))) {
                    con.setCOUnread(getInt(getColumnIndex(ConversationTable.Cols.UNREAD)));
                }
            }
            return con;
        } catch (Exception e) {
            Utils.logDebug("Error in ConversationCursorWrapper.getConversation: " + e.getMessage());
            return null;
        }
    }
}

