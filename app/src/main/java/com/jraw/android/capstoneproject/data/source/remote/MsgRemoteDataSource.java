package com.jraw.android.capstoneproject.data.source.remote;

import com.jraw.android.capstoneproject.data.model.Msg;

public interface MsgRemoteDataSource {
    //Gets all messages from server for this user
    ResponseServerMsg getMsgsFromServer() throws Exception;
    ResponseServerMsgSave saveMsg(Msg aMsg) throws Exception;
}
