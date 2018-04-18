package com.jraw.android.capstoneproject.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
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
    public Cursor query(@NonNull Uri aUri, @Nullable String[] aStrings, @Nullable String aS, @Nullable String[] aStrings1, @Nullable String aS1) {
        return null;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri aUri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri aUri, @Nullable ContentValues aContentValues) {
        return null;
    }

    @Override
    public int delete(@NonNull Uri aUri, @Nullable String aS, @Nullable String[] aStrings) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri aUri, @Nullable ContentValues aContentValues, @Nullable String aS, @Nullable String[] aStrings) {
        return 0;
    }
}
