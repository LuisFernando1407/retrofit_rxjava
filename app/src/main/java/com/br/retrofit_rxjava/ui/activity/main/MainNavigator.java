package com.br.retrofit_rxjava.ui.activity.main;

import com.br.retrofit_rxjava.data.model.Crypto;
import com.br.retrofit_rxjava.ui.activity.FlowNavigator;

public interface MainNavigator extends FlowNavigator {
    void beforeRequest();
    void afterRequest();
    void showCoins(Crypto crypto);
    void showErrorOrEmptyList();
}
