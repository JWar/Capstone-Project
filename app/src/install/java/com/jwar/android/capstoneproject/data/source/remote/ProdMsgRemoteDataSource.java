package com.jwar.android.capstoneproject.data.source.remote;

import android.support.annotation.NonNull;

import com.jraw.android.capstoneproject.data.model.Msg;
import com.jraw.android.capstoneproject.data.source.remote.BackendApi;
import com.jraw.android.capstoneproject.data.source.remote.MsgRemoteDataSource;
import com.jraw.android.capstoneproject.data.source.remote.ResponseServerMsg;

import java.util.List;

/**
 * Created by JonGaming on 16/04/2018.
 */

public class ProdMsgRemoteDataSource implements MsgRemoteDataSource {
    private static ProdMsgRemoteDataSource sInstance=null;
    private BackendApi mBackendApi;
    public static ProdMsgRemoteDataSource getInstance(@NonNull BackendApi aBackendApi) {
        if (sInstance==null) {
            sInstance = new ProdMsgRemoteDataSource(aBackendApi);
        }
        return sInstance;
    }
    private ProdMsgRemoteDataSource(@NonNull BackendApi aBackendApi) {
        mBackendApi=aBackendApi;
    }

    @Override
    public ResponseServerMsg getMsgsFromServer() {
        return mBackendApi.getMsgs("",
                BackendApi.SEARCH_TYPES.ALL.ordinal(),
                Utils.THIS_USER_ID+"");
    }
    //Returns null for moment
    @Override
    public ResponseServerMsgSave saveMsg(Msg aMsg) {

        return null;
    }
}
