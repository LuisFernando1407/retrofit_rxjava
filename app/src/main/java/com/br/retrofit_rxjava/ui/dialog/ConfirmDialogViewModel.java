package com.br.retrofit_rxjava.ui.dialog;

import com.br.retrofit_rxjava.ui.dialog.base.BaseDialogViewModel;
import com.br.retrofit_rxjava.ui.dialog.listener.ConfirmDialogListener;

public class ConfirmDialogViewModel extends BaseDialogViewModel<ConfirmDialogNavigator> {

    private ConfirmDialogListener listener;
    private Object payload;

    public String textTitle;
    public String textMessage;
    public String textButtonCancel;
    public String textButtonSuccess;

    public void dismissPress(){
        this.navigator().onDismiss();
    }

    public void confirmPress(){
        this.listener.onConfirm(this.payload);
    }

    public void setData(
            String textTitle, String textMessage, String textButtonCancel,
            String textButtonSuccess,
            ConfirmDialogListener listener, Object payload){

        this.textTitle = textTitle;
        this.textMessage = textMessage;
        this.textButtonCancel = textButtonCancel;
        this.textButtonSuccess = textButtonSuccess;

        this.listener = listener;
        this.payload = payload;
    }
}