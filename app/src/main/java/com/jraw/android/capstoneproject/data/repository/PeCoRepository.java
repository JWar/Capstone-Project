package com.jraw.android.capstoneproject.data.repository;

import android.support.annotation.NonNull;

import com.jraw.android.capstoneproject.data.model.PeCo;
import com.jraw.android.capstoneproject.data.source.local.PeCoLocalDataSource;

public class PeCoRepository {
    private static PeCoRepository sInstance=null;
    private PeCoLocalDataSource mPeCoLocalDataSource;
    public static PeCoRepository getInstance(@NonNull PeCoLocalDataSource aPeCoLocalDataSource) {
        if (sInstance==null) {
            sInstance = new PeCoRepository(aPeCoLocalDataSource);
        }
        return sInstance;
    }
    private PeCoRepository(@NonNull PeCoLocalDataSource aPeCoLocalDataSource) {
        mPeCoLocalDataSource=aPeCoLocalDataSource;
    }
    public long savePeCo(PeCo aPeCo) {
        return mPeCoLocalDataSource.savePeCo(aPeCo);
    }
}
