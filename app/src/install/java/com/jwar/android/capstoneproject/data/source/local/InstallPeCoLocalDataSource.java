package com.jwar.android.capstoneproject.data.source.local;

import android.content.Context;
import android.content.ContentUris;
import android.database.Cursor;

import com.jraw.android.capstoneproject.data.model.PeCo;
import com.jraw.android.capstoneproject.data.source.local.PeCoLocalDataSource;
import com.jraw.android.capstoneproject.database.DbSchema.PeCoTable;
import com.jraw.android.capstoneproject.utils.Utils;


public class InstallPeCoLocalDataSource implements PeCoLocalDataSource {
    private static InstallPeCoLocalDataSource sInstance=null;
    public static synchronized InstallPeCoLocalDataSource getInstance() {
        if (sInstance==null) {
            sInstance = new InstallPeCoLocalDataSource();
        }
        return sInstance;
    }
    private InstallPeCoLocalDataSource() {}

    @Override
    public long savePeCo(Context aContext, PeCo aPeCo) {
        return ContentUris.parseId(aContext.getContentResolver().insert(
                PeCoTable.CONTENT_URI,
                aPeCo.toCV())
        );
    }

    @Override
    public Cursor getPesInCo(Context aContext, long aCOPublicId) {
        return aContext.getContentResolver().query(
                PeCoTable.CONTENT_URI,
                null,
                PeCoTable.Cols.COPUBLICID + "=" + aCOPublicId,
                null,
                null
        );
    }
}
