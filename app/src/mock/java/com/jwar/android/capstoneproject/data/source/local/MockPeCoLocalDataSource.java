package com.jwar.android.capstoneproject.data.source.local;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;

import com.jraw.android.capstoneproject.data.model.PeCo;
import com.jraw.android.capstoneproject.data.source.local.PeCoLocalDataSource;
import com.jraw.android.capstoneproject.database.DbSchema;

import java.util.List;

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

    @Override
    public Cursor getPesInCo(Context aContext, long aCOPublicId) {
        Utils.logDebug("InstallPeCoLocalDS.getPesInCo: "+aCOPublicId);
        return aContext.getContentResolver().query(
                PeCoTable.CONTENT_URI,
                null,
                PeCoTable.Cols.COPUBLICID + "=" + aCOPublicId,
                null,
                null
        );
    }
}
