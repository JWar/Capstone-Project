package com.jraw.android.capstoneproject.database;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by JonGaming on 16/04/2018.
 */

public class CProvider extends ContentProvider {
    public static final int CODE_PERSON = 100;
    public static final int CODE_CONVERSATION = 101;
    public static final int CODE_MSG = 102;
    public static final int CODE_PECO = 103;
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private DbHelper mDbHelper;
    public static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = DbSchema.CONTENT_AUTHORITY;
        matcher.addURI(authority, DbSchema.PATH_PERSON,CODE_PERSON);
        matcher.addURI(authority, DbSchema.PATH_CONVERSATION,CODE_CONVERSATION);
        matcher.addURI(authority, DbSchema.PATH_MSG,CODE_MSG);
        matcher.addURI(authority, DbSchema.PATH_PECO,CODE_PECO);
        return matcher;
    }

    @Override
    public boolean onCreate() {
        mDbHelper = new DbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri aUri, @Nullable String[] aProjection, @Nullable String aSelection,
                        @Nullable String[] aSelectionArgs, @Nullable String aSortOrder) {
        Cursor cursor;
        switch (sUriMatcher.match(aUri)) {
            case CODE_MSG:
                cursor = mDbHelper.getReadableDatabase().query(
                        DbSchema.MsgTable.NAME,
                        aProjection,
                        aSelection,
                        aSelectionArgs,
                        null,
                        null,
                        aSortOrder
                );
                break;
            case CODE_PERSON:
                cursor = mDbHelper.getReadableDatabase().query(
                        DbSchema.PersonTable.NAME,
                        aProjection,
                        aSelection,
                        aSelectionArgs,
                        null,
                        null,
                        aSortOrder
                );
                break;
            case CODE_CONVERSATION:
                cursor = mDbHelper.getReadableDatabase().query(
                        DbSchema.ConversationTable.NAME,
                        aProjection,
                        aSelection,
                        aSelectionArgs,
                        null,
                        null,
                        aSortOrder
                );
                break;
            case CODE_PECO:
                cursor = mDbHelper.getReadableDatabase().query(
                        DbSchema.PeCoTable.NAME,
                        aProjection,
                        aSelection,
                        aSelectionArgs,
                        null,
                        null,
                        aSortOrder
                );
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + aUri);
        }
        cursor.setNotificationUri(getContext().getContentResolver(),aUri);
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri aUri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri aUri, @Nullable ContentValues aContentValues) {
        final SQLiteDatabase db = mDbHelper.getWritableDatabase();
        long id;
        switch (sUriMatcher.match(aUri)) {
            case CODE_MSG:
                id = db.insertOrThrow(DbSchema.MsgTable.NAME,
                        null,
                        aContentValues);
                break;
            case CODE_CONVERSATION:
                id = db.insertOrThrow(DbSchema.ConversationTable.NAME,
                        null,
                        aContentValues);
                break;
            case CODE_PERSON:
                id = db.insertOrThrow(DbSchema.PersonTable.NAME,
                        null,
                        aContentValues);
                break;
            case CODE_PECO:
                id = db.insertOrThrow(DbSchema.PeCoTable.NAME,
                        null,
                        aContentValues);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + aUri);
        }
        getContext().getContentResolver().notifyChange(aUri, null);
        return ContentUris.withAppendedId(aUri, id);
    }

    @Override
    public int delete(@NonNull Uri aUri, @Nullable String aSelection, @Nullable String[] aSelectionArgs) {
        int numRowsDeleted;
        if (aSelection == null) {
            aSelection = "1";
        }
        String tableToUse;
        switch (sUriMatcher.match(aUri)) {
            case CODE_MSG:
                tableToUse = DbSchema.MsgTable.NAME;
                break;
            case CODE_CONVERSATION:
                tableToUse = DbSchema.ConversationTable.NAME;
                break;
            case CODE_PERSON:
                tableToUse = DbSchema.PersonTable.NAME;
                break;
            case CODE_PECO:
                tableToUse = DbSchema.PeCoTable.NAME;
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + aUri);
        }
        numRowsDeleted = mDbHelper.getWritableDatabase().delete(
                tableToUse,
                aSelection,
                aSelectionArgs);
        if (numRowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(aUri, null);
        }
        return numRowsDeleted;
    }

    @Override
    public int update(@NonNull Uri aUri, @Nullable ContentValues aContentValues, @Nullable String aSelection, @Nullable String[] aSelectionArgs) {
        int numRowsUpdated;
        if (aSelection == null) {
            aSelection = "1";
        }
        String tableToUse;
        switch (sUriMatcher.match(aUri)) {
            case CODE_MSG:
                tableToUse = DbSchema.MsgTable.NAME;
                break;
            case CODE_CONVERSATION:
                tableToUse = DbSchema.ConversationTable.NAME;
                break;
            case CODE_PERSON:
                tableToUse = DbSchema.PersonTable.NAME;
                break;
            case CODE_PECO:
                tableToUse = DbSchema.PeCoTable.NAME;
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + aUri);
        }
        numRowsUpdated = mDbHelper.getWritableDatabase().update(
                tableToUse,
                aContentValues,
                aSelection,
                aSelectionArgs);
        if (numRowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(aUri, null);
        }
        return numRowsUpdated;
    }
}
