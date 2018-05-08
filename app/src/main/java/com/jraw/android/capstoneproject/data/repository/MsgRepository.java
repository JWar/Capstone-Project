package com.jraw.android.capstoneproject.data.repository;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v4.content.CursorLoader;

import com.jraw.android.capstoneproject.data.model.Conversation;
import com.jraw.android.capstoneproject.data.model.Msg;
import com.jraw.android.capstoneproject.data.model.cursorwrappers.ConversationCursorWrapper;
import com.jraw.android.capstoneproject.data.source.local.ConversationLocalDataSource;
import com.jraw.android.capstoneproject.data.source.local.MsgLocalDataSource;
import com.jraw.android.capstoneproject.data.source.remote.MsgRemoteDataSource;
import com.jraw.android.capstoneproject.data.source.remote.ResponseServerMsg;
import com.jraw.android.capstoneproject.data.source.remote.ResponseServerMsgSave;

import java.util.List;

/**
 * Created by JonGaming on 16/04/2018.
 * Handles all msg requirements.
 */
public class MsgRepository {

    private static MsgRepository sInstance = null;
    private MsgLocalDataSource mMsgLocalDataSource;
    private MsgRemoteDataSource mMsgRemoteDataSource;
    private ConversationLocalDataSource mConversationLocalDataSource;

    public static synchronized MsgRepository getInstance(@NonNull MsgLocalDataSource aMsgLocalDataSource,
                                                         @NonNull MsgRemoteDataSource aMsgRemoteDataSource,
                                                         @NonNull ConversationLocalDataSource aConversationLocalDataSource) {
        if (sInstance == null) {
            sInstance = new MsgRepository(aMsgLocalDataSource, aMsgRemoteDataSource, aConversationLocalDataSource);
        }
        return sInstance;
    }

    private MsgRepository(@NonNull MsgLocalDataSource aMsgLocalDataSource,
                          @NonNull MsgRemoteDataSource aMsgRemoteDataSource,
                          @NonNull ConversationLocalDataSource aConversationLocalDataSource) {
        mMsgLocalDataSource = aMsgLocalDataSource;
        mMsgRemoteDataSource = aMsgRemoteDataSource;
        mConversationLocalDataSource = aConversationLocalDataSource;
    }

    public void destroyInstance() {
        sInstance = null;
    }

    //Just returns number of new msgs or -1 if error. Should never be 0... as this is the point of the firebase push...
    public int getNewMsgs(Context aContext) throws Exception {
        int numNewMsgs = -1;
        ResponseServerMsg responseServerMsg = mMsgRemoteDataSource.getMsgsFromServer();
        if (responseServerMsg.action.equals("COMPLETE")) {
            //Save msgs to database
            numNewMsgs = saveMsgs(aContext,responseServerMsg.rows);
            return numNewMsgs;
        } else {
            //Notify user that there has been a problem with getting new msgs. This is indicated by -1.
            return numNewMsgs;
        }
    }
    /**
     * Returns long rather than ResponseServer as the user only cares if the msg isnt saved to local properly.
     * All other results should be reflected in the UI (i.e. if Msg Failed result appears).
     * This is for new messages FROM the user. And therefore doesnt need to update/insert a conversation as it
     * will already be done on creation of New Conversation functionality.
     */
    public long saveMsg(Context aContext, Msg aMsg) {
        ResponseServerMsgSave responseServerMsgSave = mMsgRemoteDataSource.saveMsg(aMsg);
        if (responseServerMsgSave.action.equals("COMPLETE")) {
            //Gets res of save and assigns result in msg.
            aMsg.setMSResult(Msg.RESULTS.valueOf(responseServerMsgSave.res).ordinal());
            return mMsgLocalDataSource.saveMsg(aContext, aMsg);
        } else {
            //TODO: think of how to sort out resending when failed
            aMsg.setMSResult(Msg.RESULTS.FAILED.ordinal());
            return mMsgLocalDataSource.saveMsg(aContext, aMsg);
        }
    }
    /**
     * This is for saving messages received from the server.
     * This will need to handle NEW conversations AND update conversations to reflect changes in their msg lists.
     * So need to check for existence of COPublicId in Conversation List. If not existing then there needs to be
     * a new conversation with the title and Public Id (what about created by?).
     * Will also need to update any conversations with the msg snippet, time, and read status.
     * Sounds like bulk insert is a bad idea... Will have to go through each new msg and do checks/updates/inserts
     * May make more sense to do this in repository... Will this mean msgrepo needs Conversation DS too?
     */
    private int saveMsgs(Context aContext, List<Msg> aMsgList) throws Exception {
        int numNewMsgs = 0;
        //Iterate through msg list. Checking each msg's publicid in conversation list
        for (int i = 0; i < aMsgList.size(); i++) {
            Msg msg = aMsgList.get(i);
            Cursor cursor = mConversationLocalDataSource.getConversationViaPublicId(aContext,msg.getMSCOPublicId());
            if (cursor!=null) {
                if (cursor.getCount() > 0) {//If conversation does exist
                    ConversationCursorWrapper conversationCursorWrapper = new ConversationCursorWrapper(cursor);
                    conversationCursorWrapper.moveToFirst();
                    Conversation conversation = conversationCursorWrapper.getConversation();
                    conversation.setCODateLastMsg(msg.getMSEventDate());
                    conversation.setCOSnippet(msg.getMSBody().substring(0,50));
                    conversation.setCOUnread(conversation.getCOUnread()+1);//+1 to Unread count

                    mConversationLocalDataSource.updateConversation(aContext,conversation);
                    if (mMsgLocalDataSource.saveMsg(aContext,msg)>0) {
                        numNewMsgs+=1;
                    }
                } else {//New conversation.
                    Conversation conversation = new Conversation();
                    conversation.setCOTitle(msg.getMSCOTitle());
                    conversation.setCOPublicId(msg.getMSCOPublicId());
                    conversation.setCODateLastMsg(msg.getMSEventDate());
                    conversation.setCOSnippet(msg.getMSBody().substring(0,50));
                    conversation.setCOUnread(1);//Since new conversation there is 1 unread msg!

                    //To find createdBy can use fromId of Msg and check Persons. Not sure if needed though... so leave for moment
                    mConversationLocalDataSource.saveConversation(aContext,conversation);
                    if (mMsgLocalDataSource.saveMsg(aContext,msg)>0) {
                        numNewMsgs+=1;
                    }
                }
            } else {
                throw new Exception("MsgRepo.saveMsg list cursor == null");
            }
        }
        return numNewMsgs;
    }
    public CursorLoader getMsgs(Context aContext, long aConversationPublicId) {
        return mMsgLocalDataSource.getMsgs(aContext, aConversationPublicId);
    }

    //Not implemented yet but this should allow searching msgs list by the body.
    public CursorLoader getMsgsViaBody(Context aContext, long aConversationPublicId, String aQuery) {
        return null;
    }
}
