package com.example.javabotspring.logic.OzonAnalyze;

import com.example.javabotspring.logic.entities.Order;
import com.example.javabotspring.logic.OzonHandler.OzonApi;
import com.example.javabotspring.logic.entities.Stock;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OzonApiToEntity {
    public static List<Order> getOrders_FBO(String clientID, String key) {
        JSONObject jsonObject = OzonApi.getOrders_FBO(clientID, key);
        JSONArray result = null;
        try {
            result = jsonObject.getJSONArray("result");
        } catch (JSONException e) {
            new RuntimeException(e);
        }
        List<Order> orderList = new ArrayList<>();
        for (int i = 0; i < result.length(); i++) {
            String article = "";
            int count = 0;
            String cluster = null;
            JSONArray products = null;
            try {
                products = result.getJSONObject(i).getJSONArray("products");
            } catch (JSONException e) {
                new RuntimeException(e);
            }
            for (int j = 0; j < products.length(); j++) {
                try {
                    article = products.getJSONObject(j).getString("offer_id");
                    count = products.getJSONObject(j).getInt("quantity");
                } catch (JSONException e) {
                    new RuntimeException(e);
                }
            }
            try {
                cluster = result.getJSONObject(i).getJSONObject("financial_data").getString("cluster_to");
            } catch (JSONException e) {
                new RuntimeException(e);
            }
            Order order = new Order(article, count, "FBO", cluster);
            orderList.add(order);
        }

        return orderList;
    }

    public static List<Stock> getStocks_FBO(String clientID, String key) {
        JSONArray array = null;
        try {
            array = OzonApi.getStock_v2(clientID, key);
        } catch (IOException e) {
            new RuntimeException(e);
        }
        List<Stock> stocks = new ArrayList<>();
        for (int i = 0; i < array.length(); i++) {
            Stock stock = new Stock();
            String warehouse_name = null;
            String article = null;
            int count = 0;
            try {
                warehouse_name = array.getJSONObject(i).getString("warehouse_name");
                article = array.getJSONObject(i).getString("item_code");
                count = array.getJSONObject(i).getInt("free_to_sell_amount");
            } catch (JSONException e) {
                new RuntimeException(e);
            }
            stock.setSku(article);
            stock.setCount(count);
            stock.setWarehouseName(warehouse_name);

            stocks.add(stock);

        }
        return stocks;
    }


}
