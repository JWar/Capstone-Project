package com.jraw.android.capstoneproject.data.source.local;

import android.content.Context;
import android.support.v4.content.CursorLoader;

import com.jraw.android.capstoneproject.data.model.PeCo;

public interface PeCoLocalDataSource {
    //Fairly sure the gets are redundant as this is a reference table. The save is vital though...
//    CursorLoader getPeCosUsingCo(Context aContext, int aPublicId);
//    CursorLoader getPeCosUsingPe(Context aContext, int aPeId);
    long savePeCo(Context aContext, PeCo aPeCo);
}
