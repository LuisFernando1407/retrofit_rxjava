package com.br.retrofit_rxjava.ui.activity.main;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.br.retrofit_rxjava.RetrofitRxJavaApplication;
import com.br.retrofit_rxjava.data.api.callback.Callback;
import com.br.retrofit_rxjava.data.model.Crypto;
import com.br.retrofit_rxjava.ui.base.BaseViewModel;
import com.br.retrofit_rxjava.ui.activity.main.adapter.MainAdapter;
import com.br.retrofit_rxjava.util.observer.ObserverTextWatcher;
import com.br.retrofit_rxjava.util.observer.ObserverTextWatcherCallback;

public class MainViewModel extends BaseViewModel<MainNavigator> implements ObserverTextWatcherCallback {
    /* Repository */
    private MainRepository mainRepository = new MainRepository();
    public MainAdapter mainAdapter = null;

    /* Message target */
    public final int TARGET = 1;

    /* Observer search */
    public ObserverTextWatcher observerTextWatcher = ObserverTextWatcher.of(500, this);

    public void requestCrypto(String crypto){
        /* Callback */
        Callback<Crypto, Throwable> callback = new Callback<Crypto, Throwable>() {
            @Override
            public void onSuccess(Crypto response) {
                if(response != null) {
                    mainAdapter = MainAdapter.of(
                            RetrofitRxJavaApplication.of().getContext(),
                            response.ticker.markets
                    );

                    navigator().afterRequest();
                    navigator().showCoins();
                    return;
                }

                dispatchError();
            }

            @Override
            public void onError(Throwable throwable) {
                dispatchError();
            }
        };

        this.navigator().beforeRequest();
        this.watchDisposable(this.mainRepository.getCoins(crypto, callback));
    }

    private void dispatchError(){
        this.navigator().afterRequest();
        this.navigator().showErrorOrEmptyList();
    }

    @Override
    public void onSubmit(String text) {
        Handler handler = new Handler(Looper.getMainLooper()) {
            @Override
            public void handleMessage(Message message) {
                mainAdapter.getFilter().filter(message.obj.toString());
            }
        };

        /*
            @Param 1 - Target of message
            @Param 2 - Object to send
         */
        Message message = handler.obtainMessage(TARGET, text);
        message.sendToTarget();
    }
}