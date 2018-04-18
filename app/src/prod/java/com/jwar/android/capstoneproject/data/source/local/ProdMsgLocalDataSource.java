package com.jwar.android.capstoneproject.data.source.local;

import android.content.Context;
import android.support.annotation.NonNull;

import com.jraw.android.capstoneproject.data.model.Msg;
import com.jraw.android.capstoneproject.data.source.local.MsgLocalDataSource;

import java.util.List;

/**
 * Created by JonGaming on 16/04/2018.
 */

public class ProdMsgLocalDataSource implements MsgLocalDataSource {
    private static ProdMsgLocalDataSource sInstance=null;
    public static ProdMsgLocalDataSource getInstance(@NonNull Context aContext) {
        if (sInstance==null) {
            sInstance = new ProdMsgLocalDataSource(aContext);
        }
        return sInstance;
    }
    private ProdMsgLocalDataSource(@NonNull Context aContext) {

    }

    @Override
    public List<Msg> getMsgs(long aConversationPublicId) {
        return null;
    }

    @Override
    public long saveMsg(Msg aMsg) {
        return 0;
    }
}
