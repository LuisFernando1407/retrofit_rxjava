package com.br.retrofit_rxjava.api.service;

import com.br.retrofit_rxjava.api.auth.Authenticated;

public class ServiceGenerator extends Authenticated {

    public ServiceGenerator(){
        this.setupRetrofit();
    }

    public static <S> S createService(Class<S> service){
        return retrofit.create(service);
    }
}