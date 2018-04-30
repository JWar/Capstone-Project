package com.jwar.android.capstoneproject.data.source.local;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v4.content.CursorLoader;

import com.jraw.android.capstoneproject.data.model.Conversation;
import com.jraw.android.capstoneproject.data.model.Msg;
import com.jraw.android.capstoneproject.data.model.PeCo;
import com.jraw.android.capstoneproject.data.model.Person;
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
}