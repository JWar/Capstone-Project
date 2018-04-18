package com.jraw.android.capstoneproject.data.repository;

import android.content.Context;
import android.support.annotation.NonNull;

import com.jraw.android.capstoneproject.data.model.Msg;
import com.jraw.android.capstoneproject.data.source.local.MsgLocalDataSource;
import com.jraw.android.capstoneproject.data.source.remote.MsgRemoteDataSource;
import com.jraw.android.capstoneproject.utils.Utils;
import com.jwar.android.capstoneproject.Injection;

import java.util.List;

/**
 * Created by JonGaming on 16/04/2018.
 */

public class MsgRepository {

    private static MsgRepository sInstance=null;
    private MsgLocalDataSource mMsgLocalDataSource;
    private MsgRemoteDataSource mMsgRemoteDataSource;

    public static synchronized MsgRepository getInstance(@NonNull MsgLocalDataSource aMsgLocalDataSource,
                                                         @NonNull MsgRemoteDataSource aMsgRemoteDataSource) {
        if (sInstance==null) {
            sInstance = new MsgRepository(aMsgLocalDataSource, aMsgRemoteDataSource);
        }
        return sInstance;
    }
    private MsgRepository(@NonNull MsgLocalDataSource aMsgLocalDataSource,
                          @NonNull MsgRemoteDataSource aMsgRemoteDataSource) {
        mMsgLocalDataSource = aMsgLocalDataSource;
        mMsgRemoteDataSource = aMsgRemoteDataSource;
    }
    public void destroyInstance() {
        sInstance=null;
    }
    public long saveMsg(Msg aMsg) {
        return mMsgLocalDataSource.saveMsg(aMsg);
    }
    public List<Msg> getMsgs(long aConversationPublicId) {
        return mMsgLocalDataSource.getMsgs(aConversationPublicId);
    }
}
