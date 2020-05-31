package com.br.retrofit_rxjava.ui.activity.main.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.widget.Toast;

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

    public void setDataInView(){
        this.holder.tvMarket.setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent));
        this.holder.tvMarket.setOnClickListener(e -> Toast.makeText(context, "Market name", Toast.LENGTH_SHORT).show());
        this.holder.tvMarket.setText(market.market.toUpperCase());

        this.holder.llVolume.setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent));
        this.holder.tvVolume.setOnClickListener(e -> Toast.makeText(context, "Volume of " + market.volume, Toast.LENGTH_SHORT).show());
        this.holder.tvVolume.setText(String.valueOf(market.volume));

        this.holder.llPrice.setBackgroundColor(ContextCompat.getColor(context, android.R.color.transparent));

        @SuppressLint("DefaultLocale") String priceFormat = "$" + String.format("%.2f", Double.parseDouble(market.price));
        this.holder.tvPrice.setText(priceFormat);
        this.holder.tvPrice.setOnClickListener(e -> Toast.makeText(context, "Price of " + priceFormat, Toast.LENGTH_SHORT).show());
    }
}
