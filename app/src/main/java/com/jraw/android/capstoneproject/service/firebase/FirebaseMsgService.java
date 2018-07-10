package com.jraw.android.capstoneproject.service.firebase;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.jraw.android.capstoneproject.R;
import com.jraw.android.capstoneproject.service.ApiIntentService;
import com.jraw.android.capstoneproject.utils.Utils;

/**
 * Created by JonGaming on 27/06/2017.
 * Hmm design question: I reckon best thing is to send Msg to an intent service? or to put it another way:
 * is there a problem doing it with MsgRepository directly?
 */

public class FirebaseMsgService extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        //180117_Cant remember where the new Msg is put... I think in data but going to check for both that and
        //notification.
        //Not sure if going to run into problems with simply saving the msg to database but meh.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Utils.logDebug("From: " + remoteMessage.getFrom());
        Utils.logDebug("Type: " + remoteMessage.getMessageType());
        //For simplicity sake. Only need to run intent service to query server if a msg has been received.
        //Of course can expand to do more with push. But for the moment its just a trigger for an update
        //query
        ApiIntentService.startActionGetNewMsgs(this);
    }
}
