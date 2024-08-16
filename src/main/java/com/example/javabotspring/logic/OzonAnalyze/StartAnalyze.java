package com.example.javabotspring.logic.OzonAnalyze;

import com.example.javabotspring.Protection;
import com.example.javabotspring.logic.entities.cluster.Cluster;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class StartAnalyze {
    public static void main(String[] args) throws JSONException, JsonProcessingException {
        String clientID = Protection.clientId;
        String key = Protection.ozonKey;
        List<Cluster> clusters = getClustersFromJson();
        clusters = Orders.setOrders(clientID, key, clusters);
        clusters = Stocks.setStocks(clientID, key, clusters);
        for (Cluster cluster : clusters) {
            System.out.println(cluster);
        }
    }


    private static List<Cluster> getClustersFromJson() throws JsonProcessingException, JSONException {
        String filePath = "src/main/java/com/example/javabotspring/logic/entities/cluster/clusters.json";  // Укажите правильный путь к вашему JSON файлу
        List<Cluster> clusters = new ArrayList<>();
        String content = null;
        try {
            content = new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            new RuntimeException(e);
        }

        JSONArray rows = null;
        try {
            JSONObject jsonObject = new JSONObject(content);
            rows = jsonObject.getJSONObject("result").getJSONArray("rows");
        } catch (JSONException e) {
            new RuntimeException(e);
        }

        for (int i = 0; i < rows.length(); i++) {
            ObjectMapper objectMapper = new ObjectMapper();
            Cluster cluster = objectMapper.readValue(rows.getJSONObject(i).toString(), Cluster.class);
            clusters.add(cluster);
        }
        return clusters;
    }

}
