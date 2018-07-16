package com.jraw.android.capstoneproject.ui.msgs;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.CursorLoader;
import com.jraw.android.capstoneproject.data.model.Msg;
import com.jraw.android.capstoneproject.data.repository.MsgRepository;
import com.jraw.android.capstoneproject.service.ApiIntentService;
import com.jraw.android.capstoneproject.ui.install.InstallFragment;
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
        try {
            Msg newMsg = new Msg();
            newMsg.setMSBody(aBody);
            newMsg.setMSCOPublicId(aCOPublicId);
            newMsg.setMSCOTitle(aCOTitle);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss", Locale.UK);
            newMsg.setMSEventDate(sdf.format(Calendar.getInstance().getTime()));
            String usersTel = aContext
                    .getSharedPreferences(Utils.SHAR_PREFS,0).getString(InstallFragment.TEL_NUM,null);
            if (usersTel!=null) {
                newMsg.setMSFromTel(usersTel);
                newMsg.setMSType(Msg.MSG_TYPES.TEXT.ordinal());//Assuming all are text messages for now.
                //Done async via intentservice!
                ApiIntentService.startActionSendNewMsgs(aContext, newMsg);
                mViewMsgs.sentNewMsg();
            } else {
                throw new Exception("usersTel==null");
            }
        } catch (Exception e) {
            Utils.logDebug("MsgsPresenter.sendNewMsg: "+e.getLocalizedMessage());
            mViewMsgs.problemSendingMsg();
        }
    }
}
