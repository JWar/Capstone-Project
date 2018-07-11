package com.jraw.android.capstoneproject.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.jraw.android.capstoneproject.database.DbSchema.*;
import com.jraw.android.capstoneproject.utils.Utils;

/**
 * Created by JonGaming on 02/01/2018.
 * Quick and dirty data base creation.
 */

public class DbHelper extends SQLiteOpenHelper {
    public static final int VERSION = 1;
    public static final String DATABASE_NAME = "CapstoneProjectData";

    public DbHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Utils.logDebug("DbHelper.onCreate");
        db.execSQL(DATABASE_CREATE_PERSON);
        db.execSQL(DATABASE_CREATE_CONVERSATION);
        db.execSQL(DATABASE_CREATE_MSG);
        db.execSQL(DATABASE_CREATE_PECO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion != newVersion) {
            // Simplest implementation is to drop all old tables and recreate them
            //toExec needed because of lint error with string concatenation
            String dropTable = "DROP TABLE IF EXISTS ";
            String toExec = dropTable + DATABASE_CREATE_PERSON;
            db.execSQL(toExec);
            toExec = dropTable + DATABASE_CREATE_MSG;
            db.execSQL(toExec);
            toExec = dropTable + DATABASE_CREATE_CONVERSATION;
            db.execSQL(toExec);
            toExec = dropTable + DATABASE_CREATE_PECO;
            db.execSQL(toExec);
            onCreate(db);
        }
    }

    private static final String DATABASE_CREATE_PERSON =
            "CREATE TABLE " + PersonTable.NAME + " (" +
                    PersonTable.Cols.ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    PersonTable.Cols.FIRSTNAME + " VARCHAR(45) DEFAULT NULL, " +
                    PersonTable.Cols.SURNAME + " VARCHAR(45) DEFAULT NULL, " +
                    PersonTable.Cols.TELNUM + " VARCHAR DEFAULT NULL);";
    private static final String DATABASE_CREATE_MSG =
            "CREATE TABLE " + MsgTable.NAME + " (" +
                    MsgTable.Cols.ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    MsgTable.Cols.COPUBLICID + " INTEGER(11) DEFAULT 0, " +
                    MsgTable.Cols.FROMTEL + " VARCHAR DEFAULT NULL, " +
                    MsgTable.Cols.TOTELS + " VARCHAR DEFAULT NULL, " +
                    MsgTable.Cols.BODY + " VARCHAR DEFAULT NULL, " +
                    MsgTable.Cols.EVENTDATE + " VARCHAR DEFAULT NULL, " +
                    //Type of msg - text, img, video
                    MsgTable.Cols.TYPE + " INTEGER(3) DEFAULT 0, " +
                    //Cant remember what Data is? Is it blob? I.e. for images?
                    MsgTable.Cols.DATA + " VARCHAR DEFAULT NULL, " +
                    //This is the result code for the msg txn?
                    MsgTable.Cols.RESULT + " INTEGER(3) DEFAULT 0);";
    private static final String DATABASE_CREATE_CONVERSATION =
            "CREATE TABLE " + ConversationTable.NAME + " (" +
                    ConversationTable.Cols.ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    ConversationTable.Cols.TITLE + " VARCHAR DEFAULT NULL, " +
                    ConversationTable.Cols.PUBLICID + " INTEGER DEFAULT 0, " +
                    ConversationTable.Cols.CREATEDBY + " VARCHAR, " +
                    ConversationTable.Cols.DATECREATED + " VARCHAR DEFAULT NULL, " +
                    ConversationTable.Cols.DATELASTMSG + " VARCHAR DEFAULT NULL, " +
                    ConversationTable.Cols.SNIPPET + " VARCHAR DEFAULT NULL, " +
                    ConversationTable.Cols.UNREAD + " INTEGER DEFAULT 0, " +
                    ConversationTable.Cols.COUNT + " INTEGER DEFAULT 0);";
    private static final String DATABASE_CREATE_PECO =
            "CREATE TABLE " + PeCoTable.NAME + " (" +
                    PeCoTable.Cols.ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                    PeCoTable.Cols.PEID + " INTEGER(11) DEFAULT 0, " +
                    PeCoTable.Cols.COPUBLICID + " INTEGER DEFAULT 0);";
}
