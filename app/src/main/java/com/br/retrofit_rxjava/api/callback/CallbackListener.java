package com.br.retrofit_rxjava.api.callback;

public interface CallbackListener<R, T> {
    void onSuccess(R response);
    void onError(T throwable);
}