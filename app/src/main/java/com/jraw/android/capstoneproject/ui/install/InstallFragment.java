package com.jraw.android.capstoneproject.ui.install;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.jraw.android.capstoneproject.R;
import com.jraw.android.capstoneproject.ui.IntegerAsyncTaskLoader;
import com.jraw.android.capstoneproject.utils.EspressoIdlingResource;
import com.jraw.android.capstoneproject.utils.Utils;

/**
 * ... handles installation routine.
 * This will need name, tel num, get contacts list?
 * Not sure...
 */
public class InstallFragment extends Fragment implements InstallContract.ViewInstall,
        LoaderManager.LoaderCallbacks<Integer> {

    public static final String TAG = "installFragmentTag";

    public static final String FIRST_NAME = "fname";
    private EditText mFirstNameET;
    String mFName;
    public static final String SUR_NAME = "sname";
    private EditText mSurnameET;
    String mSName;
    public static final String TEL_NUM = "telnum";
    private EditText mTelNumET;
    String mTelNum;

    private InstallContract.PresenterInstall mInstallPresenter;

    public InstallFragment() {
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_install, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (savedInstanceState!=null) {
            mFName = savedInstanceState.getString(FIRST_NAME);
            mSName = savedInstanceState.getString(SUR_NAME);
            mTelNum = savedInstanceState.getString(TEL_NUM);
        }
        mFirstNameET = view.findViewById(R.id.fragment_install_first_name_et);
        mFirstNameET.requestFocus();//Focus on start
        mSurnameET = view.findViewById(R.id.fragment_install_surname_et);
        mTelNumET = view.findViewById(R.id.fragment_install_tel_num_et);

        view.findViewById(R.id.fragment_install_save_button)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View aView) {
                        save();
                    }
                });
        view.findViewById(R.id.fragment_install_cancel_button)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View aView) {
                        requireActivity().onBackPressed();
                    }
                });
    }

    @Override
    public void setPresenter(InstallContract.PresenterInstall aPresenter) {
        mInstallPresenter = aPresenter;
    }

    private void save() {
        Bundle args = new Bundle();
        if (mTelNumET.getText().length()>10) {
            mFName = mFirstNameET.getText().toString();
            mSName = mSurnameET.getText().toString();
            mTelNum = mTelNumET.getText().toString();
            args.putString(FIRST_NAME, mFName);
            args.putString(SUR_NAME, mSName);
            args.putString(TEL_NUM, mTelNum);
            getLoaderManager().initLoader(1, args, this).forceLoad();
        } else {
            Toast.makeText(requireActivity(), getString(R.string.user_wrong_install_entry), Toast.LENGTH_SHORT).show();
        }
    }

    @NonNull
    @Override
    public Loader<Integer> onCreateLoader(int id, @Nullable Bundle args) {
        final String fName = args.getString(FIRST_NAME);
        final String sName = args.getString(SUR_NAME);
        final String telNum = args.getString(TEL_NUM);
        EspressoIdlingResource.increment();
        return new IntegerAsyncTaskLoader(requireActivity(), mInstallPresenter,
                fName, sName, telNum);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Integer> loader, Integer data) {
        //Go through result codes?
        if (!EspressoIdlingResource.getIdlingResource().isIdleNow()) {
            EspressoIdlingResource.decrement(); // Set app as idle.
        }
        if (data == -1) {
            //Error.
            Toast.makeText(requireActivity(), getString(R.string.install_error_msg), Toast.LENGTH_SHORT).show();
        } else if (data == 0) {
            //Unsuccessful server conn?
            Toast.makeText(requireActivity(), getString(R.string.install_unsuccessful_msg), Toast.LENGTH_SHORT).show();
        } else {
            //Success.
            //Store into sharedPrefs too!
            SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(Utils.SHAR_PREFS,0);
            SharedPreferences.Editor edit = sharedPreferences.edit();
            edit.putString(FIRST_NAME, mFName);
            edit.putString(SUR_NAME, mSName);
            edit.putString(TEL_NUM, mTelNum);
            edit.commit();
            mInstallPresenter.onInstalled();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<Integer> loader) {

    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(FIRST_NAME,mFName);
        outState.putString(SUR_NAME,mSName);
        outState.putString(TEL_NUM,mTelNum);
    }
}