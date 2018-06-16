package com.jraw.android.capstoneproject.ui.newcontact;

import android.content.Context;
import android.support.annotation.NonNull;

import com.jraw.android.capstoneproject.data.model.Person;
import com.jraw.android.capstoneproject.data.repository.PersonRepository;
import com.jraw.android.capstoneproject.utils.Utils;

public class NewContactPresenter implements NewContactContract.PresenterNewContact {

    private final PersonRepository mPersonRepository;

    private NewContactContract.ViewNewContact mViewNewContact;

    public NewContactPresenter(@NonNull PersonRepository aPersonRepository,
                               @NonNull NewContactContract.ViewNewContact aViewNewContact) {
        mPersonRepository=aPersonRepository;
        mViewNewContact=aViewNewContact;
        mViewNewContact.setPresenter(this);

    }

    @Override
    public void onSave(Context aContext, String aFirstname, String aSurname, String aTelNum) {
        try {
            Person person = new Person();
            person.setPEFname(aFirstname);
            person.setPESname(aSurname);
            person.setPETelNum(aTelNum);
            if (mPersonRepository.savePersonLocal(aContext, person) > 0) {
                mViewNewContact.saveSuccessful();
            } else {
                Utils.logDebug("NewContactPresenter.onSave: savePersonLocal < 1");
                mViewNewContact.problemSaving();
            }
        } catch (Exception e) {
            Utils.logDebug("NewContactPresenter.onSave: "+e.getLocalizedMessage());
            mViewNewContact.problemSaving();
        }
    }
}
