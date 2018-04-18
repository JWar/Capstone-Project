package com.jwar.android.capstoneproject;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jraw.android.capstoneproject.data.repository.ConversationRepository;
import com.jraw.android.capstoneproject.data.repository.MsgRepository;
import com.jraw.android.capstoneproject.data.source.local.ConversationLocalDataSource;
import com.jraw.android.capstoneproject.data.source.local.MsgLocalDataSource;
import com.jraw.android.capstoneproject.data.source.remote.BackendApi;
import com.jraw.android.capstoneproject.data.source.remote.MsgRemoteDataSource;
import com.jwar.android.capstoneproject.data.source.local.MockConversationLocalDataSource;
import com.jwar.android.capstoneproject.data.source.local.MockMsgLocalDataSource;
import com.jwar.android.capstoneproject.data.source.remote.MockMsgRemoteDataSource;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by JonGaming on 16/04/2018.
 */

public class Injection {
    public static ConversationRepository provideConversationRepository(@NonNull Context aContext) throws Exception {
        return ConversationRepository.getInstance(provideConversationLocalDataSource(aContext));
    }
    public static MsgRepository provideMsgRepository(@NonNull Context aContext,
                                                     @NonNull BackendApi aBackendApi) throws Exception {
        return MsgRepository.getInstance(provideMsgLocalDataSource(aContext),
                provideMsgRemoteDataSource(aBackendApi));
    }
    public static ConversationLocalDataSource provideConversationLocalDataSource(@NonNull Context aContext) throws Exception {
        return MockConversationLocalDataSource.getInstance(aContext);
    }
    public static MsgLocalDataSource provideMsgLocalDataSource(@NonNull Context aContext) throws Exception {
        return MockMsgLocalDataSource.getInstance(aContext);
    }
    public static MsgRemoteDataSource provideMsgRemoteDataSource(@NonNull BackendApi aBackendApi) {
        return MockMsgRemoteDataSource.getInstance(aBackendApi);
    }
    public static BackendApi provideBackendApi() throws Exception {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BackendApi.END_POINT)
                .addConverterFactory(GsonConverterFactory.create(provideGson()))
                .build();
        return retrofit.create(BackendApi.class);
    }
    public static Gson provideGson() {
        return new GsonBuilder().create();
    }
}
