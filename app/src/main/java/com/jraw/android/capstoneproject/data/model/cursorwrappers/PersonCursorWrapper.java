package com.jraw.android.capstoneproject.data.model.cursorwrappers;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.jraw.android.capstoneproject.data.model.Person;
import com.jraw.android.capstoneproject.database.DbSchema.PersonTable;
import com.jraw.android.capstoneproject.utils.Utils;

/**
 * Created by JonGaming on 22/07/2017.
 *
 */

public class PersonCursorWrapper extends CursorWrapper {
    public PersonCursorWrapper(Cursor aCur) {
        super(aCur);
    }

    public Person getPerson() {
        try {
            Person per = new Person();
            per.setId(getInt(getColumnIndexOrThrow(PersonTable.Cols.ID)));
            if (getColumnIndex(PersonTable.Cols.FIRSTNAME) > -1) {
                if (!isNull(getColumnIndex(PersonTable.Cols.FIRSTNAME))) {
                    per.setPEFname(getString(getColumnIndex(PersonTable.Cols.FIRSTNAME)));
                }
            }
            if (getColumnIndex(PersonTable.Cols.SURNAME) > -1) {
                if (!isNull(getColumnIndex(PersonTable.Cols.SURNAME))) {
                    per.setPESname(getString(getColumnIndex(PersonTable.Cols.SURNAME)));
                }
            }
            return per;
        } catch (Exception e) {
            Utils.logDebug("Error in PersonCursorWrapper.getPerson: " + e.getMessage());
            return null;
        }
    }
}
