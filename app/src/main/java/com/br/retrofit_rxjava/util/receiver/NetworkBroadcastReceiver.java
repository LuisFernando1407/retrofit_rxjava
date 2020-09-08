package com.br.retrofit_rxjava.util.receiver;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.br.retrofit_rxjava.ui.FlowNavigator;

public class NetworkBroadcastReceiver extends BroadcastReceiver {

    public static FlowNavigator listener;

    @SuppressLint("UnsafeProtectedBroadcastReceiver")
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null
                && activeNetwork.isConnectedOrConnecting();

        if (listener != null) {
            listener.onNetworkConnectionChanged(isConnected);
        }
    }
}