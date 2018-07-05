package com.jraw.android.capstoneproject.ui.conversation;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.jraw.android.capstoneproject.R;
import com.jraw.android.capstoneproject.ui.install.InstallContract;
import com.jraw.android.capstoneproject.ui.install.InstallFragment;
import com.jraw.android.capstoneproject.ui.install.InstallPresenter;
import com.jraw.android.capstoneproject.ui.msgs.MsgsActivity;
import com.jraw.android.capstoneproject.ui.newcontact.NewContactFragment;
import com.jraw.android.capstoneproject.ui.newcontact.NewContactPresenter;
import com.jraw.android.capstoneproject.ui.newconversation.NewConversationContract;
import com.jraw.android.capstoneproject.ui.newconversation.NewConversationFragment;
import com.jraw.android.capstoneproject.ui.newconversation.NewConversationPresenter;
import com.jraw.android.capstoneproject.utils.EspressoIdlingResource;
import com.jraw.android.capstoneproject.utils.Utils;
import com.jwar.android.capstoneproject.Injection;

import static com.jraw.android.capstoneproject.utils.Utils.CHANNEL_ID;
import static com.jraw.android.capstoneproject.utils.Utils.SHAR_PREFS;

/**
 * Think version 1 done now.
 *
 * Possible extensions:
 *  Improve the design - very minimal at the moment. Add transitions and such.
 *  More/Better tests. ConversationActivityTest doesnt test install fragment. Could do button testing too.
 *   Basically atm the tests are very basic. Could easily expand/add more.
 *  RxJava to replace Loader and IntentService. Though IntentService may actually be best thing for the job. Or
 *  JobScheduler?
 *  Notifications above 1 show more than just basic message
 *  Contact list. Add person/delete/edit. Standard functionality
 *  Implement Headers in recycler view. Specifically for msgs to make same date msgs grouped
 *  Dereference tel num in MsgHolder, so it displays name rather than tel. THis could be done by
 *  adding a new field to Msg object, and de referencing during msg save by getting from name from
 *  person list via tel. Done in IntentService.
 */
public class ConversationActivity extends AppCompatActivity implements
        ConversationContract.ActivityConversation,
        InstallContract.ActivityInstall,
        NewConversationContract.ActivityNewConversation {

    private static final String IS_INSTALLED = "isInstalled";

    //This is part of analytics, to see how often widget is used!
    private static final String FROM_WIDGET = "fromWidget";

    private ConversationPresenter mConversationPresenter;
    private InstallPresenter mInstallPresenter;
    private NewConversationPresenter mNewConversationPresenter;
    private NewContactPresenter mNewContactPresenter;

    private FirebaseAnalytics mFirebaseAnalytics;

    public static void start(Context aContext) {
        Intent intent = new Intent(aContext, ConversationActivity.class);
        aContext.startActivity(intent);
    }
    public static Intent getIntent(Context aContext, boolean aFromWidget) {
        Intent intent = new Intent(aContext, ConversationActivity.class);
        intent.putExtra(FROM_WIDGET,aFromWidget);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        Toolbar toolbar = findViewById(R.id.conversation_toolbar);
        setSupportActionBar(toolbar);
        setTitle(R.string.welcome);

        if (getIntent()!=null) {
            if (getIntent().hasExtra(FROM_WIDGET)) {
                mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
                //This is to log how often a user uses (!) the widget functionality.
                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "0");
                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "Conversations");
                //This will be the users id to link event with user. Used shar prefs
                //but for now just using utils.
                bundle.putString(FirebaseAnalytics.Param.SOURCE, Utils.THIS_USER_ID+"");
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT,
                        bundle);
            }
        }
        try {
            SharedPreferences sharedPreferences = getSharedPreferences(SHAR_PREFS,0);
            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.conversation_fragment_container);
            if (fragment == null) {//If null then check for install flag.
                if (sharedPreferences.getBoolean(IS_INSTALLED,Injection.getIsInstallDefault())) {//Is installed
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
                        (NewConversationFragment)fragment,
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
                    .replace(R.id.conversation_fragment_container, newConversationFragment, NewConversationFragment.TAG)
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
                    newConversationFragment,
                    this);
        } catch (Exception e) {
            Utils.logDebug("ConversationActivity.onNewConversation: "+e.getLocalizedMessage());
        }
    }

    @Override
    public void onNewContact() {
        try {
            NewContactFragment newContactFragment = new NewContactFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .addToBackStack(NewContactFragment.TAG)
                    .replace(R.id.conversation_fragment_container, newContactFragment, NewContactFragment.TAG)
                    .commit();
            mNewContactPresenter = new NewContactPresenter(
                    Injection.providePersonRepository(
                            Injection.providePersonLocalDataSource(),
                            Injection.providePersonRemoteDataSource(
                                    Injection.provideBackendApi()
                            )
                    ),
                    newContactFragment
            );
        } catch (Exception e) {
            Utils.logDebug("ConversationActivity.onNewContact: "+e.getLocalizedMessage());
        }
    }

    @Override
    public void onInstalled() {
        try {
            //Update shar prefs to say is installed.
            SharedPreferences sharedPreferences = getSharedPreferences(SHAR_PREFS,0);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(IS_INSTALLED,true);
            editor.apply();
            ConversationFragment conversationFragment = new ConversationFragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.conversation_fragment_container, conversationFragment, ConversationFragment.TAG)
                    .addToBackStack(ConversationFragment.TAG)
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
        if (getSupportFragmentManager().getBackStackEntryCount()<2){
            finish();
        } else {
            super.onBackPressed();
        }
    }
    @VisibleForTesting
    public IdlingResource getCountingIdlingResource() {
        return EspressoIdlingResource.getIdlingResource();
    }
}
