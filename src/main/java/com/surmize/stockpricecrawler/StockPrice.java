package com.surmize.stockpricecrawler;

import java.util.Date;

public class StockPrice {

    public String stockSymbol; // yahoo api code s
    public Date tradeTime; // yahoo api code t1
    public Float price;  // yahoo api code l1
    public Float peRatio; // yahoo api code r 
    public Float pegRatio; // yahoo api code r5 
    public Float dayLow; // yahoo api code g
    public Float dayHigh; // yahoo api code h
}
