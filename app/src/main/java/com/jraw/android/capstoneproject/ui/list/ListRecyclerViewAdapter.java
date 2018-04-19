package com.jraw.android.capstoneproject.ui.list;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jraw.android.capstoneproject.R;
import com.jraw.android.capstoneproject.data.model.Conversation;
import com.jraw.android.capstoneproject.data.model.Msg;
import com.jraw.android.capstoneproject.ui.list.holders.AbstractHolder;
import com.jraw.android.capstoneproject.ui.list.holders.ConvHolder;
import com.jraw.android.capstoneproject.ui.list.holders.MsgHolder;
import com.jraw.android.capstoneproject.utils.Utils;

import java.util.List;

public class ListRecyclerViewAdapter extends RecyclerView.Adapter<AbstractHolder> {
    private final ListHandlerCallback mListener;
    //Specifies what sort of list wanted (client, user, activity etc...). Will use R.layout.fragment_list_item_...
    private int listItemType;

    private List<Conversation> mConversations;
    private List<Msg> mMsgs;


    public ListRecyclerViewAdapter(ListHandlerCallback listener, int type) {
        mListener = listener;
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
            case R.layout.fragment_list_item_msgs:
                return new MsgHolder(view);
            case R.layout.fragment_list_item_convs:
                return new ConvHolder(view);
            default:
                Utils.logDebug("Error in ListRecyclerAdapter.onCreateViewHolder: listItemType unrecognised.");
                return null;
        }
    }
    //This is where the data is put into the holder (in bindData).
    @Override
    public void onBindViewHolder(@NonNull final AbstractHolder holder, final int position) {
        try {
            String dId=null;
            if (holder instanceof MsgHolder) {
                MsgHolder msgHolder = (MsgHolder) holder;
                dId = msgHolder.bindData(mMsgs.get(position),position);
            } else if (holder instanceof ConvHolder) {
                ConvHolder convsHolder = (ConvHolder) holder;
                dId = convsHolder.bindData(mConversations.get(position),position);
            }
            final String dataId = dId;
            holder.setListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {//This sets up what on click will be. Draws from parent Activity.
                    try {
                        if (null != mListener) {
                            // Notify the active callbacks interface (the activity, if the
                            // fragment is attached to one) that an item has been selected.
                            mListener.onListClick(holder.getAdapterPosition(), dataId);
                        }
                    } catch (Exception e) {
                        Utils.logDebug("Error in onListListener: " + e.getMessage());
                    }
                }
            });
        } catch (Exception e) {
            Utils.logDebug("Error in ListRecyclerAdapter.onBindViewHolder: " + e.getMessage());
        }
    }
    @Override
    public long getItemId(int aPos) {
        try {
            if (mConversations!=null) {
                return mConversations.get(aPos).getId();
            } else if (mMsgs!=null) {
                return mMsgs.get(aPos).getId();
            } else {
                return -1;
            }
        } catch (Exception e) {Utils.logDebug("Error in ListAdapter.getItemId: " + e.getMessage());return -1;}
    }
    @Override
    public int getItemCount() {
        try {
            if (mConversations!=null) {
                return mConversations.size();
            } else if (mMsgs!=null) {
                return mMsgs.size();
            } else {
                return -1;
            }
        } catch (Exception e) {
            Utils.logDebug("Error in ListRecyclerAdapter.getItemCount: " + e.getMessage());
            return -1;
        }
    }
}