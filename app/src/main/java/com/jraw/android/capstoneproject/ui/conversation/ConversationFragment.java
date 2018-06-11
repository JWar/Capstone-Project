package com.jraw.android.capstoneproject.ui.conversation;


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
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.jraw.android.capstoneproject.R;
import com.jraw.android.capstoneproject.ui.list.ListHandler;
import com.jraw.android.capstoneproject.ui.list.ListHandlerCallback;
import com.jraw.android.capstoneproject.ui.list.ListRecyclerViewAdapter;
import com.jraw.android.capstoneproject.ui.msgs.MsgsActivity;
import com.jraw.android.capstoneproject.utils.EspressoIdlingResource;
import com.jraw.android.capstoneproject.utils.Utils;

/**
 * Handles View for Conversation list
 */
public class ConversationFragment extends Fragment implements ConversationContract.ViewConversations,
        ListHandler.ListHandlerContract,
        LoaderManager.LoaderCallbacks<Cursor> {

    public static final String TAG = "conversationFragTag";
    private static final String TITLE_QUERY = "titleQuery";

    private ConversationContract.PresenterConversations mPresenter;

    private ListHandler mListHandler;
    private static final String LIST_STATE = "listState";
    private Parcelable mListState;

    public ConversationFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (savedInstanceState!=null) {
            mListState=savedInstanceState.getParcelable(LIST_STATE);
        }
        return inflater.inflate(R.layout.fragment_conversation, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        view.findViewById(R.id.fragment_conversation_new_conv_fab)
                .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View aView) {mPresenter.onNewConversation();}
        });
        //Set ListHandler here
        RecyclerView recyclerView = view.findViewById(R.id.fragment_conversation_recycler_view);
        mListHandler = new ListHandler(this,
                recyclerView,
                new ListRecyclerViewAdapter(new ListHandlerCallback() {
                    @Override
                    public void onListClick(int aPosition, String aId) {
                        //This is what is set on every item in the list
                        String[] split = aId.split("/");
                        MsgsActivity.start(getContext(),Long.parseLong(split[0]), split[1]);
                    }

                    @Override
                    public void onListTouch(View aView, MotionEvent aMotionEvent) {
                        //This is what is set on every item in the list
                    }
                }, R.layout.list_item_convs),
                new LinearLayoutManager(recyclerView.getContext(),LinearLayoutManager.VERTICAL,false));
        getLoaderManager().initLoader(1,null,this);
    }

    @Override
    public void setConversations(Cursor aCursor) {
        if (!EspressoIdlingResource.getIdlingResource().isIdleNow()) {
            EspressoIdlingResource.decrement(); // Set app as idle.
        }
        mListHandler.swapConversations(aCursor);
        if (mListState!=null) {
            mListHandler.setState(mListState);
        }
    }

    @Override
    public void setPresenter(ConversationContract.PresenterConversations aPresenter) {
        mPresenter = aPresenter;
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable final Bundle args) {
        if (mPresenter!=null) {
            EspressoIdlingResource.increment();
            if (args != null) {
                return mPresenter.getConversationsViaTitle(getActivity(), args.getString(TITLE_QUERY));
            } else {
                return mPresenter.getConversations(getActivity());
            }
        }
        return null;
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        Utils.dumpContent(data);
        setConversations(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        setConversations(null);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_conversations, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        MenuItem actionViewItem = menu.findItem(R.id.conversations_search_item);
        if (actionViewItem != null) {
            View v = actionViewItem.getActionView();
            //Sets new contact button method
            v.findViewById(R.id.conversations_search_bar_new_contact_button)
                    .setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View aView) {
                            mPresenter.onNewContact();
                        }
                    });

            final SearchView sV = v.findViewById(R.id.conversations_search_view);
            sV.setQuery("", false);
            sV.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    titleQuery(query);
                    sV.clearFocus();//Closes keyboard input
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    titleQuery(newText);
                    return true;
                }
            });
        }
        super.onPrepareOptionsMenu(menu);
    }

    private void titleQuery(String aQuery) {
        Bundle args =new Bundle();
        args.putString(TITLE_QUERY,aQuery);
        getLoaderManager().initLoader(1,args,this);
    }

    @Override
    public void clearListHandler() {
        mListHandler.clearListHandler();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(LIST_STATE,mListHandler.getState());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        clearListHandler();
    }
}
