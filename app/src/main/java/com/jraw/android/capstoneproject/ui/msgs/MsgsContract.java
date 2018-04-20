package com.jraw.android.capstoneproject.ui.msgs;

import com.jraw.android.capstoneproject.data.model.Msg;

import java.util.List;

/**
 * Created by JonGaming on 05/01/2018.
 * Interface to provide the contract for Msgs.
 */

public interface MsgsContract {
    interface ViewMsgs {
        //Sets ListHandler to use this list.
        void setMsgs(List<Msg> aList);
        void setPresenter(PresenterMsgs aPresenter);
    }
    interface PresenterMsgs {
        //I hope this is fairly self explanatory. Called by View.
        List<Msg> getMsgs(int aCOId);
        //Used in Search Query to filter Msgs by their content
        List<Msg> getMsgsViaBody(int aCOId, String aText);

    }
}
