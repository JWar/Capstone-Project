package com.jraw.android.capstoneproject.data.source.remote;

public interface MsgRemoteDataSource {
    //Gets all messages from server for this user
    ResponseServerMsg getMsgsFromServer();
}
