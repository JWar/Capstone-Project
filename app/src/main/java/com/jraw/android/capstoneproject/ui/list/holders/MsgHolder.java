package com.jraw.android.capstoneproject.ui.list.holders;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.jraw.android.capstoneproject.R;
import com.jraw.android.capstoneproject.data.model.Msg;
import com.jraw.android.capstoneproject.utils.Utils;

public class MsgHolder extends AbstractHolder {

    private View mView;
    private RelativeLayout mBodyRL;
    private RelativeLayout mTimeRL;
    private TextView mDateTV;//datetime text view
    private TextView mNameTV;
    private TextView mBodyTV;//body text view
    private ImageView mTickIV;


    public MsgHolder(View view) {
        super(view);
        mView = view;
        mBodyRL = view.findViewById(R.id.list_item_msgs_body_rl);
//        mTimeRL = view.findViewById(R.id.list_item_msgs_time_rl);
        mDateTV = view.findViewById(R.id.list_item_msgs_time);
        mBodyTV = view.findViewById(R.id.list_item_msgs_text_view);
    }

    private void setTimeToEnd() {
//        mNameTV.setPaddingRelative(24, 4, 72, 4);
        mBodyTV.setPaddingRelative(24, 4, 72, 4);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.ALIGN_PARENT_END);
        lp.setMargins(4, 24, 2, 4);
        mTimeRL.setLayoutParams(lp);
        lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(2, 24, 2, 4);
//        lp.addRule(RelativeLayout.END_OF, R.id.list_item_msgs_time_rl);
        mBodyRL.setLayoutParams(lp);
    }

    private void setTimeToStart() {
//        mNameTV.setPaddingRelative(76, 4, 20, 4);
        mBodyTV.setPaddingRelative(76, 4, 20, 4);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.ALIGN_PARENT_START);
        lp.setMargins(4, 24, 2, 4);
        mTimeRL.setLayoutParams(lp);
        lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(2, 24, 2, 4);
        lp.addRule(RelativeLayout.START_OF, R.id.list_item_msgs_body_rl);
        mBodyRL.setLayoutParams(lp);
//        mNameTV.setX(60);
//        mBodyTV.setX(60);
    }

    private String setViews(Msg aMsg, int aPos) {
        String toDisplay=aMsg.getMSEventDate();
        mDateTV.setText(toDisplay);
        if (aMsg.getMSFromId()== Utils.THIS_USER_ID) {//If msg is from this user
//            if (Build.VERSION.SDK_INT<Build.VERSION_CODES.LOLLIPOP) {
//                mBodyRL.setBackground(mView.getResources().getDrawable(
//                        R.drawable.bg_outgoing_bubble
//                ));
//            } else {
//                mBodyRL.setBackground(mView.getResources().getDrawable(
//                        R.drawable.bg_outgoing_bubble,null
//                ));
//            }
//            setTimeToEnd();
        } else {
            mBodyRL.setBackgroundColor(mView.getResources().getColor(R.color.colorListItem));
//            if (Build.VERSION.SDK_INT<Build.VERSION_CODES.LOLLIPOP) {
//                mBodyRL.setBackground(mView.getResources().getDrawable(
//                        R.drawable.bg_incoming_bubble
//                ));
//            } else {
//                mBodyRL.setBackground(mView.getResources().getDrawable(
//                        R.drawable.bg_incoming_bubble,null
//                ));
//            }
//            setTimeToStart();
        }
        mBodyTV.setText(aMsg.getMSBody());
        return aMsg.getId()+"";
    }

    public String bindData(Msg aMsg, int aPos) {
        try {
            return setViews(aMsg,aPos);
        } catch (Exception e) {
            Utils.logDebug("Error in MsgsHolder.bindData: " + e.getMessage());
            return null;
        }
    }

    @Override
    public void setListener(View.OnClickListener aViewListener) {
        mView.setOnClickListener(aViewListener);
    }

    @Override
    public String toString() {
        return super.toString() + " '" + mDateTV.getText() + " '" + mBodyTV.getText() + "'";
    }
}
