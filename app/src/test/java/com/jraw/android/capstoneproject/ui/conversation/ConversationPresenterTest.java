package com.jraw.android.capstoneproject.ui.conversation;

import com.jraw.android.capstoneproject.data.repository.ConversationRepository;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.verify;

//Due to Loaders... cant test anything more in test?
public class ConversationPresenterTest {
    @Mock
    private ConversationRepository mConversationRepository;

    @Mock
    private ConversationContract.ViewConversations mViewConversations;

    @Mock
    private ConversationContract.ActivityConversation mActivityConversation;

    private ConversationPresenter mConversationPresenter;
    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }
    @Test
    public void setPresenterToView() {
        mConversationPresenter = new ConversationPresenter(mConversationRepository,
                mViewConversations,
                mActivityConversation);
        verify(mViewConversations).setPresenter(mConversationPresenter);
    }
}
