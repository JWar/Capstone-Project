package com.jraw.android.capstoneproject.data.model.cursorwrappers;

import android.database.Cursor;
import android.database.CursorWrapper;
import com.jraw.android.capstoneproject.data.model.Msg;
import com.jraw.android.capstoneproject.database.DbSchema.MsgTable;
import com.jraw.android.capstoneproject.utils.Utils;

/**
 * Created by JonGaming on 22/07/2017.
 *
 */

public class MsgCursorWrapper extends CursorWrapper {
    public MsgCursorWrapper(Cursor aCur) {
        super(aCur);
    }

    public Msg getMsg() {
        try {
            Msg msg = new Msg();
            msg.setId(getInt(getColumnIndexOrThrow(MsgTable.Cols.ID)));
            if (getColumnIndex(MsgTable.Cols.COPUBLICID) > -1) {
                if (!isNull(getColumnIndex(MsgTable.Cols.COPUBLICID))) {
                    msg.setMSCOPublicId(getInt(getColumnIndex(MsgTable.Cols.COPUBLICID)));
                }
            }
            if (getColumnIndex(MsgTable.Cols.TOID) > -1) {
                if (!isNull(getColumnIndex(MsgTable.Cols.TOID))) {
                    msg.setMSToId(getInt(getColumnIndex(MsgTable.Cols.TOID)));
                }
            }
            if (getColumnIndex(MsgTable.Cols.FROMTEL) > -1) {
                if (!isNull(getColumnIndex(MsgTable.Cols.FROMTEL))) {
                    msg.setMSFromTel(getInt(getColumnIndex(MsgTable.Cols.FROMTEL)));
                }
            }
            if (getColumnIndex(MsgTable.Cols.BODY) > -1) {
                if (!isNull(getColumnIndex(MsgTable.Cols.BODY))) {
                    msg.setMSBody(getString(getColumnIndex(MsgTable.Cols.BODY)));
                }
            }
            if (getColumnIndex(MsgTable.Cols.EVENTDATE) > -1) {
                if (!isNull(getColumnIndex(MsgTable.Cols.EVENTDATE))) {
                    msg.setMSEventDate(getString(getColumnIndex(MsgTable.Cols.EVENTDATE)));
                }
            }
            if (getColumnIndex(MsgTable.Cols.TYPE) > -1) {
                if (!isNull(getColumnIndex(MsgTable.Cols.TYPE))) {
                    msg.setMSType(getInt(getColumnIndex(MsgTable.Cols.TYPE)));
                }
            }
            if (getColumnIndex(MsgTable.Cols.DATA) > -1) {
                if (!isNull(getColumnIndex(MsgTable.Cols.DATA))) {
                    msg.setMSData(getBlob(getColumnIndex(MsgTable.Cols.DATA)));
                }
            }
            if (getColumnIndex(MsgTable.Cols.RESULT) > -1) {
                if (!isNull(getColumnIndex(MsgTable.Cols.RESULT))) {
                    msg.setMSResult(getInt(getColumnIndex(MsgTable.Cols.RESULT)));
                }
            }
            return msg;
        } catch (Exception e) {
            Utils.logDebug("Error in MsgCursorWrapper.getMsg: " + e.getMessage());
            return null;
        }
    }
}

