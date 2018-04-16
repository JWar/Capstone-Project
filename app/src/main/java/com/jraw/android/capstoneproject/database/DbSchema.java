package com.jraw.android.capstoneproject.database;

/**
 * Created by JonGaming on 29/06/2017.
 *
 */

public class DbSchema {
    /**
     * Created by JonGaming on 25/11/2016.
     * Defines/handles database
     */

    public static final class PersonTable {
        public static final String NAME = "person";

        public static final class Cols {
            public static final String ID = "ID";
            public static final String FIRSTNAME = "PEFirstname";
            public static final String SURNAME = "PESurname";
        }
    }

    public static final class MsgTable {
        public static final String NAME = "msg";

        public static final class Cols {
            public static final String ID = "ID";
            public static final String COPUBLICID = "MSCOPublicID";
            public static final String TOID = "MSToID";
            public static final String FROMID = "MSFromID";
            public static final String BODY = "MSBody";
            public static final String EVENTDATE = "MSEventDate";
            public static final String TYPE = "MSType";
            public static final String DATA = "MSData";
            public static final String RESULT = "MSResult";
        }
    }

    public static final class ConversationTable {
        public static final String NAME = "conversation";

        public static final class Cols {
            public static final String ID = "ID";
            public static final String TITLE = "COTitle";
            //This is the public id of the conversation shared by all persons IN the conversation.
            //Cant use the id as this could clash.
            public static final String PUBLICID = "COPublicId";
            //This holds the tel number of the person who created the conversation? Or the id? Or the name?
            public static final String CREATEDBY = "COCreatedBy";
            public static final String DATECREATED = "CODateCreated";
        }
    }
    //This is needed to link Persons with Conversation. Basically all people in the conversation!
    public static final class PeCoTable {
        public static final String NAME = "peco";

        public static final class Cols {
            public static final String ID = "ID";
            public static final String PEID = "PCPEId";
            public static final String COPUBLICID = "PCCOPublicId";
        }
    }
}
