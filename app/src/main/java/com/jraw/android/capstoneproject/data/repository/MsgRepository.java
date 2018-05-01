package com.jraw.android.capstoneproject.data.repository;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.CursorLoader;
import com.jraw.android.capstoneproject.data.model.Msg;
import com.jraw.android.capstoneproject.data.source.local.MsgLocalDataSource;
import com.jraw.android.capstoneproject.data.source.remote.MsgRemoteDataSource;
import com.jraw.android.capstoneproject.data.source.remote.ResponseServerMsgSave;

/**
 * Created by JonGaming on 16/04/2018.
 * TODO: how to handle init in Firebase/Intent service?
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

    public long saveMsg(Context aContext, Msg aMsg) {
        ResponseServerMsgSave responseServerMsgSave = mMsgRemoteDataSource.saveMsg(aContext, aMsg);
        if (responseServerMsgSave.action.equals("COMPLETE")) {
            //Gets res of save and assigns result in msg.
            aMsg.setMSResult(Msg.RESULTS.valueOf(responseServerMsgSave.res).ordinal());
            return mMsgLocalDataSource.saveMsg(aContext, aMsg);
        } else {
            //TODO: think of how to sort out resending when failed
            aMsg.setMSResult(Msg.RESULTS.FAILED.ordinal());
            return mMsgLocalDataSource.saveMsg(aContext, aMsg);
        }
    }

    public CursorLoader getMsgs(Context aContext, long aConversationPublicId) {
        return mMsgLocalDataSource.getMsgs(aContext, aConversationPublicId);
    }
}
