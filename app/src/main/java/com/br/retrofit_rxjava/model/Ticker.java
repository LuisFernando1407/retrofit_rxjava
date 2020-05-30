package com.br.retrofit_rxjava.model;

import java.util.List;

public class Ticker {
    public String base;
    public String target;
    public String price;
    public String volume;
    public String change;
    public List<Market> markets = null;
}