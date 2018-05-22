package com.jraw.android.capstoneproject.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.jraw.android.capstoneproject.data.model.Msg;
import com.jraw.android.capstoneproject.data.repository.MsgRepository;
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
