package com.jraw.android.capstoneproject.data.model;

import android.content.ContentValues;

import com.jraw.android.capstoneproject.database.DbSchema.PeCoTable;
import com.jraw.android.capstoneproject.utils.Utils;

/**
 * Created by JonGaming on 16/04/2018.
 * Links persons with conversation.
 */

public class PeCo extends entity {
    private int PCPEId;
    private long PCCOPublicId;

    public int getPCPEId() {
        return PCPEId;
    }
    public long getPCCOPublicId() {
        return PCCOPublicId;
    }
    public void setPCPEId(int aPCPEId) {
        PCPEId=aPCPEId;
    }
    public void setPCCOPublicId(long aPCCOPublicId) {
        PCCOPublicId=aPCCOPublicId;
    }

    public ContentValues toCV() {
        try {
            ContentValues cv = new ContentValues();
            if (PCPEId>0) {
                cv.put(PeCoTable.Cols.PEID,PCPEId);
            }
            if (PCCOPublicId>0) {
                cv.put(PeCoTable.Cols.COPUBLICID,PCCOPublicId);
            }
            return cv;
        } catch (Exception e) {
            Utils.logDebug("Problem in PeCo.toCV: "+e.getLocalizedMessage());
            return null;
        }
    }
}
