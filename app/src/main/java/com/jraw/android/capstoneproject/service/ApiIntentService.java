package com.jraw.android.capstoneproject.service;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.jraw.android.capstoneproject.data.model.Conversation;
import com.jraw.android.capstoneproject.data.model.Msg;
import com.jraw.android.capstoneproject.data.model.Person;
import com.jraw.android.capstoneproject.data.repository.ConversationRepository;
import com.jraw.android.capstoneproject.data.repository.MsgRepository;
import com.jraw.android.capstoneproject.data.repository.PersonRepository;
import com.jraw.android.capstoneproject.ui.widget.CapstoneAppWidgetProvider;
import com.jraw.android.capstoneproject.utils.Utils;
import com.jwar.android.capstoneproject.Injection;

import java.util.List;

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
            List<Msg> newMsgs = sMsgRepository.getNewMsgs(this);
            if (newMsgs!=null) {
                //Successfully save this number of new msgs. Just debug, no need to let user know.
                //TODO:This will need to update/add notifications AND update widget. Will need msgs for notifs?
                //So getNewMsgs cant just return the num of msgs, will need to return msg list...
                handleNotifications(newMsgs);
                updateWidgetConversations();
                Utils.logDebug("ApiIntentService.handleActionGetNewMsgs: saved " + newMsgs.size() + " from server!");
            } else {
                //Notify user that there has been a problem with getting new msgs.
                showToastMsg("Problem getting new msgs");
            }
        } catch (Exception e) {
            Utils.logDebug("ApiIntentService.handleActionSendNewMsg: "+e.getLocalizedMessage());
            showToastMsg("Problem getting new msgs");
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
    private void handleNotifications(List<Msg> aMsgList) {
        switch (aMsgList.size()) {
            case 1:
                Msg msg = aMsgList.get(0);
                Person person = sPersonRepository.getPerson(this,msg.getMSFromTel());

                msg.getBodySnippet();
                break;
            case 0://Shouldnt have a msg list of 0...
                break;
            default:
                //Set notification title as size of list + notification new msg string
                String toDisplay = aMsgList.size()+ " " + getString(R.string.notification_unread_msgs);
        }
    }
    /**
     * Handle action SendNewMsg in the provided background thread with the provided
     * parameters. Uses Retrofit to send Msg.
     */
    private void handleActionSendNewMsg(Msg aMsg) {
        try {
            if (sMsgRepository.saveMsg(this, aMsg)>0) {
                Utils.logDebug("ApiIntentService.handleActionSendMsg: Msg sent successfully. Msg Id: "+aMsg.getId());
            } else {
                showToastMsg("Problem saving msg");
            }
        } catch (Exception e) {
            Utils.logDebug("ApiIntentService.handleActionSendNewMsg: "+e.getLocalizedMessage());
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
