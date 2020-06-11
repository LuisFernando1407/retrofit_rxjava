package com.br.retrofit_rxjava;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.br.retrofit_rxjava.util.receiver.NetworkBroadcastReceiver;

import timber.log.Timber;

public class RetrofitRxJavaApplication extends Application {
    /**
     * Context
     */
    @SuppressLint("StaticFieldLeak")
    private static Context context;

    /* Instance */
    @SuppressLint("StaticFieldLeak")
    private static RetrofitRxJavaApplication instance;

    @Override
    public void onCreate() {
        super.onCreate();

        /* Config Timber */
        if(BuildConfig.DEBUG){
            Timber.plant(new Timber.DebugTree());
        }

        /* Set context */
        RetrofitRxJavaApplication.context = getApplicationContext();

        /* Instance */
        RetrofitRxJavaApplication.instance = this;
    }

    public static synchronized RetrofitRxJavaApplication of() {
        return RetrofitRxJavaApplication.instance;
    }

    public void setNetworkListener(NetworkBroadcastReceiver.ConnectivityReceiverListener listener){
        NetworkBroadcastReceiver.connectivityReceiverListener = listener;
    }

    public Context getContext() {
        return RetrofitRxJavaApplication.context;
    }
}