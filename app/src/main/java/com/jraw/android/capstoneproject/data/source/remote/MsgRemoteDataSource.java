package com.jraw.android.capstoneproject.data.source.remote;

import android.content.Context;

import com.jraw.android.capstoneproject.data.model.Msg;

public interface MsgRemoteDataSource {
    //Gets all messages from server for this user
    ResponseServerMsg getMsgsFromServer();
    ResponseServerMsgSave saveMsg(Context aContext, Msg aMsg);
}
