package com.jraw.android.capstoneproject.ui.conversation;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.CursorLoader;

public interface ConversationContract {
    interface ViewConversations {
        //Sets ListHandler to use this list.
        void setConversations(Cursor aList);
        void setPresenter(PresenterConversations aPresenter);
    }
    interface PresenterConversations {
        //I hope this is fairly self explanatory. Call by View.
        CursorLoader getConversations(Context aContext);
        //Used in Search Query to filter Conversations with a particular title.
        CursorLoader getConversationsViaTitle(Context aContext, String aTitle);
        void onNewConversation();//New conv button.
        void onNewContact();//Brings up new contact fragment
    }
    interface ActivityConversation {
        void onNewConversation();//Response for new conv button
        void onNewContact();
    }
}