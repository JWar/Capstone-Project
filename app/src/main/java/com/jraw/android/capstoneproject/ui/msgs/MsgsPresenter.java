package com.jraw.android.capstoneproject.ui.msgs;

import android.support.annotation.NonNull;

import com.jraw.android.capstoneproject.data.repository.MsgRepository;

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
    public void getMsgs(int aCOId) {

    }

    @Override
    public void getMsgsViaBody(int aCOId,
                               String aText) {

    }
}
