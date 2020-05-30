package com.br.retrofit_rxjava.data.api.service;

import com.br.retrofit_rxjava.data.api.auth.Authenticated;

public class ServiceGenerator extends Authenticated {

    private ServiceGenerator(){
        this.setupRetrofit();
    }

    /* Method factory */
    public static ServiceGenerator of(){
        return new ServiceGenerator();
    }

    public <S> S createService(Class<S> service){
        return retrofit.create(service);
    }
}