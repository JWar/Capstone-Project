package com.jwar.android.capstoneproject.data.source.local;

import android.content.Context;

import com.jraw.android.capstoneproject.data.model.PeCo;
import com.jraw.android.capstoneproject.data.source.local.PeCoLocalDataSource;

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
        return 0;
    }
}
