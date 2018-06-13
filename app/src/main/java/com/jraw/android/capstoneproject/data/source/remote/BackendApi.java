package com.jraw.android.capstoneproject.data.source.remote;

import com.jraw.android.capstoneproject.data.model.Msg;
import com.jraw.android.capstoneproject.data.model.Person;
import com.jraw.android.capstoneproject.utils.Utils;

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
    ResponseServerMsg getMsgs(@Query("crit") String aCrit,
                              @Query("st") int aSearchType,
                              @Query("userid") String aUserId);

    @POST
    ResponseServerMsgSave sendMsg(@Url String aUrl,
                                  @Body Msg aMsg);

    @POST
    ResponseServerPersonSave sendPerson(@Url String aUrl,
                                        @Body Person aPerson);
}
