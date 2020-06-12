package com.br.retrofit_rxjava.ui.activity.splash;

import android.content.BroadcastReceiver;
import android.content.IntentFilter;

import com.br.retrofit_rxjava.ui.FlowNavigator;

public interface SplashNavigator extends FlowNavigator {
    void onRegisterReceiver(BroadcastReceiver broadcastReceiver, IntentFilter intentFilter);
    void onUnregisterReceiver(BroadcastReceiver broadcastReceiver);

    void startAnimationInitial();
    void startAnimationNoAccessInternet();
    void connectionReestablished();

    void redirectToHome();
}