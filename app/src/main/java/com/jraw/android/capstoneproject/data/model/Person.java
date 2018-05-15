package com.jraw.android.capstoneproject.data.model;

import android.content.ContentValues;
import com.jraw.android.capstoneproject.database.DbSchema.PersonTable;
import com.jraw.android.capstoneproject.utils.Utils;

/**
 * Created by JonGaming on 16/04/2018.
 * Includes persons tel
 */

public class Person extends entity {
    private String PEFname;
    private String PESname;
    //Only using one tel for the moment.
    private String PETelNum;

    private String PEFirebaseToken;

    public Person() {}

    public void setPEFname(String aName) {
        PEFname= aName;
    }
    public void setPESname(String aName) {
        PESname = aName;
    }
    public void setPETelNum(String aPETelNum) {PETelNum = aPETelNum;}
    public void setPEFirebaseToken(String aPEFirebaseToken) {PEFirebaseToken = aPEFirebaseToken;}

    public String getPEFname() {
        return PEFname;
    }
    public String getPESname() {return PESname;}
    public String getPETelNum() {return PETelNum;}
    public String getPEFirebaseToken() {return PEFirebaseToken;}

    public String getFullName() {
        String fullname = PEFname;
        if (PESname!=null) {
            fullname+= " " + PESname;
        }
        return fullname;
    }

    public ContentValues toCV() {
        try {
            ContentValues cv = new ContentValues();
            if (PEFname!=null) {
                cv.put(PersonTable.Cols.FIRSTNAME,PEFname);
            }
            if (PESname!=null) {
                cv.put(PersonTable.Cols.SURNAME,PESname);
            }
            if (PETelNum!=null) {
                cv.put(PersonTable.Cols.TELNUM,PETelNum);
            }
            return cv;
        } catch (Exception e) {
            Utils.logDebug("Problem in Person.toCV: "+e.getLocalizedMessage());
            return null;
        }
    }

    @Override
    public String toString() {
        return "Person{" +
                "PEFname='" + PEFname + '\'' +
                ", PESname='" + PESname + '\'' +
                ", PETelNum='" + PETelNum + '\'' +
                '}';
    }
}
