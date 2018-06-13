package com.jraw.android.capstoneproject.data.model;

import android.content.ContentValues;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.jraw.android.capstoneproject.database.DbSchema.MsgTable;

import java.util.Arrays;

/**
 * Created by JonGaming on 16/04/2018.
 * Parcelabled, as will be sent via Intent Service...
 */

public class Msg extends entity implements Parcelable {

    public enum MSG_TYPES {
        TEXT, IMAGE, VIDEO
    }
    //Of course if its sent then its not delivered, if its delivered its not read...
    public enum RESULTS {
        SENT, DELIVERED, READ, FAILED
    }
    //Which conversation this msg is part of, uses public id...
    @SerializedName("copublicid")
    private long MSCOPublicId = 0;
    //See MSToID above, although knowing who the msg is from is necessary. Remove toId?
    //This is the tel of the person who sent this.
    @SerializedName("fromtel")
    private String MSFromTel;
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

    public Msg(Parcel pc) {
        MSCOPublicId = pc.readLong();
        MSFromTel = pc.readString();
        MSBody = pc.readString();
        MSEventDate = pc.readString();
        MSType = pc.readInt();
        pc.readByteArray(MSData);
        MSResult = pc.readInt();
    }
    @Override
    public void writeToParcel(Parcel pc, int flags) {
        pc.writeLong(MSCOPublicId);
        pc.writeString(MSFromTel);
        pc.writeString(MSBody);
        pc.writeString(MSEventDate);
        pc.writeInt(MSType);
        pc.writeByteArray(MSData);
        pc.writeInt(MSResult);
    }

    public static final Parcelable.Creator<Msg> CREATOR = new Parcelable.Creator<Msg>() {
        public Msg createFromParcel(Parcel pc) {
            return new Msg(pc);
        }
        public Msg[] newArray(int size) {
            return new Msg[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    public void setMSCOPublicId(long aLong) {
        MSCOPublicId = aLong;
    }
    public void setMSFromTel(String aStr) {
        MSFromTel = aStr;
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

    public long getMSCOPublicId() {
        return MSCOPublicId;
    }
    public String getMSFromTel() {return MSFromTel;}
    public String getMSBody() {
        return MSBody;
    }
    public String getBodySnippet() {
        if (MSBody.length()>49) {
            return MSBody.substring(0, 50);
        } else {
            return MSBody;
        }
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
        if (getId()>0) {
            cV.put(MsgTable.Cols.ID,getId());
        }
        if (MSCOPublicId!=0) {
            cV.put(MsgTable.Cols.COPUBLICID,MSCOPublicId);
        }
        if (MSFromTel!=null) {
            cV.put(MsgTable.Cols.FROMTEL,MSFromTel);
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
                ", MSFromTel=" + MSFromTel +
                ", MSBody='" + MSBody + '\'' +
                ", MSEventDate='" + MSEventDate + '\'' +
                ", MSType=" + MSType +
                ", MSData=" + Arrays.toString(MSData) +
                ", MSResult=" + MSResult +
                ", MSCOTitle='" + MSCOTitle + '\'' +
                '}';
    }
}
