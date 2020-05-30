package com.br.retrofit_rxjava;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import timber.log.Timber;

public class RetrofitRxJavaApplication extends Application {

    /**
     * Instance
     */
    @SuppressLint("StaticFieldLeak")
    private static Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        /* Config Timber */
        if(BuildConfig.DEBUG){
            Timber.plant(new Timber.DebugTree());
        }

        /* Set context */
        RetrofitRxJavaApplication.context = getApplicationContext();

    }

    public static Context getInstance() {
        return RetrofitRxJavaApplication.context;
    }
}