package com.jraw.android.capstoneproject.ui.msgs;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.google.firebase.analytics.FirebaseAnalytics;
import com.jraw.android.capstoneproject.R;
import com.jraw.android.capstoneproject.utils.Utils;
import com.jwar.android.capstoneproject.Injection;

//TODO: will need to handle marking conversation as unread.
public class MsgsActivity extends AppCompatActivity {

    private static final String CO_PUBLIC_ID = "coPublicId";
    private long mCoPublicId;
    private static final String CO_TITLE = "coTitle";
    private String mCOTitle;
    //This is part of analystics, to see how often widget is used!
    private static final String FROM_WIDGET = "fromWidget";

    private MsgsPresenter mMsgsPresenter;

    private FirebaseAnalytics mFirebaseAnalytics;

    public static void start(Context aContext, long aCoPublicId, String aCOTitle) {
        Intent intent = new Intent(aContext, MsgsActivity.class);
        intent.putExtra(CO_PUBLIC_ID, aCoPublicId);
        intent.putExtra(CO_TITLE, aCOTitle);
        aContext.startActivity(intent);
    }
    public static Intent getIntent(Context aContext, long aCoPublicId, String aCOTitle) {
        Intent intent = new Intent(aContext, MsgsActivity.class);
        intent.putExtra(CO_PUBLIC_ID, aCoPublicId);
        intent.putExtra(CO_TITLE, aCOTitle);
        return intent;
    }
    public static Intent getIntent(Context aContext, long aCoPublicId, String aCOTitle,
                                   boolean aFromWidget) {
        Intent intent = new Intent(aContext, MsgsActivity.class);
        intent.putExtra(CO_PUBLIC_ID, aCoPublicId);
        intent.putExtra(CO_TITLE, aCOTitle);
        intent.putExtra(FROM_WIDGET, aFromWidget);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msgs);
        if (savedInstanceState != null) {
            mCoPublicId = savedInstanceState.getLong(CO_PUBLIC_ID);
            mCOTitle = savedInstanceState.getString(CO_TITLE);
        } else if (getIntent() != null) {
            mCoPublicId = getIntent().getLongExtra(CO_PUBLIC_ID, -1);
            mCOTitle = getIntent().getStringExtra(CO_TITLE);
            if (getIntent().hasExtra(FROM_WIDGET)) {
                mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
                //This is to log how often a user uses (!) the widget functionality.
                Bundle bundle = new Bundle();
                bundle.putString(FirebaseAnalytics.Param.ITEM_ID, mCoPublicId+"");
                bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, mCOTitle);
                //This will be the users id to link event with user. Used shar prefs
                //but for now just using utils.
                bundle.putString(FirebaseAnalytics.Param.SOURCE, Utils.THIS_USER_ID+"");
                mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT,
                        bundle);
            }
        }
        try {
            MsgsFragment fragment = (MsgsFragment) getSupportFragmentManager().findFragmentByTag(MsgsFragment.TAG);
            if (fragment == null) {
                fragment = MsgsFragment.getInstance(mCoPublicId, mCOTitle);
                getSupportFragmentManager()
                        .beginTransaction()
                        .addToBackStack(MsgsFragment.TAG)
                        .add(R.id.msgs_list_fragment_container, fragment, MsgsFragment.TAG)
                        .commit();
            }
            mMsgsPresenter = new MsgsPresenter(
                    Injection.provideMsgRepository(
                            Injection.provideMsgLocalDataSource(),
                            Injection.provideMsgRemoteDataSource(
                                    Injection.provideBackendApi()
                            ),
                            Injection.provideConversationLocalDataSource()
                    ),
                    fragment);

        } catch (Exception e) {
            Utils.logDebug("Error in MsgsActivity.onCreate: " + e.getLocalizedMessage());
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(CO_PUBLIC_ID, mCoPublicId);
        outState.putString(CO_TITLE, mCOTitle);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (getSupportFragmentManager().getBackStackEntryCount() < 2) {
            finish();
        } else {
            super.onBackPressed();
        }
    }
}
