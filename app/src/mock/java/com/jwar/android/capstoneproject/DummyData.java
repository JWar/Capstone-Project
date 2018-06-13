package com.jwar.android.capstoneproject;


import com.jraw.android.capstoneproject.data.model.Conversation;
import com.jraw.android.capstoneproject.data.model.Msg;
import com.jraw.android.capstoneproject.data.model.PeCo;
import com.jraw.android.capstoneproject.data.model.Person;
import com.jraw.android.capstoneproject.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Mocks remote data to control test data.
 */
public class DummyData {
    public static List<Msg> getRemoteMsgs() {
        return null;
    }
    public static List<Msg> getMsgs() {
        List<Msg> toReturn = new ArrayList<>();
        Msg msg;
        //First conversation
        msg = new Msg();
        msg.setMSBody("Hello fellow human");
        msg.setMSCOPublicId(1);
        msg.setMSFromTel(Utils.THIS_USER_TEL);
        msg.setMSEventDate("2018/01/11 16:20:00");
        msg.setMSType(Msg.MSG_TYPES.TEXT.ordinal());
        msg.setMSResult(Msg.RESULTS.READ.ordinal());
        toReturn.add(msg);
        msg = new Msg();
        msg.setMSBody("Hello back");
        msg.setMSCOPublicId(1);
        msg.setMSFromTel(Utils.USER_B);
        msg.setMSEventDate("2018/01/11 16:22:00");
        msg.setMSType(Msg.MSG_TYPES.TEXT.ordinal());
        msg.setMSResult(Msg.RESULTS.READ.ordinal());
        toReturn.add(msg);
        msg = new Msg();
        msg.setMSBody("How goes it?");
        msg.setMSCOPublicId(1);
        msg.setMSFromTel(Utils.THIS_USER_TEL);
        msg.setMSEventDate("2018/01/11 16:23:00");
        msg.setMSType(Msg.MSG_TYPES.TEXT.ordinal());
        msg.setMSResult(Msg.RESULTS.READ.ordinal());
        toReturn.add(msg);
        msg = new Msg();
        msg.setMSBody("It goes");
        msg.setMSCOPublicId(1);
        msg.setMSFromTel(Utils.USER_B);
        msg.setMSEventDate("2018/01/11 16:24:00");
        msg.setMSType(Msg.MSG_TYPES.TEXT.ordinal());
        msg.setMSResult(Msg.RESULTS.READ.ordinal());
        toReturn.add(msg);
        msg = new Msg();
        msg.setMSBody("And yourself?");
        msg.setMSCOPublicId(1);
        msg.setMSFromTel(Utils.USER_B);
        msg.setMSEventDate("2018/01/11 16:24:00");
        msg.setMSType(Msg.MSG_TYPES.TEXT.ordinal());
        msg.setMSResult(Msg.RESULTS.READ.ordinal());
        toReturn.add(msg);
        msg = new Msg();
        msg.setMSBody("I'm here");
        msg.setMSCOPublicId(1);
        msg.setMSFromTel(Utils.THIS_USER_TEL);
        msg.setMSEventDate("2018/01/11 16:25:00");
        msg.setMSType(Msg.MSG_TYPES.TEXT.ordinal());
        msg.setMSResult(Msg.RESULTS.READ.ordinal());
        toReturn.add(msg);
        msg = new Msg();
        msg.setMSBody("Not there though?");
        msg.setMSCOPublicId(1);
        msg.setMSFromTel(Utils.USER_B);
        msg.setMSEventDate("2018/01/11 16:26:00");
        msg.setMSType(Msg.MSG_TYPES.TEXT.ordinal());
        msg.setMSResult(Msg.RESULTS.READ.ordinal());
        toReturn.add(msg);
        msg = new Msg();
        msg.setMSBody("Where?");
        msg.setMSCOPublicId(1);
        msg.setMSFromTel(Utils.THIS_USER_TEL);
        msg.setMSEventDate("2018/01/11 16:27:00");
        msg.setMSType(Msg.MSG_TYPES.TEXT.ordinal());
        msg.setMSResult(Msg.RESULTS.READ.ordinal());
        toReturn.add(msg);
        msg = new Msg();
        msg.setMSBody("Exactly");
        msg.setMSCOPublicId(1);
        msg.setMSFromTel(Utils.USER_B);
        msg.setMSEventDate("2018/01/11 16:28:00");
        msg.setMSType(Msg.MSG_TYPES.TEXT.ordinal());
        msg.setMSResult(Msg.RESULTS.READ.ordinal());
        toReturn.add(msg);
        msg = new Msg();
        msg.setMSBody("...");
        msg.setMSCOPublicId(1);
        msg.setMSFromTel(Utils.USER_C);
        msg.setMSEventDate("2018/01/11 16:29:00");
        msg.setMSType(Msg.MSG_TYPES.TEXT.ordinal());
        msg.setMSResult(Msg.RESULTS.READ.ordinal());
        toReturn.add(msg);
        //Conv two
        msg = new Msg();
        msg.setMSBody("Hey there");
        msg.setMSCOPublicId(2);
        msg.setMSFromTel(Utils.THIS_USER_TEL);
        msg.setMSEventDate("2018/01/11 16:24:00");
        msg.setMSType(Msg.MSG_TYPES.TEXT.ordinal());
        msg.setMSResult(Msg.RESULTS.READ.ordinal());
        toReturn.add(msg);
        msg = new Msg();
        msg.setMSBody("Hi!");
        msg.setMSCOPublicId(2);
        msg.setMSFromTel(Utils.USER_D);
        msg.setMSEventDate("2018/01/11 16:25:00");
        msg.setMSType(Msg.MSG_TYPES.TEXT.ordinal());
        msg.setMSResult(Msg.RESULTS.READ.ordinal());
        toReturn.add(msg);
        msg = new Msg();
        msg.setMSBody("Was really nice seeing you!");
        msg.setMSCOPublicId(2);
        msg.setMSFromTel(Utils.USER_D);
        msg.setMSEventDate("2018/01/11 16:25:05");
        msg.setMSType(Msg.MSG_TYPES.TEXT.ordinal());
        msg.setMSResult(Msg.RESULTS.READ.ordinal());
        toReturn.add(msg);
        msg = new Msg();
        msg.setMSBody("You too :)");
        msg.setMSCOPublicId(2);
        msg.setMSFromTel(Utils.THIS_USER_TEL);
        msg.setMSEventDate("2018/01/11 16:25:15");
        msg.setMSType(Msg.MSG_TYPES.TEXT.ordinal());
        msg.setMSResult(Msg.RESULTS.READ.ordinal());
        toReturn.add(msg);
        msg = new Msg();
        msg.setMSBody("What time did you make it back home?");
        msg.setMSCOPublicId(2);
        msg.setMSFromTel(Utils.USER_D);
        msg.setMSEventDate("2018/01/11 16:26:30");
        msg.setMSType(Msg.MSG_TYPES.TEXT.ordinal());
        msg.setMSResult(Msg.RESULTS.READ.ordinal());
        toReturn.add(msg);
        msg = new Msg();
        msg.setMSBody(":)");
        msg.setMSCOPublicId(2);
        msg.setMSFromTel(Utils.USER_D);
        msg.setMSEventDate("2018/01/11 16:27:00");
        msg.setMSType(Msg.MSG_TYPES.TEXT.ordinal());
        msg.setMSResult(Msg.RESULTS.READ.ordinal());
        toReturn.add(msg);
        return toReturn;
    }
    public static List<Conversation> getConversations() {
        List<Conversation> toReturn = new ArrayList<>();
        Conversation conv;
        conv = new Conversation();
        conv.setId(1);
        conv.setCOPublicId(1);
        conv.setCOTitle("Mad bantz");
        conv.setCOCreatedBy("USER A");
        conv.setCODateCreated("2018/01/11 16:23:00");
        toReturn.add(conv);
        conv = new Conversation();
        conv.setId(2);
        conv.setCOPublicId(2);
        conv.setCOTitle("Bantz that are mad");
        conv.setCOCreatedBy("USER A");
        conv.setCODateCreated("2018/01/11 16:23:30");
        toReturn.add(conv);
        return toReturn;
    }
    public static List<Person> getPersons() {
        List<Person> toReturn = new ArrayList<>();
        Person person = new Person();
        person.setId(1);
        person.setPEFname("First");
        person.setPESname("Person");
        person.setPETelNum("01234567890");
        toReturn.add(person);
        person = new Person();
        person.setId(2);
        person.setPEFname("Second");
        person.setPESname("Person");
        person.setPETelNum("09876543210");
        toReturn.add(person);
        return toReturn;
    }
    public static List<PeCo> getPeCos() {
        List<PeCo> toReturn = new ArrayList<>();
        PeCo peCo = new PeCo();
        peCo.setId(1);
        peCo.setPCCOPublicId(1);
        peCo.setPCPEId(1);
        toReturn.add(peCo);
        peCo = new PeCo();
        peCo.setId(2);
        peCo.setPCCOPublicId(2);
        peCo.setPCPEId(2);
        toReturn.add(peCo);
        return toReturn;
    }
}
