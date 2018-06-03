package com.jwar.android.capstoneproject;

import android.support.annotation.NonNull;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jraw.android.capstoneproject.data.repository.ConversationRepository;
import com.jraw.android.capstoneproject.data.repository.MsgRepository;
import com.jraw.android.capstoneproject.data.repository.PeCoRepository;
import com.jraw.android.capstoneproject.data.repository.PersonRepository;
import com.jraw.android.capstoneproject.data.source.local.ConversationLocalDataSource;
import com.jraw.android.capstoneproject.data.source.local.MsgLocalDataSource;
import com.jraw.android.capstoneproject.data.source.local.PeCoLocalDataSource;
import com.jraw.android.capstoneproject.data.source.local.PersonLocalDataSource;
import com.jraw.android.capstoneproject.data.source.remote.BackendApi;
import com.jraw.android.capstoneproject.data.source.remote.MsgRemoteDataSource;
import com.jraw.android.capstoneproject.data.source.remote.PersonRemoteDataSource;
import com.jwar.android.capstoneproject.data.source.local.ProdConversationLocalDataSource;
import com.jwar.android.capstoneproject.data.source.local.ProdMsgLocalDataSource;
import com.jwar.android.capstoneproject.data.source.local.ProdPeCoLocalDataSource;
import com.jwar.android.capstoneproject.data.source.local.ProdPersonLocalDataSource;
import com.jwar.android.capstoneproject.data.source.remote.ProdMsgRemoteDataSource;
import com.jwar.android.capstoneproject.data.source.remote.ProdPersonRemoteDataSource;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by JonGaming on 16/04/2018.
 */

public class Injection {
    public static PeCoRepository providePeCoRepository(@NonNull PeCoLocalDataSource aPeCoLocalDataSource) throws Exception {
        return PeCoRepository.getInstance(aPeCoLocalDataSource);
    }
    public static PersonRepository providePersonRepository(@NonNull PersonLocalDataSource aPersonLocalDataSource,
                                                           @NonNull PersonRemoteDataSource aPersonRemoteDataSource) throws Exception {
        return PersonRepository.getInstance(
                aPersonLocalDataSource,
                aPersonRemoteDataSource);
    }
    public static ConversationRepository provideConversationRepository(@NonNull ConversationLocalDataSource aConversationLocalDataSource) throws Exception {
        return ConversationRepository.getInstance(aConversationLocalDataSource);
    }
    public static MsgRepository provideMsgRepository(@NonNull MsgLocalDataSource aMsgLocalDataSource,
                                                     @NonNull MsgRemoteDataSource aMsgRemoteDataSource,
                                                     @NonNull ConversationLocalDataSource aConversationLocalDataSource) throws Exception {
        return MsgRepository.getInstance(
                aMsgLocalDataSource,
                aMsgRemoteDataSource,
                aConversationLocalDataSource);
    }
    public static ConversationLocalDataSource provideConversationLocalDataSource() throws Exception {
        return ProdConversationLocalDataSource.getInstance();
    }
    public static MsgLocalDataSource provideMsgLocalDataSource() throws Exception {
        return ProdMsgLocalDataSource.getInstance();
    }
    public static MsgRemoteDataSource provideMsgRemoteDataSource(@NonNull BackendApi aBackendApi) {
        return ProdMsgRemoteDataSource.getInstance(aBackendApi);
    }
    public static PersonRemoteDataSource providePersonRemoteDataSource(@NonNull BackendApi aBackendApi) {
        return ProdPersonRemoteDataSource.getInstance(aBackendApi);
    }
    public static PersonLocalDataSource providePersonLocalDataSource() {
        return ProdPersonLocalDataSource.getInstance();
    }
    public static PeCoLocalDataSource providePeCoLocalDataSource() {
        return ProdPeCoLocalDataSource.getInstance();
    }
    public static BackendApi provideBackendApi() throws Exception {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BackendApi.END_POINT)
                .addConverterFactory(GsonConverterFactory.create(provideGson()))
                .build();
        return retrofit.create(BackendApi.class);
    }
    private static Gson provideGson() {
        return new GsonBuilder().create();
    }
    //Sets androidTest expected response for Install. Basically provides a string that
    //bypasses test. Temp measure to avoid tests in install.
    public static String getTestConvTitle() {
        return "install";
    }
    //Sets androidTest expected response for Install. Though not sure about this...
    //Install has user data. So need to figure out way of turning install build tests off?
    public static String getTestMsgBody() {
        return "install";
    }
    //This is for making sure Install AndroidTest of ConversationActivity acts normally,
    //and returns the default value needed to ensure the install triggers if not installed.
    //So false is the default value of IS INSTALLED. I.e. it isnt installed...
    public static boolean getIsInstallDefault() {
        return false;
    }
}
