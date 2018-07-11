package com.jraw.android.capstoneproject.database;

import android.net.Uri;

/**
 * Created by JonGaming on 29/06/2017.
 *
 */

public class DbSchema {

    public static final String CONTENT_AUTHORITY = "com.jraw.android.capstoneproject";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://"+ CONTENT_AUTHORITY);

    public static final String PATH_PERSON = "person";
    public static final String PATH_CONVERSATION = "conversation";
    public static final String PATH_MSG = "msg";
    public static final String PATH_PECO = "peco";

    public static final class PersonTable {
        public static final String NAME = "person";
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_PERSON)
                .build();

        public static final class Cols {
            public static final String ID = "ID";
            public static final String FIRSTNAME = "PEFirstname";
            public static final String SURNAME = "PESurname";
            public static final String TELNUM = "PETelNum";
        }
    }

    public static final class MsgTable {
        public static final String NAME = "msg";

        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_MSG)
                .build();

        public static final class Cols {
            public static final String ID = "ID";
            public static final String COPUBLICID = "MSCOPublicID";
            public static final String FROMTEL = "MSFromTel";
            public static final String TOTELS = "MSToTels";
            public static final String BODY = "MSBody";
            public static final String EVENTDATE = "MSEventDate";
            public static final String TYPE = "MSType";
            public static final String DATA = "MSData";
            public static final String RESULT = "MSResult";
        }
    }

    public static final class ConversationTable {
        public static final String NAME = "conversation";
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_CONVERSATION)
                .build();
        public static final class Cols {
            public static final String ID = "ID";
            public static final String TITLE = "COTitle";
            //This is the public id of the conversation shared by all persons IN the conversation.
            //Cant use the id as this could clash.
            public static final String PUBLICID = "COPublicId";
            //This holds the tel number of the person who created the conversation? Or the id? Or the name?
            public static final String CREATEDBY = "COCreatedBy";
            public static final String DATECREATED = "CODateCreated";
            //Uses date of newest msg.
            public static final String DATELASTMSG = "CODateLastMsg";
            //Displays bit of the latest msg.
            public static final String SNIPPET = "COSnippet";
            //Num of unread msgs. 0 means none (... funnily enough)
            public static final String UNREAD = "COUnread";
            //Number of msgs in converssation. So widget can be easily updated.
            public static final String COUNT = "COCount";
        }
    }
    //This is needed to link Persons with Conversation. Basically all people in the conversation!
    public static final class PeCoTable {
        public static final String NAME = "peco";
        public static final Uri CONTENT_URI = BASE_CONTENT_URI.buildUpon()
                .appendPath(PATH_PECO)
                .build();
        public static final class Cols {
            public static final String ID = "ID";
            public static final String PEID = "PCPEId";
            public static final String COPUBLICID = "PCCOPublicId";
        }
    }
}
