package com.jraw.android.capstoneproject.data.model;

/**
 * Created by JonGaming on 16/04/2018.
 * Includes persons tel
 */

public class Person extends entity {
    private String PEFname;
    private String PESname;
    //Only using one tel for the moment.
    private String PETelNum;

    public Person() {}

    public void setPEFname(String aName) {
        PEFname= aName;
    }
    public void setPESname(String aName) {
        PESname = aName;
    }
    public String getPEFname() {
        return PEFname;
    }
    public String getPESname() {return PESname;}
    public String getPETelNum() {return PETelNum;}
    public void setPETelNum(String aPETelNum) {PETelNum = aPETelNum;}
}
