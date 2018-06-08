package com.jraw.android.capstoneproject.ui.newcontact;

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
import android.widget.EditText;
import android.widget.Toast;

import com.jraw.android.capstoneproject.R;

public class NewContactFragment extends Fragment implements NewContactContract.ViewNewContact {

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
        mFirstNameET.requestFocus();//Focus on start
        mSurnameET = view.findViewById(R.id.fragment_new_contact_surname_et);
        mTelNumET = view.findViewById(R.id.fragment_new_contact_tel_num_et);
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
                mPresenterNewContact.onSave(getActivity(),
                        mFirstNameET.getText().toString(),
                        mSurnameET.getText().toString(),
                        mTelNumET.getText().toString());
                return true;
            case R.id.new_contact_cancel:
                mPresenterNewContact.onCancel();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
