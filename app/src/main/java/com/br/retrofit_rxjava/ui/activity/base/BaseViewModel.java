package com.br.retrofit_rxjava.ui.activity.base;

import androidx.lifecycle.ViewModel;

import java.lang.ref.WeakReference;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BaseViewModel<N extends BaseNavigator> extends ViewModel {

    private WeakReference<N> navigator = null;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public CompositeDisposable compositeDisposable() {
        return compositeDisposable;
    }

    public N navigator(){
        return this.navigator.get();
    }

    public void setNavigator(N navigator){
        this.navigator = new WeakReference<>(navigator);
    }

    public void watchDisposable(Disposable disposable){
        if(disposable != null) this.compositeDisposable.add(disposable);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        /* Allows you to free resources and threads allocated to Observer through the "dispose" method */
        compositeDisposable.dispose();
        /* If you are doing multiple subscriptions, it is very important to clear it to avoid memory leaks */
        //compositeDisposable.clear()
    }
}
