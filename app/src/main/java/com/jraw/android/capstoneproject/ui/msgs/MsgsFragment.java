package com.jraw.android.capstoneproject.ui.msgs;


import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.jraw.android.capstoneproject.R;
import com.jraw.android.capstoneproject.ui.list.ListHandler;
import com.jraw.android.capstoneproject.ui.list.ListHandlerCallback;
import com.jraw.android.capstoneproject.ui.list.ListRecyclerViewAdapter;
import com.jraw.android.capstoneproject.utils.EspressoIdlingResource;
import com.jraw.android.capstoneproject.utils.Utils;

/**
 * Handles View part of Msgs functionality.
 * Holds list of msgs.
 *
 * Uses a custom 'ActionBar' called SearchBar. Simply provides a SearchView for the user to query
 * data via Presenter. Should be flexible and easily added to other views. Can also be extended and
 * allows for communication between host and SearchBar. Its basically a View so resides in View part.
 */
public class MsgsFragment extends Fragment implements MsgsContract.ViewMsgs,
        ListHandler.ListHandlerContract,
        LoaderManager.LoaderCallbacks<Cursor> {

    public static final String TAG = "msgsFragTag";
    private static final String CO_PUBLIC_ID = "coPubId";
    private long mCOPubId;
    private static final String CO_TITLE = "coTitle";
    private String mCOTitle;

    private MsgsContract.PresenterMsgs mPresenterMsgs;

    private EditText mMsgET;

    private ListHandler mListHandler;
    private static final String LIST_STATE = "listState";
    private Parcelable mListState;

    public MsgsFragment() {}

    public static MsgsFragment getInstance(long aCOPublicID, String aCOTitle) {
        MsgsFragment fragment = new MsgsFragment();
        Bundle bundle = new Bundle();
        bundle.putLong(CO_PUBLIC_ID,aCOPublicID);
        bundle.putString(CO_TITLE,aCOTitle);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState!=null) {
            mCOPubId = savedInstanceState.getLong(CO_PUBLIC_ID);
            mCOTitle = savedInstanceState.getString(CO_TITLE);
            mListState = savedInstanceState.getParcelable(LIST_STATE);
        } else if (getArguments()!=null) {
            mCOPubId = getArguments().getLong(CO_PUBLIC_ID);
            mCOTitle = getArguments().getString(CO_TITLE);
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_msgs, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mMsgET = view.findViewById(R.id.fragment_msgs_new_msg_edit_text);
        ImageButton mSendIB = view.findViewById(R.id.fragment_msgs_new_msg_send_button);
        mSendIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View aView) {
                sendNewMsg();
            }
        });

        //Set ListHandler here
        RecyclerView recyclerView = view.findViewById(R.id.fragment_msgs_recycler_view);
        mListHandler = new ListHandler(this,
                recyclerView,
                new ListRecyclerViewAdapter(new ListHandlerCallback() {
                    @Override
                    public void onListClick(int aPosition, String aId) {
                        //This is what is set on every item in the list
                        //For now nothing happening on Msg click. Extending would add Msg info? Save/Copy/blah to Msg?
                    }

                    @Override
                    public void onListTouch(View aView, MotionEvent aMotionEvent) {
                        //This is what is set on every item in the list
                    }
                }, R.layout.list_item_msgs),
                new LinearLayoutManager(recyclerView.getContext(), LinearLayoutManager.VERTICAL,true));
        Bundle args = new Bundle();
        args.putLong(CO_PUBLIC_ID,mCOPubId);
        getLoaderManager().initLoader(1,args,this);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        EspressoIdlingResource.increment();
        return mPresenterMsgs.getMsgs(requireActivity(),args.getLong(CO_PUBLIC_ID));
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        setMsgs(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        setMsgs(null);
    }

    @Override
    public void setMsgs(Cursor aList) {
        if (!EspressoIdlingResource.getIdlingResource().isIdleNow()) {
            EspressoIdlingResource.decrement(); // Set app as idle.
        }
        mListHandler.swapMsgs(aList);
        if (mListState != null) {
            mListHandler.setState(mListState);
        }
        if (mCOTitle != null && !mCOTitle.equals("null")) {
            requireActivity().setTitle(mCOTitle);
        } else {
            requireActivity().setTitle("");
        }
    }

    @Override
    public void sendNewMsg() {
        String body = mMsgET.getText().toString();
        mPresenterMsgs.sendNewMsg(requireActivity(), mCOPubId,mCOTitle,body);
    }

    @Override
    public void sentNewMsg() {
        //Clear sent msg.
        mMsgET.setText(null);
    }

    @Override
    public void problemSendingMsg() {
        Toast.makeText(requireActivity(), getString(R.string.send_msg_error), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setPresenter(MsgsContract.PresenterMsgs aPresenter) {
        mPresenterMsgs=aPresenter;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(CO_PUBLIC_ID,mCOPubId);
        outState.putString(CO_TITLE,mCOTitle);
        outState.putParcelable(LIST_STATE,mListHandler.getState());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        clearListHandler();
    }

    @Override
    public void clearListHandler() {
        mListHandler.clearListHandler();
    }
}
