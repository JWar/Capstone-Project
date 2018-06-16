package com.jraw.android.capstoneproject.ui.list;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jraw.android.capstoneproject.R;
import com.jraw.android.capstoneproject.data.model.Person;
import com.jraw.android.capstoneproject.data.model.cursorwrappers.ConversationCursorWrapper;
import com.jraw.android.capstoneproject.data.model.cursorwrappers.MsgCursorWrapper;
import com.jraw.android.capstoneproject.data.model.cursorwrappers.PersonCursorWrapper;
import com.jraw.android.capstoneproject.ui.list.holders.AbstractHolder;
import com.jraw.android.capstoneproject.ui.list.holders.ConvHolder;
import com.jraw.android.capstoneproject.ui.list.holders.MsgHolder;
import com.jraw.android.capstoneproject.ui.list.holders.NewConvAddedPersonHolder;
import com.jraw.android.capstoneproject.ui.list.holders.NewConvPersonHolder;
import com.jraw.android.capstoneproject.utils.Utils;

import java.util.List;

public class ListRecyclerViewAdapter extends RecyclerView.Adapter<AbstractHolder> {
    private ListHandlerCallback mListener;
    private ListHandlerCallbackPerson mListenerPerson;
    //Specifies what sort of list wanted (client, user, activity etc...). Will use R.layout.fragment_list_item_...
    private int listItemType;
    //Switched to using Cursor...
    private ConversationCursorWrapper mConversationCursorWrapper;
    private MsgCursorWrapper mMsgCursorWrapper;
    private PersonCursorWrapper mPersonCursorWrapper;
    private List<Person> mPersonAddedList;//This is a copy of the PersonRepo field
//    private List<Conversation> mConversations;
//    private List<Msg> mMsgs;


    public ListRecyclerViewAdapter(ListHandlerCallback listener, int type) {
        mListener = listener;
        listItemType = type;
    }
    //Quick and dirty way allowing list clicks for persons.
    public ListRecyclerViewAdapter(ListHandlerCallbackPerson listener, int type) {
        mListenerPerson = listener;
        listItemType = type;
    }

    //This is where the holder type is specified. Must check param list type and change accordingly.
    //User Type to User Holder etc...
    @NonNull
    @Override
    public AbstractHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(listItemType, parent, false);
        //Control which viewholder to call based upon list (item) type.
        switch(listItemType) {
            case R.layout.list_item_msgs:
                return new MsgHolder(view);
            case R.layout.list_item_convs:
                return new ConvHolder(view);
            case R.layout.list_item_person:
                return new NewConvPersonHolder(view);
            case R.layout.list_item_added_person:
                return new NewConvAddedPersonHolder(view);
            default:
                Utils.logDebug("Error in ListRecyclerAdapter.onCreateViewHolder: listItemType unrecognised.");
                return null;
        }
    }
    //This is where the data is put into the holder (in bindData).
    @Override
    public void onBindViewHolder(@NonNull final AbstractHolder holder, final int position) {
        try {
            String dId;
            if (holder instanceof MsgHolder) {
                MsgHolder msgHolder = (MsgHolder) holder;
                mMsgCursorWrapper.moveToPosition(position);
                dId = msgHolder.bindData(mMsgCursorWrapper.getMsg(),position);
                setListener(holder,dId);
            } else if (holder instanceof ConvHolder) {
                ConvHolder convsHolder = (ConvHolder) holder;
                mConversationCursorWrapper.moveToPosition(position);
                dId = convsHolder.bindData(mConversationCursorWrapper.getConversation(),position);
                setListener(holder,dId);
            }  else if (holder instanceof NewConvPersonHolder) {
                NewConvPersonHolder personHolder = (NewConvPersonHolder) holder;
                mPersonCursorWrapper.moveToPosition(position);
                setListenerPerson(personHolder,personHolder.bindData(mPersonCursorWrapper.getPerson(),position));
            } else if (holder instanceof NewConvAddedPersonHolder) {
                NewConvAddedPersonHolder personHolder = (NewConvAddedPersonHolder) holder;
                setListenerPerson(personHolder,
                        personHolder.bindData(mPersonAddedList.get(position),position));
            }
        } catch (Exception e) {
            Utils.logDebug("Error in ListRecyclerAdapter.onBindViewHolder: " + e.getMessage());
        }
    }
    private void setListener(final AbstractHolder aHolder, final String aDId) {
        aHolder.setListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//This sets up what on click will be. Draws from parent Activity.
                try {
                    if (null != mListener) {
                        // Notify the active callbacks interface (the activity, if the
                        // fragment is attached to one) that an item has been selected.
                        mListener.onListClick(aHolder.getAdapterPosition(), aDId);
                    }
                } catch (Exception e) {
                    Utils.logDebug("Error in onListListener: " + e.getMessage());
                }
            }
        });
    }
    private void setListenerPerson(final AbstractHolder aPersonHolder, final Person aPerson) {
        aPersonHolder.setListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//This sets up what on click will be. Draws from parent Activity.
                try {
                    if (null != mListenerPerson) {
                        // Notify the active callbacks interface (the activity, if the
                        // fragment is attached to one) that an item has been selected.
                        mListenerPerson.onListClick(aPersonHolder.getAdapterPosition(), aPerson);
                    }
                } catch (Exception e) {
                    Utils.logDebug("Error in onListListener: " + e.getMessage());
                }
            }
        });
    }
    @Override
    public long getItemId(int aPos) {
        try {
            if (mConversationCursorWrapper!=null) {
                mConversationCursorWrapper.moveToPosition(aPos);
                return mConversationCursorWrapper.getConversation().getId();
            } else if (mMsgCursorWrapper!=null) {
                mMsgCursorWrapper.moveToPosition(aPos);
                return mMsgCursorWrapper.getMsg().getId();
            } else if (mPersonCursorWrapper!=null) {
                mPersonCursorWrapper.moveToPosition(aPos);
                return mPersonCursorWrapper.getPerson().getId();
            } else if (mPersonAddedList!=null) {
                return mPersonAddedList.get(aPos).getId();
            } else {
                return -1;
            }
        } catch (Exception e) {Utils.logDebug("Error in ListAdapter.getItemId: " + e.getMessage());return -1;}
    }
    @Override
    public int getItemCount() {
        try {
            if (mConversationCursorWrapper!=null) {
                return mConversationCursorWrapper.getCount();
            } else if (mMsgCursorWrapper!=null) {
                return mMsgCursorWrapper.getCount();
            } else if (mPersonCursorWrapper!=null) {
                return mPersonCursorWrapper.getCount();
            } else if (mPersonAddedList!=null) {
                return mPersonAddedList.size();
            } else {
                return -1;
            }
        } catch (Exception e) {
            Utils.logDebug("Error in ListRecyclerAdapter.getItemCount: " + e.getMessage());
            return -1;
        }
    }

    public void setConversationCursorWrapper(Cursor aCursor) {
        //Think CursorLoader handles closing...
//        if (mConversationCursorWrapper!=null) {
//            mConversationCursorWrapper.close();
//            mConversationCursorWrapper=null;
//        }
        mConversationCursorWrapper = new ConversationCursorWrapper(aCursor);
        notifyDataSetChanged();
    }
    public void setMsgCursorWrapper(Cursor aCursor) {
        //Think CursorLoader handles closing...
//        if (mMsgCursorWrapper!=null) {
//            mMsgCursorWrapper.close();
//            mMsgCursorWrapper=null;
//        }
        mMsgCursorWrapper = new MsgCursorWrapper(aCursor);
        notifyDataSetChanged();
    }
    public void setPersonCursorWrapper(Cursor aCursor) {
        mPersonCursorWrapper = new PersonCursorWrapper(aCursor);
        notifyDataSetChanged();
    }
    public void setPersonAddedList(@Nullable List<Person> aPersonAddedList) {
        mPersonAddedList=aPersonAddedList;
        notifyDataSetChanged();
    }
}