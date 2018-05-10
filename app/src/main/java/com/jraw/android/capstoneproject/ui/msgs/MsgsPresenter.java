package com.jraw.android.capstoneproject.ui.msgs;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.CursorLoader;

import com.jraw.android.capstoneproject.data.model.Msg;
import com.jraw.android.capstoneproject.data.repository.MsgRepository;
import com.jraw.android.capstoneproject.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by JonGaming on 17/07/2017.
 * Handles retrieval of msgs in a conversation
 */

public class MsgsPresenter implements MsgsContract.PresenterMsgs {

    private final MsgRepository mMsgRepository;

    private MsgsContract.ViewMsgs mViewMsgs;

    public MsgsPresenter(@NonNull MsgRepository aMsgRepository,
                         @NonNull MsgsContract.ViewMsgs aViewMsgs) {
        mMsgRepository = aMsgRepository;
        mViewMsgs = aViewMsgs;
        mViewMsgs.setPresenter(this);
    }

    @Override
    public CursorLoader getMsgs(Context aContext, long aCoPublicId) {
        return mMsgRepository.getMsgs(aContext,aCoPublicId);
    }

    @Override
    public CursorLoader getMsgsViaBody(Context aContext, long aCoPublicId, String aText) {
        return mMsgRepository.getMsgsViaBody(aContext,aCoPublicId,aText);
    }

    @Override
    public void sendNewMsg(Context aContext, long aCOPublicId, String aCOTitle, String aBody) {
        Msg newMsg = new Msg();
        newMsg.setMSBody(aBody);
        newMsg.setMSCOPublicId(aCOPublicId);
        newMsg.setMSCOTitle(aCOTitle);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss", Locale.ENGLISH);
        newMsg.setMSEventDate(sdf.format(Calendar.getInstance().getTime()));
        newMsg.setMSFromId(Utils.THIS_USER_ID);
        newMsg.setMSType(Msg.MSG_TYPES.TEXT.ordinal());//Assuming all are text messages for now.
        //What to do now? Intent Service call? Do it in Repository as Repository is an app wide object whereas Presenter isnt.
        mMsgRepository.saveMsg(aContext,newMsg);
    }
}
