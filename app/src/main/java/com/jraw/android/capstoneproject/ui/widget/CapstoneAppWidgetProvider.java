package com.jraw.android.capstoneproject.ui.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.jraw.android.capstoneproject.R;
import com.jraw.android.capstoneproject.data.model.Conversation;
import com.jraw.android.capstoneproject.ui.conversation.ConversationActivity;
import com.jraw.android.capstoneproject.ui.msgs.MsgsActivity;

public class CapstoneAppWidgetProvider extends AppWidgetProvider {

    private static final String FIRST_CONV_PUBLIC_ID = "firstCOPubId";
    private static final String SECOND_CONV_PUBLIC_ID = "secondCOPubId";

    public static void updateWidgetConversations(
            Context context,
            AppWidgetManager appWidgetManager,
            int appWidgetId,
            Conversation[] aConversations) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);
        Conversation firstConversation = aConversations[0];
        Conversation secondConversation = aConversations[1];
        PendingIntent pendingIntent;
        if (firstConversation != null) {
            Intent firstConvIntent = MsgsActivity.getIntent(context,
                    firstConversation.getCOPublicId(),
                    firstConversation.getCOTitle(),
                    true);
            pendingIntent = PendingIntent.getActivity(context, 0, firstConvIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            views.setTextViewText(R.id.widget_first_conv_title_tv, firstConversation.getCOTitle());
            views.setTextViewText(R.id.widget_first_conv_snippet_tv, firstConversation.getCOSnippet());

            views.setOnClickPendingIntent(R.id.widget_first_conv_rl, pendingIntent);

            if (secondConversation != null) {
                Intent secondConvIntent = MsgsActivity.getIntent(context,
                        secondConversation.getCOPublicId(),
                        secondConversation.getCOTitle(),
                        true);
                pendingIntent = PendingIntent.getActivity(context, 0, secondConvIntent, PendingIntent.FLAG_UPDATE_CURRENT);

                views.setTextViewText(R.id.widget_second_conv_title_tv, secondConversation.getCOTitle());
                views.setTextViewText(R.id.widget_second_conv_snippet_tv, secondConversation.getCOSnippet());

                views.setOnClickPendingIntent(R.id.widget_second_conv_rl, pendingIntent);
            }
        } else {
            //Only if first conv null do we need default widget string.
            views.setTextViewText(R.id.widget_first_conv_title_tv, context.getString(R.string.widget_default_title));
            //Set widget press to open ConversationActivity
            Intent nullConvIntent = ConversationActivity.getIntent(context, true);
            pendingIntent = PendingIntent.getActivity(context, 0, nullConvIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            views.setOnClickPendingIntent(R.id.widget_first_conv_rl, pendingIntent);
            views.setOnClickPendingIntent(R.id.widget_second_conv_rl, pendingIntent);
        }
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        //System will handle nulls.
        Conversation[] nullConvs = new Conversation[2];
        for (int appWidgetId : appWidgetIds) {
            updateWidgetConversations(context, appWidgetManager, appWidgetId, nullConvs);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }
}
