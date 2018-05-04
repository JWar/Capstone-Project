package com.jraw.android.capstoneproject.ui.newconversation;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jraw.android.capstoneproject.R;
import com.jraw.android.capstoneproject.ui.list.ListHandler;
import com.jraw.android.capstoneproject.ui.list.ListHandlerCallback;
import com.jraw.android.capstoneproject.ui.list.ListRecyclerViewAdapter;
import com.jraw.android.capstoneproject.utils.Utils;

/**
 * Handles new conversations creation.
 * This will be two lists, one horizontal, one vertical.
 * The horizontal will contain all the people selected to be in the conversation.
 * The vertical will contain a list of all persons to select.
 */
public class NewConversationFragment extends Fragment {

    private ListHandler mAddedLH;
    private static final String ADDED_STATE = "addedState";
    private Parcelable mAddedState;

    private ListHandler mPersonsLH;
    private static final String PERSONS_STATE = "personsState";
    private Parcelable mPersonsState;

    public NewConversationFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (savedInstanceState!=null) {
            mAddedState = savedInstanceState.getParcelable(ADDED_STATE);
            mPersonsState = savedInstanceState.getParcelable(PERSONS_STATE);
        }
        return inflater.inflate(R.layout.fragment_new_conversation, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView mAddedRV = view.findViewById(R.id.fragment_new_conversation_added_rv);
        mAddedRV.setLayoutManager(new LinearLayoutManager(mAddedRV.getContext(),LinearLayoutManager.VERTICAL,false));
        mAddedLH = new ListHandler(
                this,
                mAddedRV,
                new ListRecyclerViewAdapter(new ListHandlerCallback() {
                    @Override
                    public void onListClick(int aPosition, String aId) {

                    }

                    @Override
                    public void onListTouch(View aView, MotionEvent aMotionEvent) {

                    }
                }, R.layout.list_item_added_person)
        );
        if (mAddedState!=null) {
            mAddedLH.setState(mAddedState);
        }
        mPersonsLH = new ListHandler(
                this,
                view.findViewById(R.id.fragment_new_conversation_persons_rv),
                new ListRecyclerViewAdapter(new ListHandlerCallback() {
                    @Override
                    public void onListClick(int aPosition, String aId) {

                    }

                    @Override
                    public void onListTouch(View aView, MotionEvent aMotionEvent) {

                    }
                }, R.layout.list_item_person)
        );
        if (mPersonsState!=null) {
            mPersonsLH.setState(mPersonsState);
        }
    }

}
