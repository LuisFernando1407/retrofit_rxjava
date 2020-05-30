package com.br.retrofit_rxjava.data.api.service;

import com.br.retrofit_rxjava.data.api.auth.Authenticated;

public class ServiceGenerator extends Authenticated {

    public ServiceGenerator(){
        this.setupRetrofit();
    }

    public <S> S createService(Class<S> service){
        return retrofit.create(service);
    }
}