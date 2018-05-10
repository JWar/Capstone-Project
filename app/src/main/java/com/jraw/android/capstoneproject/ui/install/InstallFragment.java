package com.jraw.android.capstoneproject.ui.install;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.jraw.android.capstoneproject.R;

/**
 * ... handles installation routine.
 * This will need name, tel num, get contacts list?
 * Not sure...
 */
public class InstallFragment extends Fragment implements InstallContract.ViewInstall,
        LoaderManager.LoaderCallbacks<>{

    public static final String TAG = "installFragmentTag";

    private EditText mFirstNameET;
    private EditText mSurnameET;
    private EditText mTelNumET;

    private InstallContract.PresenterInstall mInstallPresenter;

    public InstallFragment() {}

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_install, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mFirstNameET = view.findViewById(R.id.fragment_install_first_name_et);
        mSurnameET = view.findViewById(R.id.fragment_install_surname_et);
        mTelNumET = view.findViewById(R.id.fragment_install_tel_num_et);
    }

    @Override
    public void setPresenter(InstallContract.PresenterInstall aPresenter) {
        mInstallPresenter = aPresenter;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_install,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.install_save:
                //TODO: this must be called async.
                save();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void save() {
        //Call loader?
        //TODO: install routine must be async
        mInstallPresenter.onSave(mFirstNameET.getText().toString(),
                mSurnameET.getText().toString(),
                mTelNumET.getText().toString());
    }
}