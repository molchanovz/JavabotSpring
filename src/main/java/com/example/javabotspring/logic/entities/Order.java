package com.example.javabotspring.logic.entities;

import java.util.HashMap;

public class Order {
    String Article;
    double price;
    int count = 1;
    String orderType;
    String clusterName;
    HashMap<String, Integer> productCluster;

    public Order(String article, double price) {
        Article = article;
        this.price = price;
    }

    public Order(String article, double price, int count) {
        Article = article;
        this.price = price;
        this.count = count;
    }

    public Order(String article, int count) {
        Article = article;
        this.count = count;
    }

    public Order(String article, double price, int count, String orderType) {
        Article = article;
        this.price = price;
        this.count = count;
        this.orderType = orderType;
    }

    public Order(String article, int count, String orderType, String clusterName) {
        Article = article;
        this.price = price;
        this.count = count;
        this.orderType = orderType;
        this.clusterName = clusterName;
    }

    public String getArticle() {
        return Article;
    }

    public void setArticle(String article) {
        Article = article;
    }

    public double getPrice() {
        return price;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    @Override
    public String toString() {
        return "Order{" +
                "Article='" + Article + '\'' +
                ", price=" + price +
                ", count=" + count +
                ", orderType='" + orderType + '\'' +
                ", cluster='" + clusterName + '\'' +
                '}';
    }
}
