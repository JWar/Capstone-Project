package com.jraw.android.capstoneproject.ui.newconversation;

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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import com.jraw.android.capstoneproject.R;
import com.jraw.android.capstoneproject.data.model.Person;
import com.jraw.android.capstoneproject.ui.list.ListHandler;
import com.jraw.android.capstoneproject.ui.list.ListHandlerCallback;
import com.jraw.android.capstoneproject.ui.list.ListHandlerCallbackPerson;
import com.jraw.android.capstoneproject.ui.list.ListRecyclerViewAdapter;

import java.util.List;

/**
 * Handles new conversations creation.
 * This will be two lists, one horizontal, one vertical.
 * The horizontal will contain all the people selected to be in the conversation.
 * The vertical will contain a list of all persons to select.
 * TODO: how best to handle new PublicId?
 */
public class NewConversationFragment extends Fragment implements NewConversationContract.ViewNewConversation,
        ListHandler.ListHandlerContract,
        LoaderManager.LoaderCallbacks<Cursor> {

    private NewConversationContract.PresenterNewConversation mPresenterNewConversation;

    private ListHandler mAddedLH;
    private static final String ADDED_STATE = "addedState";
    private Parcelable mAddedState;

    private ListHandler mPersonsLH;
    private static final String PERSONS_STATE = "personsState";
    private Parcelable mPersonsState;

    public NewConversationFragment() {setHasOptionsMenu(true);}

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
                new ListRecyclerViewAdapter(new ListHandlerCallbackPerson() {
                    @Override
                    public void onListClick(int aPosition, Person aPerson) {
                        mPresenterNewConversation.removeAddedPerson(aPerson);
                    }

                    @Override
                    public void onListTouch(View aView, MotionEvent aMotionEvent) {}
                }, R.layout.list_item_added_person)
        );

        mPersonsLH = new ListHandler(
                this,
                view.findViewById(R.id.fragment_new_conversation_persons_rv),
                new ListRecyclerViewAdapter(new ListHandlerCallbackPerson() {
                    @Override
                    public void onListClick(int aPosition, Person aPerson) {
                        mPresenterNewConversation.addAddedPerson(aPerson);
                        getAddedPersons();//Updates list to reflect changes.
                    }

                    @Override
                    public void onListTouch(View aView, MotionEvent aMotionEvent) {}
                }, R.layout.list_item_person)
        );
        getLoaderManager().initLoader(1,null,this);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {
        return mPresenterNewConversation.getPersons(getActivity());
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        setPersons(data);
        //Ensures added persons list is set
        getAddedPersons();
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {
        mPersonsLH.swapPerson(null);
    }

    @Override
    public void setPersons(Cursor aCursor) {
        mPersonsLH.swapPerson(aCursor);
        if (mPersonsState!=null) {
            mPersonsLH.setState(mPersonsState);
        }
    }

    @Override
    public void getAddedPersons() {
        mAddedLH.swapAddedPerson(mPresenterNewConversation.getAddedPersons());
        if (mAddedState!=null) {
            mAddedLH.setState(mAddedState);
            //This ensures it is only done once. Doing it this way so the state isnt called on every update of list.
            //Using this one method to do it all.
            mAddedState=null;
        }
    }

    @Override
    public void clearListHandler() {
        mPersonsLH.clearListHandler();
        mAddedLH.clearListHandler();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_new_conversation, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_new_conversation_create:
                mPresenterNewConversation.onCreateConv();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void setPresenter(NewConversationContract.PresenterNewConversation aPresenter) {
        mPresenterNewConversation=aPresenter;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(ADDED_STATE,mAddedLH.getState());
        outState.putParcelable(PERSONS_STATE,mPersonsLH.getState());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        clearListHandler();
    }
}
