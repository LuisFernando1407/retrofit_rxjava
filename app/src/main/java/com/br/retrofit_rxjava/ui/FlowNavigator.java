package com.br.retrofit_rxjava.ui;

import com.br.retrofit_rxjava.ui.base.BaseNavigator;
import com.br.retrofit_rxjava.ui.dialog.listener.ConfirmDialogListener;

public interface FlowNavigator extends BaseNavigator {
    void onAlertError(String title, String message);
    void onAlertSuccess(String title, String message);
    void onAlertConfirm(int title,
                        int message,
                        int[] buttons,
                        boolean isCancelableInTouch,
                        Object payload,
                        ConfirmDialogListener listener);
    void onNetworkConnectionChanged(boolean isConnected);
}