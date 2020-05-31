package com.br.retrofit_rxjava.ui.activity.main;

import com.br.retrofit_rxjava.ui.FlowNavigator;

public interface MainNavigator extends FlowNavigator {
    void beforeRequest();

    void afterRequest();

    void showCoins();

    void showErrorOrEmptyList();
}