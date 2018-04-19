package com.jraw.android.capstoneproject.ui.newconversation;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.jraw.android.capstoneproject.R;
import com.jraw.android.capstoneproject.utils.Utils;

/**
 * Handles new conversations creation, including adding people to conversation.
 */
public class NewConversationFragment extends Fragment {
    private LinearLayout addContactsLL;
    private NewConversationFragmentInteractionListener mListener;

    public NewConversationFragment() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_new_conversation, container, false);
        addContactsLL = (LinearLayout) v.findViewById(R.id.new_conversation_contacts_ll);

        v.findViewById(R.id.new_conversation_add_person_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddPerson();
            }
        });

        return v;
    }

    public void onAddPerson() {

    }

    //Individual method to add up textview with contacts name
    private void setAddedContactTV(String aName) {
        try {
            TextView newContact = new TextView(getActivity());
            newContact.setText(aName);
            addContactsLL.addView(newContact, new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
//            RelativeLayout newView = new RelativeLayout(getActivity());
//            newView.setLayoutParams(
//                    new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                            ViewGroup.LayoutParams.WRAP_CONTENT));
//            ImageButton telLayoutIB = new ImageButton(getActivity());
//            telLayoutIB.setId(smsId);
//            telLayoutIB.setBackgroundColor(Color.TRANSPARENT);
//            telLayoutIB.setImageResource(R.drawable.pm_sms_icon);
//            telLayoutIB.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    onSMS(aPos);
//                }
//            });
//            RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
//                    ViewGroup.LayoutParams.WRAP_CONTENT);
//            lp.setMarginEnd(24);
//            lp.setMarginStart(36);
//            lp.addRule(RelativeLayout.ALIGN_PARENT_END);
//            newView.addView(telLayoutIB, lp);
//            telLayoutIB = new ImageButton(getActivity());
//            telLayoutIB.setId(telId);
//            telLayoutIB.setBackgroundColor(Color.TRANSPARENT);
//            telLayoutIB.setImageResource(android.R.drawable.sym_action_call);
//            telLayoutIB.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    onTelCall(aTelNum);
//                }
//            });
//            lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
//                    ViewGroup.LayoutParams.WRAP_CONTENT);
//
//            lp.addRule(RelativeLayout.START_OF, smsId);
//            lp.setMarginEnd(24);
//            newView.addView(telLayoutIB, lp);
//            lp = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
//                    ViewGroup.LayoutParams.WRAP_CONTENT);
//            lp.addRule(RelativeLayout.ALIGN_PARENT_START);
//            lp.addRule(RelativeLayout.START_OF, telId);
//
//            newView.addView(cET, 0, lp);
//            mTelScrollLL.addView(newView, aPos);
        } catch (Exception e) {
            Utils.logDebug("Error in OnEditFragment.setTelET: " + e.getMessage());}
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof NewConversationFragmentInteractionListener) {
            mListener = (NewConversationFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement NewConversationFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface NewConversationFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
