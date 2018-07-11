package com.jraw.android.capstoneproject.data.source.remote;

import com.jraw.android.capstoneproject.data.model.Msg;
import com.jraw.android.capstoneproject.data.model.Person;
import com.jraw.android.capstoneproject.utils.Utils;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface BackendApi {

    enum SEARCH_TYPES {
        ALL
    }

    String END_POINT = Utils.URL;

    //Would really have an auth token
    @GET("tbc")
    Call<ResponseServerMsg> getMsgs(@Query("crit") String aCrit,
                              @Query("st") int aSearchType,
                              @Query("userid") String aUserId);

    @POST("storemsg.aj")
    Call<ResponseServerMsgSave> sendMsg(@Body Msg aMsg);

    @POST("storeperson.aj")
    Call<ResponseServerPersonSave> sendPerson(@Body Person aPerson);
}
