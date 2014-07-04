package com.surmize.stockpricecrawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

public class StockPriceService {

    static final Logger logger = Logger.getLogger(StockPriceService.class.getName());
    private final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("M/d/yyyy h:mma z", Locale.ENGLISH);
    
    public List<StockPrice> getStockPriceData(List<String> symbols){
        BufferedReader in = null;
        List<StockPrice> prices = new ArrayList<>();
        try {
            URL priceUrl = new URL(buildUrlString(symbols));
            in = new BufferedReader( new InputStreamReader(priceUrl.openStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                String[] stockArray = inputLine.split(",");
                StockPrice sp = new StockPrice();
                sp.stockSymbol =  removeQuotes(stockArray[0]);
                sp.tradeTime = getDateFromDatePlusTime(stockArray[1], stockArray[2]);
                sp.price = getFloat(stockArray[3]);
                sp.peRatio = getFloat(stockArray[4]);
                sp.pegRatio = getFloat(stockArray[5]);
                sp.dayLow = getFloat(stockArray[6]);
                sp.dayHigh = getFloat(stockArray[7]);
                prices.add(sp);
            }
            in.close();
        } catch (IOException ex) {
            logger.log(Level.SEVERE, null, ex);
        } finally {
            try {
                in.close();
            } catch (IOException ex) {
                logger.log(Level.SEVERE, null, ex);
            }
        }
        return prices;
    }
    
    private String buildUrlString(List<String> symbols){
        //exmaple: http://finance.yahoo.com/d/quotes.csv?s=XOM+BBDb.TO+JNJ+MSFT&f=snd1l1yr
        StringBuilder sb  = new StringBuilder("http://finance.yahoo.com/d/quotes.csv?s=");
        for (String symbol : symbols) {
            sb.append(symbol).append("+");
        }
        sb.deleteCharAt(sb.length()-1);
        sb.append("&f=sd1t1l1rr5gh");  // see this website for reference  http://www.gummy-stuff.org/Yahoo-data.htm
        return sb.toString();
    }
    
    private String removeQuotes(String removeMyQuotes){
        return removeMyQuotes.replace("\"", "");
    }
    
     private Date getDateFromDatePlusTime(String date, String time){
        String dateString = removeQuotes(date)+" "+removeQuotes(time)+" EST";
        String replaceAll = dateString.replaceAll("\"", "");
        try {
            return simpleDateFormat.parse(replaceAll);
        } catch (ParseException ex) {
            logger.log(Level.SEVERE, "Error Parsing Date "+dateString, ex);
            return new Date();
        }
        
    }
     
    private Float getFloat(String floatMe){
        if(!floatMe.contains("N")){
            return Float.parseFloat(floatMe);
        }
        return 0f;
    }
}
