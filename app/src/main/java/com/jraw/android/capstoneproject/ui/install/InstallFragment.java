package com.jraw.android.capstoneproject.ui.install;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.jraw.android.capstoneproject.R;
import com.jraw.android.capstoneproject.ui.IntegerAsyncTaskLoader;

/**
 * ... handles installation routine.
 * This will need name, tel num, get contacts list?
 * Not sure...
 */
public class InstallFragment extends Fragment implements InstallContract.ViewInstall,
        LoaderManager.LoaderCallbacks<Integer>{

    public static final String TAG = "installFragmentTag";

    private static final String FIRST_NAME = "fname";
    private EditText mFirstNameET;
    private static final String SUR_NAME = "sname";
    private EditText mSurnameET;
    private static final String TEL_NUM = "telnum";
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
                save();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void save() {
        Bundle args = new Bundle();
        args.putString(FIRST_NAME,mFirstNameET.getText().toString());
        args.putString(SUR_NAME,mSurnameET.getText().toString());
        args.putString(TEL_NUM,mTelNumET.getText().toString());
        getLoaderManager().initLoader(1,args,this);
    }

    @NonNull
    @Override
    public Loader<Integer> onCreateLoader(int id, @Nullable Bundle args) {
        final String fName = args.getString(FIRST_NAME);
        final String sName = args.getString(SUR_NAME);
        final String telNum = args.getString(TEL_NUM);
        return new IntegerAsyncTaskLoader(getActivity(),mInstallPresenter,
                fName,sName,telNum);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Integer> loader, Integer data) {
        //Go through result codes?
        if (data==-1) {
            //Error.
            Toast.makeText(getActivity(), getString(R.string.install_error_msg), Toast.LENGTH_SHORT).show();
        } else if (data==0) {
            //Unsuccessful server conn?
            Toast.makeText(getActivity(), getString(R.string.install_unsuccessful_msg), Toast.LENGTH_SHORT).show();
        } else {
            //Success.
            mInstallPresenter.onInstalled();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Integer> loader) {

    }
}