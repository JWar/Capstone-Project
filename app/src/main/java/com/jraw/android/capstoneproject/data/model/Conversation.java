package com.jraw.android.capstoneproject.data.model;


/**
 * Created by JonGaming on 16/04/2018.
 */

public class Conversation extends entity {
    private String COTitle;
    private int COPublicId;
    private String COCreatedBy;
    private String CODateCreated;

    public Conversation() {}

    public void setCOTitle(String aStr) {
        COTitle = aStr;
    }
    public void setCOPublicId(int aPublicId) {COPublicId=aPublicId;}
    public void setCOCreatedBy(String aStr) {
        COCreatedBy = aStr;
    }
    public void setCODateCreated(String aStr) {
        CODateCreated = aStr;
    }
    public String getCOTitle() {
        return COTitle;
    }
    public int getCOPublicId() {return COPublicId;}
    public String getCOCreatedBy() {
        return COCreatedBy;
    }
    public String getCODateCreated() {
        return CODateCreated;
    }
}