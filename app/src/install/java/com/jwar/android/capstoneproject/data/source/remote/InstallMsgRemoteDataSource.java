package com.jwar.android.capstoneproject.data.source.remote;

import android.support.annotation.NonNull;

import com.jraw.android.capstoneproject.data.model.Msg;
import com.jraw.android.capstoneproject.data.source.remote.BackendApi;
import com.jraw.android.capstoneproject.data.source.remote.MsgRemoteDataSource;
import com.jraw.android.capstoneproject.data.source.remote.ResponseServerMsg;
import com.jraw.android.capstoneproject.data.source.remote.ResponseServerMsgSave;
import com.jraw.android.capstoneproject.utils.Utils;

import retrofit2.Call;

/**
 * Created by JonGaming on 16/04/2018.
 */

public class InstallMsgRemoteDataSource implements MsgRemoteDataSource {
    private static InstallMsgRemoteDataSource sInstance=null;
    private BackendApi mBackendApi;
    public static InstallMsgRemoteDataSource getInstance(@NonNull BackendApi aBackendApi) {
        if (sInstance==null) {
            sInstance = new InstallMsgRemoteDataSource(aBackendApi);
        }
        return sInstance;
    }
    private InstallMsgRemoteDataSource(@NonNull BackendApi aBackendApi) {
        mBackendApi=aBackendApi;
    }

    @Override
    public Call<ResponseServerMsg> getMsgsFromServer() {
        return mBackendApi.getMsgs("",
                BackendApi.SEARCH_TYPES.ALL.ordinal(),
                Utils.THIS_USER_ID+"");
    }
    //Returns null for moment
    @Override
    public Call<ResponseServerMsgSave> saveMsg(Msg aMsg) {
        return mBackendApi.sendMsg(
                aMsg
        );
    }
}
