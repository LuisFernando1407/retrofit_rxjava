package com.br.retrofit_rxjava.ui;

import com.br.retrofit_rxjava.ui.base.BaseNavigator;

public interface FlowNavigator extends BaseNavigator {
    void onAlertError(String title, String message);
    void onAlertSuccess(String title, String message);
    void onNetworkConnectionChanged(boolean isConnected);
}