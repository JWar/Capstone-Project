package com.jraw.android.capstoneproject.ui.conversation;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.jraw.android.capstoneproject.R;
import com.jraw.android.capstoneproject.ui.install.InstallContract;
import com.jraw.android.capstoneproject.ui.install.InstallFragment;
import com.jraw.android.capstoneproject.ui.install.InstallPresenter;
import com.jraw.android.capstoneproject.ui.msgs.MsgsActivity;
import com.jraw.android.capstoneproject.ui.newconversation.NewConversationContract;
import com.jraw.android.capstoneproject.ui.newconversation.NewConversationFragment;
import com.jraw.android.capstoneproject.ui.newconversation.NewConversationPresenter;
import com.jraw.android.capstoneproject.utils.Utils;
import com.jwar.android.capstoneproject.Injection;

import static com.jraw.android.capstoneproject.utils.Utils.CHANNEL_ID;
import static com.jraw.android.capstoneproject.utils.Utils.SHAR_PREFS;

/**
 * Todos will be here.
 * TODO: general todo list. The presence of this means there is still things todo!
 * Notifications - handled in intent service. Simple case of msg snippet? Will need to update same
 *  notification with multiple messages, dont need snippet for that though.
 *  Notifications will need a way of recording the conversations that are in there, to ensure when theyre
 *  read the notification is removed. Basically need to set up a communication between the act of reading a
 *  conversation and its unread notification.
 * Analytics - get it set up.
 *
 * Msg delete will need to keep conversation field updated.
 * Test - push/firebase needs testing. Rig up a mock run through with rcving/sending a msg or two.
 *
 */
public class ConversationActivity extends AppCompatActivity implements
        ConversationContract.ActivityConversation,
        InstallContract.ActivityInstall,
        NewConversationContract.ActivityNewConversation {

    private static final String IS_INSTALLED = "isInstalled";

    private ConversationPresenter mConversationPresenter;
    private InstallPresenter mInstallPresenter;
    private NewConversationPresenter mNewConversationPresenter;

    public static void start(Context aContext) {
        Intent intent = new Intent(aContext, ConversationActivity.class);
        aContext.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        Toolbar toolbar = findViewById(R.id.conversation_toolbar);
        setSupportActionBar(toolbar);
        try {
            SharedPreferences sharedPreferences = getSharedPreferences(SHAR_PREFS,0);
            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.conversation_fragment_container);
            if (fragment == null) {//If null then check for install flag.
                if (sharedPreferences.getBoolean(IS_INSTALLED,false)) {//Is installed
                    ConversationFragment conversationFragment = new ConversationFragment();
                    getSupportFragmentManager()
                            .beginTransaction()
                            .addToBackStack(ConversationFragment.TAG)
                            .add(R.id.conversation_fragment_container, conversationFragment, ConversationFragment.TAG)
                            .commit();
                    mConversationPresenter = new ConversationPresenter(
                            Injection.provideConversationRepository(
                                    Injection.provideConversationLocalDataSource()),
                            conversationFragment,
                            this);
                } else {//Not installed
                    InstallFragment installFragment = new InstallFragment();
                    getSupportFragmentManager()
                            .beginTransaction()
                            .addToBackStack(InstallFragment.TAG)
                            .add(R.id.conversation_fragment_container, installFragment, InstallFragment.TAG)
                            .commit();
                    mInstallPresenter = new InstallPresenter(
                            Injection.providePersonRepository(
                                    Injection.providePersonLocalDataSource(),
                                    Injection.providePersonRemoteDataSource(
                                            Injection.provideBackendApi()
                                    )
                            ),
                            installFragment,
                            this);
                }
            } else if (fragment instanceof ConversationFragment) {
                //If it is a conversation fragment that means app must be IS_INSTALLED
                mConversationPresenter = new ConversationPresenter(
                        Injection.provideConversationRepository(
                                Injection.provideConversationLocalDataSource()),
                        (ConversationFragment) fragment,
                        this);
            } else if (fragment instanceof InstallFragment) {
                mInstallPresenter = new InstallPresenter(
                        Injection.providePersonRepository(
                                Injection.providePersonLocalDataSource(),
                                Injection.providePersonRemoteDataSource(
                                        Injection.provideBackendApi()
                                )
                        ),
                        (InstallFragment) fragment,
                        this);
            } else if (fragment instanceof NewConversationFragment) {
                mNewConversationPresenter = new NewConversationPresenter(
                        Injection.providePersonRepository(
                                Injection.providePersonLocalDataSource(),
                                Injection.providePersonRemoteDataSource(
                                        Injection.provideBackendApi()
                                )
                        ),
                        Injection.provideConversationRepository(
                                Injection.provideConversationLocalDataSource()
                        ),
                        Injection.providePeCoRepository(
                                Injection.providePeCoLocalDataSource()
                        ),
                        this);
            }
            createNotificationChannel();
        } catch (Exception e) {
            Utils.logDebug("Error in ConversationActivity.onCreate: "+e.getLocalizedMessage());
        }
    }
    private void createNotificationChannel() throws Exception {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


    @Override
    public void onNewConversation() {
        try {
            NewConversationFragment newConversationFragment = new NewConversationFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .addToBackStack(NewConversationFragment.TAG)
                    .add(R.id.conversation_fragment_container, newConversationFragment, NewConversationFragment.TAG)
                    .commit();
            mNewConversationPresenter = new NewConversationPresenter(
                    Injection.providePersonRepository(
                            Injection.providePersonLocalDataSource(),
                            Injection.providePersonRemoteDataSource(
                                    Injection.provideBackendApi()
                            )
                    ),
                    Injection.provideConversationRepository(
                            Injection.provideConversationLocalDataSource()
                    ),
                    Injection.providePeCoRepository(
                            Injection.providePeCoLocalDataSource()
                    ),
                    this);
        } catch (Exception e) {
            Utils.logDebug("ConversationActivity.onNewConversation: "+e.getLocalizedMessage());
        }
    }

    @Override
    public void onInstalled() {
        try {
            ConversationFragment conversationFragment = new ConversationFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .addToBackStack(ConversationFragment.TAG)
                    .replace(R.id.conversation_fragment_container, conversationFragment, ConversationFragment.TAG)
                    .commit();
            mConversationPresenter = new ConversationPresenter(
                    Injection.provideConversationRepository(
                            Injection.provideConversationLocalDataSource()),
                    conversationFragment,
                    this);
        } catch (Exception e) {
            Utils.logDebug("ConversationActivity.onInstalled: "+e.getLocalizedMessage());
        }
    }

    @Override
    public void goToConversation(long aCoPublicId, String aCoTitle) {
        try {
            MsgsActivity.start(this,aCoPublicId,aCoTitle);//Show new conversation.
            //Close newConversation fragment
            //Trying to just use popBackStack. Think it should work...
            getSupportFragmentManager().popBackStack();
        } catch (Exception e) {
            Utils.logDebug("ConversationActivity.goToConversation: "+e.getLocalizedMessage());
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (getSupportFragmentManager().getBackStackEntryCount()<2){
            finish();
        } else {
            super.onBackPressed();
        }
    }
}
