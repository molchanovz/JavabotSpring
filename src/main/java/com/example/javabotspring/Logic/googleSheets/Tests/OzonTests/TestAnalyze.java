package com.example.javabotspring.Logic.googleSheets.Tests.OzonTests;

import com.example.javabotspring.Bot.components.Service;
import com.example.javabotspring.Logic.Clasters.Ozon.Claster;
import com.example.javabotspring.Logic.Entities.Stock;
import com.example.javabotspring.Logic.OzonHandler.OzonApiToEntity;
import com.example.javabotspring.Logic.googleSheets.GoogleService;
import com.example.javabotspring.Logic.googleSheets.OzonAnalyze;
import com.example.javabotspring.Logic.googleSheets.Product;
import org.apache.http.util.EntityUtils;
import org.apache.tools.ant.taskdefs.optional.Cab;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;



public class TestAnalyze {
    static final String clientId = "259267";
    static final String key = "451952b2-a29b-4f7e-819e-3fd96f580fbc";

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
