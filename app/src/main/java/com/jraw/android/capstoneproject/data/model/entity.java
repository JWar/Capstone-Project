package com.jraw.android.capstoneproject.data.model;

/**
 * Created by JonGaming on 16/04/2018.
 */

public class entity {
    private int id = 0;
    public entity() {}

    public int getId() {
        return id;
    }

    public void setId(int aId) {
        id = aId;
    }

    public boolean getBool(int aBoolInt) throws Exception{
        switch (aBoolInt) {
            case 0:
                return false;
            case 1:
                return true;
            default:
                throw new Exception("SetBool value not 1 or 0 error");
        }
    }
    public String toString() {
        return super.toString()+ ": " + getId();
    }
    public String returnNullAsString(String aStr) {
        if (aStr!=null) {
            return aStr;
        } else {
            return "null";
        }
    }
}
