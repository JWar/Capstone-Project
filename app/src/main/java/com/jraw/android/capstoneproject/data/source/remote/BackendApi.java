package com.jraw.android.capstoneproject.data.source.remote;

import org.json.JSONObject;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface BackendApi {

        enum SEARCH_TYPES {
            ALL
        }

        String END_POINT = "TBC";

        //Would really have an auth token
        @GET("tbc")
        ResponseServerMsg getMsgs(@Query("crit") String aCrit,
                                  @Query("st") int aSearchType,
                                  @Query("userid") String aUserId);

        @POST
        JSONObject sendMsg(@Url String aUrl,
                            @Body String aMsg);
}
