package com.jwar.android.capstoneproject.data.source.local;

import android.content.Context;
import android.support.v4.content.CursorLoader;

import com.jraw.android.capstoneproject.data.model.Msg;
import com.jraw.android.capstoneproject.data.source.local.MsgLocalDataSource;

/**
 * Created by JonGaming on 16/04/2018.
 */

public class ProdMsgLocalDataSource implements MsgLocalDataSource {
    private static ProdMsgLocalDataSource sInstance=null;
    public static ProdMsgLocalDataSource getInstance() {
        if (sInstance==null) {
            sInstance = new ProdMsgLocalDataSource();
        }
        return sInstance;
    }
    private ProdMsgLocalDataSource() {}

    @Override
    public CursorLoader getMsgs(Context aContext, long aConversationPublicId) {
        return null;
    }

    @Override
    public long saveMsg(Context aContext, Msg aMsg) {
        return 0;
    }
}
