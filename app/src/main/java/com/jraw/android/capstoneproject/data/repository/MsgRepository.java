package com.jraw.android.capstoneproject.data.repository;

import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v4.content.CursorLoader;
import android.telephony.SmsManager;

import com.jraw.android.capstoneproject.data.model.Conversation;
import com.jraw.android.capstoneproject.data.model.Msg;
import com.jraw.android.capstoneproject.data.model.cursorwrappers.ConversationCursorWrapper;
import com.jraw.android.capstoneproject.data.source.local.ConversationLocalDataSource;
import com.jraw.android.capstoneproject.data.source.local.MsgLocalDataSource;
import com.jraw.android.capstoneproject.data.source.remote.MsgRemoteDataSource;
import com.jraw.android.capstoneproject.data.source.remote.ResponseServerMsg;
import com.jraw.android.capstoneproject.data.source.remote.ResponseServerMsgSave;
import com.jraw.android.capstoneproject.ui.install.InstallFragment;
import com.jraw.android.capstoneproject.utils.Utils;

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

    //Needs to return received msgs for notifications and snippets.
    public int getNewMsgs(Context aContext) throws Exception {
        SharedPreferences sharedPreferences = aContext.getSharedPreferences(Utils.SHAR_PREFS,0);
        String usersTel = sharedPreferences.getString(InstallFragment.TEL_NUM,null);
        if (usersTel!=null) {
            ResponseServerMsg responseServerMsg = mMsgRemoteDataSource.getMsgsFromServer(usersTel);
            if (responseServerMsg.action.equals("COMPLETE")) {
                //Save msgs to database
                return saveMsgs(aContext, responseServerMsg.rows);
            } else {
                //Notify user that there has been a problem with getting new msgs. This is indicated by null.
                return -1;
            }
        } else {
            throw new Exception("usersTel==null");
        }
    }

    /**
     * Returns long rather than ResponseServer as the user only cares if the msg isnt saved to local properly.
     * All other results should be reflected in the UI (i.e. if Msg Failed result appears).
     * This is for new messages FROM the user. And therefore doesnt need to update/insert a conversation as it
     * will already be done on creation of New Conversation functionality.
     */
    public long saveMsg(Context aContext, Msg aMsg) throws Exception {
        try {
            ResponseServerMsgSave responseServerMsgSave = mMsgRemoteDataSource.saveMsg(aMsg);
            if (responseServerMsgSave.action.equals("COMPLETE")) {
                //Gets res of save and assigns result in msg.
                aMsg.setMSResult(Msg.RESULTS.valueOf(responseServerMsgSave.res).ordinal());
                return mMsgLocalDataSource.saveMsg(aContext, aMsg);
            } else {

                //Resorts to sms if failure!
                sendSMS(aMsg.getMSFromTel(), aMsg.getMSBody());
                aMsg.setMSResult(Msg.RESULTS.FAILED.ordinal());
                return mMsgLocalDataSource.saveMsg(aContext, aMsg);
            }
        } catch (Exception e) {
            sendSMS(aMsg.getMSFromTel(), aMsg.getMSBody());
            aMsg.setMSResult(Msg.RESULTS.FAILED.ordinal());
            return mMsgLocalDataSource.saveMsg(aContext, aMsg);
        }
    }

    private void sendSMS(String aTelNum, String aMsgBody) throws Exception {
        SmsManager smsManager = SmsManager.getDefault();
        smsManager.sendTextMessage(aTelNum, null, aMsgBody, null, null);
    }

    /**
     * This is for saving messages received from the server.
     * This will need to handle NEW conversations AND update conversations to reflect changes in their msg lists.
     * So need to check for existence of COPublicId in Conversation List. If not existing then there needs to be
     * a new conversation with the title and Public Id (what about created by?).
     * Will also need to update any conversations with the msg snippet, time, and read status.
     * Sounds like bulk insert is a bad idea... Will have to go through each new msg and do checks/updates/inserts
     * May make more sense to do this in repository... Will this mean msgrepo needs Conversation DS too?
     * Updated to return list of msgs successfully saved. For notifications/widget handling in IntentService
     */
    private int saveMsgs(Context aContext, List<Msg> aMsgList) throws Exception {
        int numNewMsgs=0;
        //Iterate through msg list. Checking each msg's publicid in conversation list
        for (int i = 0; i < aMsgList.size(); i++) {
            Msg msg = aMsgList.get(i);
            Cursor cursor = mConversationLocalDataSource.getConversationViaPublicId(aContext, msg.getMSCOPublicId());
            if (cursor != null) {
                if (cursor.getCount() > 0) {//If conversation does exist
                    ConversationCursorWrapper conversationCursorWrapper = new ConversationCursorWrapper(cursor);
                    conversationCursorWrapper.moveToFirst();
                    Conversation conversation = conversationCursorWrapper.getConversation();
                    conversation.setCODateLastMsg(msg.getMSEventDate());
                    conversation.setCOSnippet(msg.getBodySnippet());
                    conversation.setCOUnread(conversation.getCOUnread() + 1);//+1 to Unread count
                    conversation.setCOCount(conversation.getCOCount() + 1);//Update count by one

                    mConversationLocalDataSource.updateConversation(aContext, conversation);
                    if (mMsgLocalDataSource.saveMsg(aContext, msg) > 0) {
                        numNewMsgs+=1;
                    }
                } else {//New conversation.
                    Conversation conversation = new Conversation();
                    conversation.setCOTitle(msg.getMSCOTitle());
                    conversation.setCOPublicId(msg.getMSCOPublicId());
                    conversation.setCODateLastMsg(msg.getMSEventDate());
                    conversation.setCOSnippet(msg.getBodySnippet());
                    conversation.setCOUnread(1);//Since new conversation there is 1 unread msg!
                    conversation.setCOCount(1);//Since new conversation there is 1 msg!

                    //To find createdBy can use fromId of Msg and check Persons. Not sure if needed though... so leave for moment

                    mConversationLocalDataSource.saveConversation(aContext, conversation);
                    if (mMsgLocalDataSource.saveMsg(aContext, msg) > 0) {
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

    //Not implemented msg delete yet. Will need to also trigger conversation count change.
    public void deleteMsg(Context aContext, Msg aMsg) {
        try {
            if (mMsgLocalDataSource.deleteMsg(aMsg) > 0) {
                ConversationCursorWrapper conversationCursorWrapper = new ConversationCursorWrapper(
                        mConversationLocalDataSource.getConversationViaPublicId(aContext, aMsg.getMSCOPublicId()));
                conversationCursorWrapper.moveToFirst();
                Conversation conversation = conversationCursorWrapper.getConversation();
                conversationCursorWrapper.close();
                conversation.setCOCount(conversation.getCOCount() - 1);
                mConversationLocalDataSource.updateConversation(aContext, conversation);
            }
        } catch (Exception e) {
            Utils.logDebug("MsgRepository.deleteMsg: "+e.getLocalizedMessage());
        }
    }
}
