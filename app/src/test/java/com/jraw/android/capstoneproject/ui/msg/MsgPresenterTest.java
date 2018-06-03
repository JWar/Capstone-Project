package com.jraw.android.capstoneproject.ui.msg;

import com.jraw.android.capstoneproject.data.repository.MsgRepository;
import com.jraw.android.capstoneproject.ui.msgs.MsgsContract;
import com.jraw.android.capstoneproject.ui.msgs.MsgsPresenter;

import org.junit.Test;
import org.mockito.Mock;

import static org.mockito.Mockito.verify;

public class MsgPresenterTest {
    @Mock
    private MsgRepository mMsgRepository;

    @Mock
    private MsgsContract.ViewMsgs mViewMsgs;


    private MsgsPresenter mMsgsPresenter;

    @Test
    public void setPresenterToView() {
        mMsgsPresenter = new MsgsPresenter(mMsgRepository,
                mViewMsgs);
        verify(mViewMsgs).setPresenter(mMsgsPresenter);
    }
}
