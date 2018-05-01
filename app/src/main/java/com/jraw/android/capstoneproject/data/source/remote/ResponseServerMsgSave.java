package com.jraw.android.capstoneproject.data.source.remote;

//For getting result of msg being sent to server. Result can be failed to send, sent, delivered. Adding a 'Read' state
//requires more complications than I can be bothered with, but it should be a call to server when the conversation is
//accessed in the other users phone, that then updates all users with a read flag.
public class ResponseServerMsgSave extends ResponseServer {
    //Returns result of save
    public String res;
    public ResponseServerMsgSave() {}
}
