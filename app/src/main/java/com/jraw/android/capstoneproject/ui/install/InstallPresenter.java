package com.jraw.android.capstoneproject.ui.install;

import android.support.annotation.NonNull;

import com.google.firebase.iid.FirebaseInstanceId;
import com.jraw.android.capstoneproject.data.model.Person;
import com.jraw.android.capstoneproject.data.repository.PersonRepository;
import com.jraw.android.capstoneproject.data.source.remote.ResponseServerPersonSave;
import com.jraw.android.capstoneproject.utils.Utils;

public class InstallPresenter implements InstallContract.PresenterInstall {
    private final PersonRepository mPersonRepository;
    private InstallContract.ViewInstall mViewInstall;
    private InstallContract.ActivityInstall mActivityInstall;
    public InstallPresenter(@NonNull PersonRepository aPersonRepository,
                            @NonNull InstallContract.ViewInstall aViewInstall,
                            @NonNull InstallContract.ActivityInstall aActivityInstall) {
        mPersonRepository = aPersonRepository;
        mViewInstall = aViewInstall;
        mActivityInstall = aActivityInstall;
        mViewInstall.setPresenter(this);
    }

    @Override
    public int onSave(String aFirstName, String aSurname, String aTelNum) {
        try {
            //This will need to be async. Need to get firebase token, call server with these details
            //and return with the response letting know success/failure.
            //If success then onInstalled needs to be called. Else, notify user of failure.
            String token = FirebaseInstanceId.getInstance().getToken();
            Person person = new Person();
            person.setPEFname(aFirstName);
            person.setPESname(aSurname);
            person.setPETelNum(aTelNum);
            person.setPEFirebaseToken(token);//Needs to be sent so server can push to device
            ResponseServerPersonSave responseServerPersonSave = mPersonRepository.savePersonRemote(person);
            if (responseServerPersonSave.action.equals("COMPLETE")) {
                return 1;
            } else {
                Utils.logDebug("InstallPresenter.onSave: INCOMPLETE " +
                        responseServerPersonSave.error1 +
                        " " +
                        responseServerPersonSave.error2);
                return 0;
            }
        } catch (Exception e) {
            Utils.logDebug("InstallPresenter.onSave: "+e.getLocalizedMessage());
            return -1;
        }
    }

    @Override
    public void onInstalled() {mActivityInstall.onInstalled();}
}
