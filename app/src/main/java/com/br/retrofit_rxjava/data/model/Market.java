package com.br.retrofit_rxjava.data.model;

import android.annotation.SuppressLint;
import android.widget.Toast;

import com.br.retrofit_rxjava.RetrofitRxJavaApplication;

public class Market {
    public String market;
    public String price;
    public Double volume;

    public void toastVolume(){
        Toast.makeText(RetrofitRxJavaApplication.getInstance(), "Volume of " + this.volume, Toast.LENGTH_SHORT).show();
    }

    public void toastPrice(){
        Toast.makeText(RetrofitRxJavaApplication.getInstance(), "Price of " + this.priceFormatted(), Toast.LENGTH_SHORT).show();
    }

    @SuppressLint("DefaultLocale")
    public String priceFormatted(){
        return "$ " + String.format("%.2f", Double.parseDouble(this.price));
    }
}