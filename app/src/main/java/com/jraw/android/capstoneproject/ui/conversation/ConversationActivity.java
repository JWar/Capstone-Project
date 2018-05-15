package com.jraw.android.capstoneproject.ui.conversation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.jraw.android.capstoneproject.R;
import com.jraw.android.capstoneproject.ui.install.InstallContract;
import com.jraw.android.capstoneproject.ui.install.InstallFragment;
import com.jraw.android.capstoneproject.ui.install.InstallPresenter;
import com.jraw.android.capstoneproject.ui.msgs.MsgsActivity;
import com.jraw.android.capstoneproject.ui.newconversation.NewConversationContract;
import com.jraw.android.capstoneproject.utils.Utils;
import com.jwar.android.capstoneproject.Injection;

import static com.jraw.android.capstoneproject.utils.Utils.SHAR_PREFS;

public class ConversationActivity extends AppCompatActivity implements
        InstallContract.ActivityInstall,
        NewConversationContract.ActivityNewConversation {

    private static final String IS_INSTALLED = "isInstalled";

    private ConversationPresenter mConversationPresenter;
    private InstallPresenter mInstallPresenter;

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
                            conversationFragment);
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
                            this
                    );
                }
            } else if (fragment instanceof ConversationFragment) {
                //If it is a conversation fragment that means app must be IS_INSTALLED
                mConversationPresenter = new ConversationPresenter(
                        Injection.provideConversationRepository(
                                Injection.provideConversationLocalDataSource()),
                        (ConversationFragment) fragment);
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
            }
        } catch (Exception e) {
            Utils.logDebug("Error in ConversationActivity.onCreate: "+e.getLocalizedMessage());
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
                    conversationFragment);
        } catch (Exception e) {
            Utils.logDebug("ConversationActivity.onInstalled: "+e.getLocalizedMessage());
        }
    }

    @Override
    public void goToConversation(long aCoPublicId, String aCoTitle) {
        try {
            MsgsActivity.start(this,aCoPublicId,aCoTitle);//Show new conversation.
            //Close newConversation fragment
            getSupportFragmentManager()
                    .beginTransaction()
                    .remove()
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
