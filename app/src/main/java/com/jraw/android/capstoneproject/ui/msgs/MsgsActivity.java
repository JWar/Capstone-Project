package com.jraw.android.capstoneproject.ui.msgs;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jraw.android.capstoneproject.R;
import com.jraw.android.capstoneproject.utils.Utils;
import com.jwar.android.capstoneproject.Injection;
//TODO: will need to handle marking conversation as unread.
public class MsgsActivity extends AppCompatActivity {

    private static final String CO_ID = "coId";
    private int mCOId;

    private MsgsPresenter mMsgsPresenter;

    public static void start(Context aContext, int aCOId) {
        Intent intent = new Intent(aContext,MsgsActivity.class);
        intent.putExtra(CO_ID,aCOId);
        aContext.startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_msgs);
        if (savedInstanceState!=null) {
            mCOId = savedInstanceState.getInt(CO_ID);
        } else if (getIntent()!=null) {
            mCOId = getIntent().getIntExtra(CO_ID,-1);
        }
        try {
            MsgsFragment fragment = (MsgsFragment) getSupportFragmentManager().findFragmentByTag(MsgsFragment.TAG);
            if (fragment==null) {
                fragment = MsgsFragment.getInstance(mCOId);
                getSupportFragmentManager()
                        .beginTransaction()
                        .addToBackStack(MsgsFragment.TAG)
                        .add(R.id.msgs_list_fragment_container,fragment,MsgsFragment.TAG)
                        .commit();
            }
//            mMsgsPresenter = new MsgsPresenter(Injection.provideMsgRepository(this),
//
//                    fragment);
        } catch (Exception e) {
            Utils.logDebug("Error in MsgsActivity.onCreate: "+e.getLocalizedMessage());}
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(CO_ID,mCOId);
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
