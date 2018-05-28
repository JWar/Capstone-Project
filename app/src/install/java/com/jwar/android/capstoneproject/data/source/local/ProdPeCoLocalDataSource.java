package com.jwar.android.capstoneproject.data.source.local;

import android.content.Context;

import com.jraw.android.capstoneproject.data.model.PeCo;
import com.jraw.android.capstoneproject.data.source.local.PeCoLocalDataSource;

public class ProdPeCoLocalDataSource implements PeCoLocalDataSource {
    private static ProdPeCoLocalDataSource sInstance=null;
    public static synchronized ProdPeCoLocalDataSource getInstance() {
        if (sInstance==null) {
            sInstance = new ProdPeCoLocalDataSource();
        }
        return sInstance;
    }
    private ProdPeCoLocalDataSource() {}

    @Override
    public long savePeCo(Context aContext, PeCo aPeCo) {
        return ContentUris.parseId(aContext.getContentResolver().insert(
                DbSchema.PeCoTable.CONTENT_URI,
                aPeCo.toCV())
        );
    }
}
