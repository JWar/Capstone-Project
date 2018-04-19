package com.jraw.android.capstoneproject.ui.conversation;

import com.jraw.android.capstoneproject.data.model.Conversation;

import java.util.List;

public interface ConversationContract {
    interface ViewConversations {
        //Sets ListHandler to use this list.
        void setConversations(List<Conversation> aList);
        void setPresenter(PresenterConversations aPresenter);
    }
    interface PresenterConversations {
        //I hope this is fairly self explanatory. Call by View.
        void getConversations();
        //Used in Search Query to filter Conversations with a particular title.
        void getConversationsViaTitle(String aTitle);
    }
}