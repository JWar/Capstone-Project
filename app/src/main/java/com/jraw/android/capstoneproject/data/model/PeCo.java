package com.jraw.android.capstoneproject.data.model;

/**
 * Created by JonGaming on 16/04/2018.
 * Links persons with conversation.
 */

public class PeCo extends entity {
    private int PCPEId;
    private int PCCOPublicId;

    public int getPCPEId() {
        return PCPEId;
    }
    public int getPCCOPublicId() {
        return PCCOPublicId;
    }
    public void setPCPEId(int aPCPEId) {
        PCPEId=aPCPEId;
    }
    public void setPCCOPublicId(int aPCCOPublicId) {
        PCCOPublicId=aPCCOPublicId;
    }
}
