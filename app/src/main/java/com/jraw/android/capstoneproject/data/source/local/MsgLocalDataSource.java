package com.jraw.android.capstoneproject.data.source.local;

import com.jraw.android.capstoneproject.data.model.Msg;

import java.util.List;

public interface MsgLocalDataSource {
    List<Msg> getMsgs(long aConversationPublicId);
    long saveMsg(Msg aMsg);
}
