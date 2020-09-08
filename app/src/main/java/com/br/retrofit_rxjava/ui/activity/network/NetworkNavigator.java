package com.br.retrofit_rxjava.ui.activity.network;

import com.br.retrofit_rxjava.ui.FlowNavigator;

public interface NetworkNavigator extends FlowNavigator {
    void startAnimationNoAccessInternet();
    <A> void connectionReestablished(Class<A> activity);
}
