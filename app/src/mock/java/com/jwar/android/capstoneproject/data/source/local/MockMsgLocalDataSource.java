package com.jwar.android.capstoneproject.data.source.local;

import android.content.Context;
import android.support.annotation.NonNull;

import com.jraw.android.capstoneproject.data.model.Msg;
import com.jraw.android.capstoneproject.data.source.local.MsgLocalDataSource;

import java.util.List;

/**
 * Created by JonGaming on 16/04/2018.
 */

public class MockMsgLocalDataSource implements MsgLocalDataSource {
    private static MockMsgLocalDataSource sInstance=null;
    public static synchronized MockMsgLocalDataSource getInstance(@NonNull Context aContext) {
        if (sInstance==null) {
            sInstance = new MockMsgLocalDataSource(aContext);
        }
        return sInstance;
    }
    private MockMsgLocalDataSource(@NonNull Context aContext) {

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
