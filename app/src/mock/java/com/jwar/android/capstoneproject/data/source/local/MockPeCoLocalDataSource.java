package com.jwar.android.capstoneproject.data.source.local;

import android.content.ContentUris;
import android.content.Context;

import com.jraw.android.capstoneproject.data.model.PeCo;
import com.jraw.android.capstoneproject.data.source.local.PeCoLocalDataSource;
import com.jraw.android.capstoneproject.database.DbSchema;

public class MockPeCoLocalDataSource implements PeCoLocalDataSource {
    private static MockPeCoLocalDataSource sInstance=null;
    public static synchronized MockPeCoLocalDataSource getInstance() {
        if (sInstance==null) {
            sInstance=new MockPeCoLocalDataSource();
        }
        return sInstance;
    }
    private MockPeCoLocalDataSource(){}

    @Override
    public long savePeCo(Context aContext, PeCo aPeCo) {
        return ContentUris.parseId(aContext.getContentResolver().insert(
                DbSchema.PeCoTable.CONTENT_URI,
                aPeCo.toCV())
        );
    }
}
