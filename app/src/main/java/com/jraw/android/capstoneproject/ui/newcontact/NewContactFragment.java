package com.jraw.android.capstoneproject.ui.newcontact;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

import com.jraw.android.capstoneproject.R;

public class NewContactFragment extends Fragment implements NewContactContract.ViewNewContact {

    public static final String TAG = "newContactFragTag";

    private EditText mFirstNameET;
    private EditText mSurnameET;
    private EditText mTelNumET;

    private NewContactContract.PresenterNewContact mPresenterNewContact;

    public NewContactFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_new_contact, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFirstNameET = view.findViewById(R.id.fragment_new_contact_first_name_et);
        mFirstNameET.requestFocus();//Focus on start. sigh doesnt bring keyboard up...
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mFirstNameET, InputMethodManager.SHOW_IMPLICIT);
        mSurnameET = view.findViewById(R.id.fragment_new_contact_surname_et);
        mTelNumET = view.findViewById(R.id.fragment_new_contact_tel_num_et);
        getActivity().setTitle(getString(R.string.new_contact));
    }

    @Override
    public void saveSuccessful() {
        Toast.makeText(getActivity(), getString(R.string.save_successful), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void problemSaving() {
        Toast.makeText(getActivity(), getString(R.string.problem_saving), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setPresenter(NewContactContract.PresenterNewContact aPresenter) {
        mPresenterNewContact=aPresenter;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_new_contact, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.new_contact_save:
                String firstName = mFirstNameET.getText().toString();
                String surname = mSurnameET.getText().toString();
                String telNum = mTelNumET.getText().toString();
                if (firstName.length()>0&&
                        telNum.length()>0) {
                    mPresenterNewContact.onSave(getActivity(),
                            firstName,
                            surname,
                            telNum);
                } else {
                    Toast.makeText(getActivity(), getString(R.string.new_contact_empty_error), Toast.LENGTH_SHORT).show();
                }
                return true;
            case R.id.new_contact_cancel:
                getActivity().onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
