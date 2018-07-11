package com.jraw.android.capstoneproject.data.source.remote;

import com.jraw.android.capstoneproject.data.model.Msg;

import retrofit2.Call;

public interface MsgRemoteDataSource {
    //Gets all messages from server for this user
    Call<ResponseServerMsg> getMsgsFromServer();
    Call<ResponseServerMsgSave> saveMsg(Msg aMsg);
}
