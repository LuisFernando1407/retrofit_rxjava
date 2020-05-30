package com.br.retrofit_rxjava.data.model;

import java.util.List;

public class Ticker {
    public String base;
    public String target;
    public String price;
    public String volume;
    public String change;
    public List<Market> markets = null;
}