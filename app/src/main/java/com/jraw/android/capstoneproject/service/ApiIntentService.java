package com.jraw.android.capstoneproject.service;

import android.app.IntentService;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.Context;
import android.database.Cursor;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.widget.Toast;

import com.jraw.android.capstoneproject.R;
import com.jraw.android.capstoneproject.data.model.Conversation;
import com.jraw.android.capstoneproject.data.model.Msg;
import com.jraw.android.capstoneproject.data.model.cursorwrappers.ConversationCursorWrapper;
import com.jraw.android.capstoneproject.data.model.cursorwrappers.PersonCursorWrapper;
import com.jraw.android.capstoneproject.data.repository.ConversationRepository;
import com.jraw.android.capstoneproject.data.repository.MsgRepository;
import com.jraw.android.capstoneproject.data.repository.PeCoRepository;
import com.jraw.android.capstoneproject.data.repository.PersonRepository;
import com.jraw.android.capstoneproject.database.DbSchema;
import com.jraw.android.capstoneproject.ui.msgs.MsgsActivity;
import com.jraw.android.capstoneproject.ui.widget.CapstoneAppWidgetProvider;
import com.jraw.android.capstoneproject.utils.Utils;
import com.jwar.android.capstoneproject.Injection;

/**
 * Handles getting and saving msgs via Retrofit.
 * Also handles notifications.
 * Also handles Widget via static method in provider
 */
public class ApiIntentService extends IntentService {

    private static final String ACTION_GET_NEW_MSGS = "com.jraw.android.capstoneproject.service.action.GET_NEW_MSGS";
    private static final String ACTION_SEND_NEW_MSG = "com.jraw.android.capstoneproject.service.action.SEND_NEW_MSG";

    private static final String EXTRA_MSG = "com.jraw.android.capstoneproject.service.extra.msg";

    //Urgh dont like this at all. Storing static field in IntentService sounds like a bad idea.
    private static MsgRepository sMsgRepository;
    private static ConversationRepository sConversationRepository;
    private static PersonRepository sPersonRepository;
    private static PeCoRepository sPeCoRepository;

    private int mNotificationId=1;

    public ApiIntentService() {
        super("ApiIntentService");
    }

    /**
     * Triggers app to get all msgs stored in server for this phone.
     */
    public static void startActionGetNewMsgs(Context context) {
        Intent intent = new Intent(context, ApiIntentService.class);
        intent.setAction(ACTION_GET_NEW_MSGS);
        context.startService(intent);
    }

    /**
     * Starts this service to perform send new msg. Requires Msg to be sent, strangely enough...
     */
    public static void startActionSendNewMsgs(Context context, Msg aMsg) {
        Intent intent = new Intent(context, ApiIntentService.class);
        intent.setAction(ACTION_SEND_NEW_MSG);
        intent.putExtra(EXTRA_MSG, aMsg);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            if (sMsgRepository==null) {
                try {//Init Repo if null
                    sMsgRepository = Injection.provideMsgRepository(
                            Injection.provideMsgLocalDataSource(),
                            Injection.provideMsgRemoteDataSource(
                                    Injection.provideBackendApi()),
                            Injection.provideConversationLocalDataSource());
                } catch (Exception e) {
                    Utils.logDebug("Problem in ApiIntentService.onHandleIntent: MsgRepo init");
                    showToastMsg("Problem initialising Messages");
                    return;
                }
            }
            if (sConversationRepository==null) {
                try {//Init Repo if null
                    sConversationRepository = Injection.provideConversationRepository(
                            Injection.provideConversationLocalDataSource());
                } catch (Exception e) {
                    Utils.logDebug("Problem in ApiIntentService.onHandleIntent: ConvRepo init");
                    showToastMsg("Problem initialising Conversations");
                    return;
                }
            }
            if (sPersonRepository==null) {
                try {//Init Repo if null
                    sPersonRepository = Injection.providePersonRepository(
                            Injection.providePersonLocalDataSource(),
                            Injection.providePersonRemoteDataSource(
                                    Injection.provideBackendApi()
                            ));
                } catch (Exception e) {
                    Utils.logDebug("Problem in ApiIntentService.onHandleIntent: PersonRepo init");
                    showToastMsg("Problem initialising Persons");
                    return;
                }
            }
            if (sPeCoRepository==null) {
                try {//Init Repo if null
                    sPeCoRepository = Injection.providePeCoRepository(
                            Injection.providePeCoLocalDataSource()
                    );
                } catch (Exception e) {
                    Utils.logDebug("Problem in ApiIntentService.onHandleIntent: PeCoRepo init");
                    showToastMsg("Problem initialising references");
                    return;
                }
            }
            final String action = intent.getAction();
            if (ACTION_GET_NEW_MSGS.equals(action)) {
                handleActionGetNewMsgs();
            } else if (ACTION_SEND_NEW_MSG.equals(action)) {
                Msg newMsg = intent.getParcelableExtra(EXTRA_MSG);
                handleActionSendNewMsg(newMsg);
            }
        }
    }

    /**
     * Handle action get new msgs to check server for all messages pending for this phone
     */
    private void handleActionGetNewMsgs() {
        try {
            int numNewMsgs = sMsgRepository.getNewMsgs(this);
            if (numNewMsgs>-1) {//Not sure in what case it could be 0 but hey...
                handleNotifications();
                //Will widget need upgrading if numNewMsgs 0? Dont think it can ever be though.
                updateWidgetConversations();
                Utils.logDebug("ApiIntentService.handleActionGetNewMsgs: saved " + numNewMsgs + " from server!");
            } else {
                throw new Exception(getString(R.string.problem_getting_new_msg));
            }
        } catch (Exception e) {
            Utils.logDebug("ApiIntentService.handleActionGetNewMsg: "+e.getLocalizedMessage());
            showToastMsg(getString(R.string.problem_getting_new_msg));
        }
    }
    private void updateWidgetConversations() {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, CapstoneAppWidgetProvider.class));
        Conversation[] conversations = sConversationRepository.getConversationsTopTwo(this);
        for (int appWidgetId: appWidgetIds) {
            CapstoneAppWidgetProvider.updateWidgetConversations(this, appWidgetManager, appWidgetId,
                    conversations);
        }
    }
    //Need to check presence of already set notifications.
    //Need to update notifications on read. Need to therefore store public id against the
    //Think going to re write this to just do a query of the conversation table for unreads.
    //So dont need to faff about with anything in app memory, can just rely on database. Better way of
    //structuring it anyway.
    //Tad inefficient to get all unread convs and not really use them. But this allows relatively easy
    //adding of new features!
    private void handleNotifications() {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
        NotificationCompat.Builder mBuilder;
        ConversationCursorWrapper unreadConvsCursor = new ConversationCursorWrapper(
                sConversationRepository.getAllUnreadConversations(this));
        try {
            int curCount = unreadConvsCursor.getCount();
            switch (curCount) {
                case 1:
                    unreadConvsCursor.moveToPosition(0);
                    Conversation conv = unreadConvsCursor.getConversation();
                    PendingIntent pendingIntent = PendingIntent.getActivity(
                            this,
                            0,
                            MsgsActivity.getIntent(this, conv.getCOPublicId(), conv.getCOTitle()),
                            0);
                    mBuilder = new NotificationCompat.Builder(this, Utils.CHANNEL_ID)
                            .setSmallIcon(R.drawable.ic_baseline_message_24px)
                            .setContentTitle(conv.getCOTitle())
                            .setContentText(conv.getCOSnippet())
                            .setAutoCancel(true)
                            .setContentIntent(pendingIntent)
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                    notificationManager.notify(mNotificationId, mBuilder.build());
                    break;
                case 0:
                    //If no unread msgs then notification not needed.
                    notificationManager.cancel(mNotificationId);
                    break;
                default:
                    //Nothing fancy if more than one. This can be extended
                    String toDisplay = curCount + " " + getString(R.string.notification_unread_msgs);
                    mBuilder = new NotificationCompat.Builder(this, Utils.CHANNEL_ID)
                            .setSmallIcon(R.drawable.ic_baseline_message_24px)
                            .setContentText(toDisplay)
                            .setAutoCancel(true)
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT);
                    notificationManager.notify(mNotificationId, mBuilder.build());
            }
        } catch (Exception e) {
            Utils.logDebug("ApiIntentService.handleNotifications: "+e.getLocalizedMessage());
            showToastMsg("Problem with notifications");
        } finally {
            unreadConvsCursor.close();
        }
    }
    /**
     * Handle action SendNewMsg in the provided background thread with the provided
     * parameters. Uses Retrofit to send Msg.
     */
    private void handleActionSendNewMsg(Msg aMsg) {
        Cursor pesInCoCursor=null;
        PersonCursorWrapper personsCursor = null;
        try {
            //Will need to de ref all persons in conversation, get their tels and add them to msgs toTels.
            //This is all person ids in conversation
            pesInCoCursor = sPeCoRepository.getPesInCo(this,aMsg.getMSCOPublicId());
            //This is all persons in list. Get persons, get tels from person
            int len = pesInCoCursor.getCount();
            String[] peIdList = new String[len];
            for (int i = 0;i<len;i++) {
                pesInCoCursor.moveToPosition(i);
                peIdList[i] = "" +
                        pesInCoCursor.getInt(pesInCoCursor.getColumnIndex(DbSchema.PeCoTable.Cols.PEID));
            }
            personsCursor = new PersonCursorWrapper(sPersonRepository.getPersonsFromPeIds(this,peIdList));
            StringBuilder toTels = new StringBuilder();
            for (int i =0;i<personsCursor.getCount();i++) {
                personsCursor.moveToPosition(i);
                String concat = personsCursor.getPerson()
                        .getPETelNum() + ",";
                toTels.append(concat);
            }
            //Make sure to cut off the trailing ,.
            aMsg.setMSToTels(toTels.toString().substring(0,toTels.length()-1));
            if (sMsgRepository.saveMsg(this, aMsg)>0) {
                Utils.logDebug("ApiIntentService.handleActionSendMsg: Msg sent successfully. Msg Id: "+aMsg.getId());
            } else {
                showToastMsg(getString(R.string.send_msg_error));
            }
        } catch (Exception e) {
            Utils.logDebug("ApiIntentService.handleActionSendNewMsg: "+e.getLocalizedMessage());
            showToastMsg(getString(R.string.send_msg_error));
        } finally {
            Utils.closeCursor(pesInCoCursor);
            Utils.closeCursor(personsCursor);
        }
    }
    private void showToastMsg(final String aText) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), aText, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
