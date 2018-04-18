package com.jraw.android.capstoneproject.data.source.local;

import com.jraw.android.capstoneproject.data.model.Conversation;

import java.util.List;

public interface ConversationLocalDataSource {
    List<Conversation> getConversations();
    List<Conversation> getConversationsViaTitle(String aTitle);
}
