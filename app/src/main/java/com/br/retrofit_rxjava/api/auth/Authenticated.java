package com.br.retrofit_rxjava.api.auth;

import com.br.retrofit_rxjava.api.constants.APIConstants;
import com.br.retrofit_rxjava.util.Util;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Authenticated {
    protected static Retrofit retrofit;

    private static final int CONNECTION_TIMEOUT = 20*1000;

    private OkHttpClient client = new OkHttpClient.Builder().addInterceptor(chain -> {

        Request newRequest = chain.request();

        if(Util.getApiToken() != null){
            newRequest = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer " + Util.getApiToken())
                    .build();
        }

        return chain.proceed(newRequest);
    }).connectTimeout(CONNECTION_TIMEOUT, TimeUnit.MINUTES).readTimeout(1, TimeUnit.MINUTES).build();

    protected void setupRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl(APIConstants.BASE_APP)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }
}
