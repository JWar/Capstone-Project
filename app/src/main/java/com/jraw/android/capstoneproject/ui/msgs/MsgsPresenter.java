package com.jraw.android.capstoneproject.ui.msgs;

import android.support.annotation.NonNull;

import com.jraw.android.capstoneproject.data.model.Msg;
import com.jraw.android.capstoneproject.data.repository.MsgRepository;

import java.util.List;

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
    public List<Msg> getMsgs(int aCOId) {
        return mMsgRepository.getMsgs(aCOId);
    }

    //Not implemented yet
    @Override
    public List<Msg> getMsgsViaBody(int aCOId,
                               String aText) {
//        return mMsgRepository.getMsgs()
        return null;
    }
}
