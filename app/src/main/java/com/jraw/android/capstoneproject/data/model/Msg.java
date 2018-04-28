package com.jraw.android.capstoneproject.data.model;

import android.content.ContentValues;

import com.google.gson.annotations.SerializedName;
import com.jraw.android.capstoneproject.database.DbSchema.MsgTable;

import java.util.Arrays;

/**
 * Created by JonGaming on 16/04/2018.
 *
 */

public class Msg extends entity {

    public enum MSG_TYPES {
        TEXT, IMAGE, VIDEO
    }
    //Of course if its sent then its not delivered, if its delivered its not read...
    public enum RESULTS {
        SENT, DELIVERED, READ, FAILED
    }
    //Which conversation this msg is part of, uses public id...
    @SerializedName("copublicid")
    private int MSCOPublicId = 0;
    //Supposed to be the ID this msg is to but redundant given the way COID handles everything
    @SerializedName("toid")
    private int MSToId = 0;
    //See MSToID above, although knowing who the msg is from is necessary. Remove toId?
    @SerializedName("fromid")
    private int MSFromId = 0;
    //Content of Msg
    @SerializedName("body")
    private String MSBody;
    //Msg date
    @SerializedName("eventdate")
    private String MSEventDate;
    //Type of msg? Text,image,video?
    @SerializedName("type")
    private int MSType = 0;
    //Data?? Presumably if this msg has image or video
    //Im assuming it will be the uri...
    @SerializedName("data")
    private byte[] MSData;
    //Read,sent,delivered etc...
    @SerializedName("result")
    private int MSResult;
    //Holds title of conversation. This is not needed in the database as it can be used to create a new conversation
    @SerializedName("title")
    private String MSCOTitle;

    public Msg() {}

    public void setMSCOPublicId(int aInt) {
        MSCOPublicId = aInt;
    }
    public void setMSToId(int aInt) {
        MSToId = aInt;
    }
    public void setMSFromId(int aInt) {
        MSFromId = aInt;
    }
    public void setMSBody(String aStr) {
        MSBody = aStr;
    }
    public void setMSEventDate(String aStr) {
        MSEventDate = aStr;
    }
    public void setMSType(int aInt) {
        MSType = aInt;
    }
    public void setMSData(byte[] aBytes) {MSData = aBytes;}
    public void setMSResult(int aInt) {
        MSResult = aInt;
    }

    public void setMSCOTitle(String aMSCOTitle) {MSCOTitle = aMSCOTitle;}
    public String getMSCOTitle() {return MSCOTitle;}

    public int getMSCOPublicId() {
        return MSCOPublicId;
    }
    public int getMSToId() {
        return MSToId;
    }
    public int getMSFromId() {
        return MSFromId;
    }
    public String getMSBody() {
        return MSBody;
    }
    public String getMSEventDate() {
        return MSEventDate;
    }
    public int getMSType() {
        return MSType;
    }
    public byte[] getMSData() {
        return MSData;
    }
    public int getMSResult() {
        return MSResult;
    }

    //Returns this Msg as a ContentValues object
    public ContentValues toCV() {
        ContentValues cV = new ContentValues();
        cV.put(MsgTable.Cols.ID,getId());
        if (MSCOPublicId!=0) {
            cV.put(MsgTable.Cols.COPUBLICID,MSCOPublicId);
        }
        if (MSToId!=0) {
            cV.put(MsgTable.Cols.TOID,MSToId);
        }
        if (MSFromId!=0) {
            cV.put(MsgTable.Cols.FROMID,MSFromId);
        }
        if (MSBody!=null) {
            cV.put(MsgTable.Cols.BODY,MSBody);
        }
        if (MSEventDate!=null) {
            cV.put(MsgTable.Cols.EVENTDATE,MSEventDate);
        }
        if (MSType!=0) {
            cV.put(MsgTable.Cols.TYPE,MSType);
        }
        if (MSData!=null) {
            cV.put(MsgTable.Cols.DATA,MSData);
        }
        if (MSResult!=0) {
            cV.put(MsgTable.Cols.RESULT,MSResult);
        }
        return cV;
    }

    @Override
    public String toString() {
        return "Msg{" +
                "MSCOPublicId=" + MSCOPublicId +
                ", MSToId=" + MSToId +
                ", MSFromId=" + MSFromId +
                ", MSBody='" + MSBody + '\'' +
                ", MSEventDate='" + MSEventDate + '\'' +
                ", MSType=" + MSType +
                ", MSData=" + Arrays.toString(MSData) +
                ", MSResult=" + MSResult +
                ", MSCOTitle='" + MSCOTitle + '\'' +
                '}';
    }
}
