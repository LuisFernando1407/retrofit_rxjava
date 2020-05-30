package com.br.retrofit_rxjava.data.api.callback;

public interface CallbackListener<R, T> {
    void onSuccess(R response);
    void onError(T throwable);
}