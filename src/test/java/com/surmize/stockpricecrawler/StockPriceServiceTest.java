package com.surmize.stockpricecrawler;

import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class StockPriceServiceTest {
    
    public StockPriceServiceTest() {
    }

    @Test
    public void testGetStockPriceData() {
        List<String> symbols = new ArrayList<>();
        symbols.add("AAPL");
        symbols.add("GOOG");
        symbols.add("FB");
        symbols.add("AMZN");
        
        StockPriceService service = new StockPriceService();
        List<StockPrice> prices = service.getStockPriceData(symbols);
        Assert.assertTrue(prices.size() == 4);
        for (StockPrice stockPrice : prices) {
            System.out.println(stockPrice.stockSymbol+" "+stockPrice.price);
        }
    }
    
}
