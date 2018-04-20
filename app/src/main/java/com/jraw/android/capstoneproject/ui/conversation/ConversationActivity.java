package com.jraw.android.capstoneproject.ui.conversation;

import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.jraw.android.capstoneproject.R;
import com.jraw.android.capstoneproject.utils.Utils;
import com.jwar.android.capstoneproject.Injection;


public class ConversationActivity extends AppCompatActivity {

    private ConversationPresenter mConversationPresenter;
    private InstallPresenter mInstallPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        Toolbar toolbar = findViewById(R.id.conversations_toolbar);
        setSupportActionBar(toolbar);
        try {
            Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.conversations_fragment_container);
            if (fragment == null) {//Assuming if null then its a Conversation.
                ConversationFragment conversationFragment = new ConversationFragment();
                getSupportFragmentManager()
                        .beginTransaction()
                        .addToBackStack(ConversationFragment.TAG)
                        .add(R.id.conversations_fragment_container, conversationFragment, ConversationFragment.TAG)
                        .commit();
                //Hmm this is where the database gets init... the Presenter inits the Repo inits LocalDataSource which inits DB.
                mConversationPresenter = new ConversationPresenter(Injection.provideConversationRepository(this),
                        conversationFragment);
            } else if (fragment instanceof ConversationFragment) {
                mConversationPresenter = new ConversationPresenter(Injection.provideConversationRepository(this),
                        (ConversationFragment) fragment);
            } else if (fragment instanceof InstallFragment) {
                mInstallPresenter = new InstallPresenter((InstallFragment) fragment);
            }
        } catch (Exception e) {
            Utils.logDebug("Error in ConversationActivity.onCreate: "+e.getLocalizedMessage());
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
