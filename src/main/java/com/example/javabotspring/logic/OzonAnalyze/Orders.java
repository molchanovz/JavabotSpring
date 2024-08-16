package com.example.javabotspring.logic.OzonAnalyze;

import com.example.javabotspring.logic.entities.Order;
import com.example.javabotspring.logic.entities.cluster.Cluster;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class Orders {
    public static List<Cluster> setOrders(String clientID, String key, List<Cluster> clusters) {

        List<Order> orderList = OzonApiToEntity.getOrders_FBO(clientID, key);
        List<Order> ordersSorted = orderList.stream().sorted(new Comparator<Order>() {
            @Override
            public int compare(Order o1, Order o2) {
                return o1.getClusterName().compareTo(o2.getClusterName());
            }
        }).toList();
        for (Cluster cluster : clusters) {
            for (Order order : ordersSorted) {

                if (order.getClusterName().equals(cluster.getName())) {
                    if (cluster.getProductOrders() == null) {
                        cluster.setProductOrders(new HashMap<>());
                    }
                    HashMap<String, Integer> map = cluster.getProductOrders();
                    int count;
                    if (map.containsKey(order.getArticle())) {
                        count = map.get(order.getArticle()) + order.getCount();
                    } else {
                        count = order.getCount();
                    }
                    map.put(order.getArticle(), count);
                    cluster.setProductOrders(map);
                }
            }
        }
        return clusters;
    }
}
