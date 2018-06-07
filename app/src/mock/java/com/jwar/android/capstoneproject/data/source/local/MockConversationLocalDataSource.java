package com.jwar.android.capstoneproject.data.source.local;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v4.content.CursorLoader;

import com.jraw.android.capstoneproject.data.model.Conversation;
import com.jraw.android.capstoneproject.data.model.Msg;
import com.jraw.android.capstoneproject.data.model.PeCo;
import com.jraw.android.capstoneproject.data.model.Person;
import com.jraw.android.capstoneproject.data.model.cursorwrappers.ConversationCursorWrapper;
import com.jraw.android.capstoneproject.data.source.local.ConversationLocalDataSource;
import com.jraw.android.capstoneproject.database.DbSchema;
import com.jraw.android.capstoneproject.utils.Utils;
import com.jwar.android.capstoneproject.DummyData;

import java.util.List;

/**
 * Created by JonGaming on 16/04/2018.
 * Ok this isnt the best way of doing this but itll have to do:
 * Need a way of checking if dummy data is in database for Mock. Will have a key/value check in
 * shared preferences to check if this is the first time Mock has been init.
 * To be really rigorous this first init check and insert should be done in all local data stores,
 * just as a precaution. But frankly dont think it should ever be an issue...
 */

public class MockConversationLocalDataSource  implements ConversationLocalDataSource {
    private static MockConversationLocalDataSource sInstance=null;

    private static final String FIRST_INIT = "firstInit";

    //Param redundant??
    public static synchronized MockConversationLocalDataSource getInstance() {
        if (sInstance==null) {
            sInstance = new MockConversationLocalDataSource();
        }
        return sInstance;
    }
    private MockConversationLocalDataSource() {}

    @Override
    public CursorLoader getConversations(Context aContext) {
        SharedPreferences sharedPreferences = aContext.getSharedPreferences(Utils.SHAR_PREFS,0);
        if (sharedPreferences.getBoolean(FIRST_INIT,true)) {
            //Insert DummyData
            insertDummyData(aContext);
            //Update sharedPrefs
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(FIRST_INIT,false);
            editor.commit();//Dont think it matters too much but this is being done on same thread as getConversations is asyncd
        }
        return new CursorLoader(
                aContext,
                DbSchema.ConversationTable.CONTENT_URI,
                null,
                null,
                null,
                DbSchema.ConversationTable.Cols.DATELASTMSG + " DESC"//Order by newest message
        );
    }

    private void insertDummyData(Context aContext) {
        try {
            ContentResolver contentResolver = aContext.getContentResolver();
            List<Conversation> conversations = DummyData.getConversations();
            ContentValues[] contentValues = new ContentValues[conversations.size()];
            for (int i = 0; i < conversations.size(); i++) {
                contentValues[i] = conversations.get(i).toCV();
            }
            contentResolver.bulkInsert(DbSchema.ConversationTable.CONTENT_URI, contentValues);
            List<Msg> msgs = DummyData.getMsgs();
            contentValues = new ContentValues[msgs.size()];
            for (int i = 0; i < msgs.size(); i++) {
                contentValues[i] = msgs.get(i).toCV();
            }
            contentResolver.bulkInsert(DbSchema.MsgTable.CONTENT_URI, contentValues);
            List<Person> persons = DummyData.getPersons();
            contentValues = new ContentValues[persons.size()];
            for (int i = 0; i < persons.size(); i++) {
                contentValues[i] = persons.get(i).toCV();
            }
            contentResolver.bulkInsert(DbSchema.PersonTable.CONTENT_URI, contentValues);
            List<PeCo> peCos = DummyData.getPeCos();
            contentValues = new ContentValues[peCos.size()];
            for (int i = 0; i < peCos.size(); i++) {
                contentValues[i] = peCos.get(i).toCV();
            }
            contentResolver.bulkInsert(DbSchema.PeCoTable.CONTENT_URI, contentValues);
        } catch (Exception e) {
            Utils.logDebug("MockConversationLocalDataSource.insertDummyData: "+e.getLocalizedMessage());
        }
    }

    @Override
    public CursorLoader getConversationsViaTitle(Context aContext, String aTitle) {
        return new CursorLoader(
                aContext,
                DbSchema.ConversationTable.CONTENT_URI,
                null,
                DbSchema.ConversationTable.Cols.TITLE + " LIKE ?",
                new String[] {"%"+aTitle+"%"},
                DbSchema.ConversationTable.Cols.DATELASTMSG + " DESC"
        );
    }

    @Override
    public Cursor getConversationViaPublicId(Context aContext, long aCOPublicId) {
        return aContext.getContentResolver().query(
                DbSchema.ConversationTable.CONTENT_URI,
                null,
                DbSchema.ConversationTable.Cols.PUBLICID + "=?",
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
                DbSchema.ConversationTable.CONTENT_URI,
                aConversation.toCV()
        ));
    }

    @Override
    public int updateConversation(Context aContext, Conversation aConversation) {
        return aContext.getContentResolver().update(
                DbSchema.ConversationTable.CONTENT_URI,
                aConversation.toCV(),
                DbSchema.ConversationTable.Cols.PUBLICID + "=?",
                new String[]{aConversation.getCOPublicId()+""}
        );
    }
}
