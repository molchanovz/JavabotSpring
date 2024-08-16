package com.example.javabotspring.logic.googleSheets.Tests.OzonTests;

import com.example.javabotspring.Protection;
import com.example.javabotspring.logic.entities.Stock;
import com.example.javabotspring.logic.googleSheets.GoogleService;
import com.example.javabotspring.logic.googleSheets.OzonAnalyze;
import com.example.javabotspring.logic.googleSheets.Product;
import org.json.JSONException;


import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;



public class TestAnalyze {
    static final String clientId = Protection.clientId;
    static final String key = Protection.ozonKey;

    public static void main(String[] args) throws JSONException, IOException, InterruptedException, GeneralSecurityException {
        List<Stock> stocks;
        //stocks = OzonApiToEntity.getStock_v2(clientId,key);
        //stocks.stream().forEach(System.out::println);

        List<Product> products = new ArrayList<>();
        products = OzonAnalyze.getOrders(products);
        List<List<Object>> stroki = new ArrayList<>();
        for (Product product : products) {
            List<Object> stroka = new ArrayList<>();
            stroka.add(String.valueOf(product.getArticle()));
            product.getProductCluster().forEach((s, integer) -> {stroka.add(s);stroka.add(integer);});
            stroki.add(stroka);
        }
        //https://docs.google.com/spreadsheets/d/1eCTS34i-FZxS_MRrFMs8rphVgpWXt3WNe5XGnqras80/edit#gid=51165295
        String spreadsheetId = "1eCTS34i-FZxS_MRrFMs8rphVgpWXt3WNe5XGnqras80";
        String listName = "Отчет";
        String range = listName + "!A:AA";

        GoogleService.write(spreadsheetId,range,stroki);
    }
}
