package com.br.retrofit_rxjava.ui.activity.main;

import com.br.retrofit_rxjava.api.callback.Callback;
import com.br.retrofit_rxjava.api.endpoints.CoinEndpoints;
import com.br.retrofit_rxjava.api.service.ServiceGenerator;
import com.br.retrofit_rxjava.model.Crypto;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainRepository {
    public Disposable getCoins(String type, Callback<Crypto, Throwable> callback) {
        return ServiceGenerator.createService(CoinEndpoints.class).getCoinData(type)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(callback::onError)
                .subscribe(callback::onSuccess, callback::onError);
    }
}