package com.example.javabotspring.logic.googleSheets;

import java.util.HashMap;
import java.util.List;

public class Product {
    String article;
    HashMap<String, Integer> productCluster;
    int orders;

    public Product() {
    }

    public Product(String article) {
        this.article = article;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public HashMap<String, Integer> getProductCluster() {
        return productCluster;
    }

    public void setProductCluster(HashMap<String, Integer> productCluster) {
        this.productCluster = productCluster;
    }

    public int getOrders() {
        return orders;
    }

    public void setOrders(int orders) {
        this.orders = orders;
    }

    public void getAllClusters(List<ProductCluster> productClustersSorted) {
        HashMap<String, Integer> productCluster = new HashMap<>();
        for (ProductCluster cluster : productClustersSorted) {
            if (!productCluster.containsKey(cluster.getCluster())) {
                productCluster.put(cluster.getCluster(), 0);
            }
        }
        this.productCluster = productCluster;
    }
}
