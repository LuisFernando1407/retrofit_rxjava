package com.br.retrofit_rxjava.ui.activity.main;

import com.br.retrofit_rxjava.data.api.callback.Callback;
import com.br.retrofit_rxjava.data.model.Crypto;
import com.br.retrofit_rxjava.ui.activity.base.BaseViewModel;

public class MainViewModel extends BaseViewModel<MainNavigator> {
    /* Repository */
    private MainRepository mainRepository = new MainRepository();

    public void requestCrypto(String crypto){
        /* Callback */
        Callback<Crypto, Throwable> callback = new Callback<Crypto, Throwable>() {
            @Override
            public void onSuccess(Crypto response) {
                if(response != null) {
                    navigator().showCoins(response);
                    navigator().afterRequest();
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
}