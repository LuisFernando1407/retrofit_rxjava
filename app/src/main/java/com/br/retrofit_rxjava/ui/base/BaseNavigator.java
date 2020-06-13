package com.br.retrofit_rxjava.ui.base;

import android.os.Bundle;

public interface BaseNavigator {
    <A> void openActivity(Class<A> activity, boolean isFinished, Bundle bundle);
    void onBackPress();
}
