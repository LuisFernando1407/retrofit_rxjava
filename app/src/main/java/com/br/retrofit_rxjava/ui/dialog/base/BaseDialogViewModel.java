package com.br.retrofit_rxjava.ui.dialog.base;

import androidx.lifecycle.ViewModel;

import java.lang.ref.WeakReference;

public abstract class BaseDialogViewModel<N extends BaseDialogNavigator> extends ViewModel {
    private WeakReference<N> navigator = null;
    public N navigator() {
        return this.navigator.get();
    }
    public void setNavigator(N navigator) {
        this.navigator = new WeakReference<>(navigator);
    }
}