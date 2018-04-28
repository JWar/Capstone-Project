package com.jraw.android.capstoneproject.data.source.local;

import android.content.Context;
import android.support.v4.content.CursorLoader;

public interface ConversationLocalDataSource {
    CursorLoader getConversations(Context aContext);
    CursorLoader getConversationsViaTitle(Context aContext, String aTitle);
}
