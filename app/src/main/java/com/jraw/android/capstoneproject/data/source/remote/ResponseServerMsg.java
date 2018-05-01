package com.jraw.android.capstoneproject.data.source.remote;

import com.jraw.android.capstoneproject.data.model.Msg;

import java.util.List;

/**
 * Gets response from server. Data tends to be in rows key.
 * Just gets the msgs waiting to be collected by the users phone.
 */
public class ResponseServerMsg extends ResponseServer {
    public List<Msg> rows;
    public ResponseServerMsg(){}
}
