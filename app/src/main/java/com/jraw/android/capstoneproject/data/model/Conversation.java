package com.jraw.android.capstoneproject.data.model;


import android.content.ContentValues;
import com.jraw.android.capstoneproject.database.DbSchema.ConversationTable;
import com.jraw.android.capstoneproject.utils.Utils;

/**
 * Created by JonGaming on 16/04/2018.
 */

public class Conversation extends entity {
    private String COTitle;
    private long COPublicId;
    private String COCreatedBy;
    private String CODateCreated;
    private String CODateLastMsg;
    private String COSnippet;
    private int COUnread;
    private int COCount;

    public Conversation() {}

    public void setCOTitle(String aStr) {
        COTitle = aStr;
    }
    public void setCOPublicId(long aPublicId) {COPublicId=aPublicId;}
    public void setCOCreatedBy(String aStr) {
        COCreatedBy = aStr;
    }
    public void setCODateCreated(String aStr) {
        CODateCreated = aStr;
    }
    public void setCODateLastMsg(String aCODateLastMsg) {CODateLastMsg = aCODateLastMsg;}
    public void setCOSnippet(String aCOSnippet) {COSnippet = aCOSnippet;}
    public void setCOUnread(int aCOUnread) {COUnread = aCOUnread;}
    public void setCOCount(int aCOCount) {COCount = aCOCount;}

    public String getCOTitle() {
        return COTitle;
    }
    public long getCOPublicId() {return COPublicId;}
    public String getCOCreatedBy() {
        return COCreatedBy;
    }
    public String getCODateCreated() {
        return CODateCreated;
    }
    public String getCODateLastMsg() {return CODateLastMsg;}
    public String getCOSnippet() {return COSnippet;}
    public int getCOUnread() {return COUnread;}
    public int getCOCount() {return COCount;}

    public ContentValues toCV() {
        try {
            ContentValues cv = new ContentValues();
            if (COTitle!=null) {
                cv.put(ConversationTable.Cols.TITLE,COTitle);
            }
            if (COPublicId>0) {
                cv.put(ConversationTable.Cols.PUBLICID,COPublicId);
            }
            if (COCreatedBy!=null) {
                cv.put(ConversationTable.Cols.CREATEDBY,COCreatedBy);
            }
            if (CODateCreated!=null) {
                cv.put(ConversationTable.Cols.DATECREATED,CODateCreated);
            }
            if (CODateLastMsg!=null) {
                cv.put(ConversationTable.Cols.DATELASTMSG,CODateLastMsg);
            }
            if (COSnippet!=null) {
                cv.put(ConversationTable.Cols.SNIPPET,COSnippet);
            }
            if (COUnread>0) {
                cv.put(ConversationTable.Cols.UNREAD,COUnread);
            }
            if (COCount>0) {
                cv.put(ConversationTable.Cols.COUNT,COCount);
            }
            return cv;
        } catch (Exception e) {
            Utils.logDebug("Problem in Conversation.toCV: "+e.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public String toString() {
        return "Conversation{" +
                "COTitle='" + COTitle + '\'' +
                ", COPublicId=" + COPublicId +
                ", COCreatedBy='" + COCreatedBy + '\'' +
                ", CODateCreated='" + CODateCreated + '\'' +
                ", CODateLastMsg='" + CODateLastMsg + '\'' +
                ", COSnippet='" + COSnippet + '\'' +
                ", COUnread=" + COUnread +
                ", COCount=" + COCount +
                '}';
    }
}