package com.example.javabotspring.logic.OzonAnalyze;

import com.example.javabotspring.logic.OzonHandler.OzonApi;
import com.example.javabotspring.logic.entities.Stock;
import com.example.javabotspring.logic.entities.cluster.Cluster;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.util.EntityUtils;
import org.apache.tomcat.util.json.JSONParser;
import org.apache.tomcat.util.json.ParseException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Stocks {
    public static List<Cluster> setStocks(String clientID, String key, List<Cluster> clusters) {

        List<Stock> stocks = null;
        stocks = OzonApiToEntity.getStocks_FBO(clientID, key);
        for (Cluster cluster : clusters) {
            for (Stock stock : stocks) {
                if (cluster.getWarehouses().contains(stock.getWarehouseName())) {
                    if (cluster.getProductCounts() == null) {
                        cluster.setProductCounts(new HashMap<>());
                    }
                    HashMap<String, Integer> map = cluster.getProductCounts();
                    if (map.containsKey(stock.getSku())) {
                        map.put(stock.getSku(), map.get(stock.getSku()) + stock.getCount());
                    } else {
                        map.put(stock.getSku(), stock.getCount());
                    }
                    cluster.setProductCounts(map);
                }
            }
        }
        return clusters;
    }
}
