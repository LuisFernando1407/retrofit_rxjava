package com.br.retrofit_rxjava.ui.activity.main.adapter;

import android.content.Context;

import androidx.core.content.ContextCompat;

import com.br.retrofit_rxjava.data.model.Market;

public class MainAdapterUtil {
    private MainAdapter.ViewHolder holder;
    private Context context;
    private Market market;

    private MainAdapterUtil(MainAdapter.ViewHolder holder, Context context, Market market){
        this.holder = holder;
        this.context = context;
        this.market = market;
    }

    public static MainAdapterUtil of(MainAdapter.ViewHolder holder, Context context, Market market){
        return new MainAdapterUtil(holder, context, market);
    }

    public void changeColorAndSetData(){
        /* Change color */
        this.holder.itemMainBinding.tvMarket.setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent));
        this.holder.itemMainBinding.llVolume.setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent));
        this.holder.itemMainBinding.llPrice.setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent));

        /* Set market in view */
        this.holder.itemMainBinding.setMarket(this.market);
    }
}
