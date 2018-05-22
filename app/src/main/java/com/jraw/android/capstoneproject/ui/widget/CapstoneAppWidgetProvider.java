package com.jraw.android.capstoneproject.ui.widget;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.jraw.android.capstoneproject.R;
import com.jraw.android.capstoneproject.data.model.Conversation;
import com.jraw.android.capstoneproject.ui.msgs.MsgsActivity;

public class CapstoneAppWidgetProvider extends AppWidgetProvider {

    private static final String FIRST_CONV_PUBLIC_ID = "firstCOPubId";
    private static final String SECOND_CONV_PUBLIC_ID = "secondCOPubId";

    public static void updateWidgetConversations(
            Context context,
            AppWidgetManager appWidgetManager,
            int appWidgetId,
            Conversation[] aConversations) {
        Conversation firstConversation = aConversations[0];
        Conversation secondConversation = aConversations[1];

        Intent firstConvIntent = new Intent(context, MsgsActivity.class);
        firstConvIntent.putExtra(FIRST_CONV_PUBLIC_ID,firstConversation.getCOPublicId());
        Intent secondConvIntent = new Intent(context, MsgsActivity.class);
        secondConvIntent.putExtra(SECOND_CONV_PUBLIC_ID,secondConversation.getCOPublicId());

        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.widget);

        views.setTextViewText(R.id.widget_first_conv_title_tv, firstConversation.getCOTitle());
        views.setTextViewText(R.id.widget_first_conv_snippet_tv, firstConversation.getCOSnippet());

        views.setTextViewText(R.id.widget_second_conv_title_tv, secondConversation.getCOTitle());
        views.setTextViewText(R.id.widget_second_conv_snippet_tv, secondConversation.getCOSnippet());

        views.setOnClickPendingIntent(R.id.widget_first_conv_rl, firstConvIntent);
        views.setOnClickPendingIntent(R.id.widget_second_conv_tv, secondConvIntent);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }


    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        //TODO: Set default widget text...

    }

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }
}
