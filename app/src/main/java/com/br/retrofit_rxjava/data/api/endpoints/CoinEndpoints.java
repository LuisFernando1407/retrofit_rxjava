package com.br.retrofit_rxjava.data.api.endpoints;

import com.br.retrofit_rxjava.data.model.Crypto;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface CoinEndpoints {
    @GET("{coin}-usd")
    Single<Crypto> getCoinData(@Path("coin") String coin);
}