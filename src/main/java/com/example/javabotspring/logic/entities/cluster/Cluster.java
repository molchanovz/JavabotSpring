package com.example.javabotspring.logic.entities.cluster;

import java.util.HashMap;
import java.util.List;

public class Cluster {
    private String name;
    private HashMap<String, Integer> productOrders;
    private HashMap<String, Integer> productCounts;
    private List<String> warehouses;

    // Конструктор по умолчанию
    public Cluster() {
    }

    public Cluster(String name, List<String> warehouses) {
        this.name = name;
        this.warehouses = warehouses;
    }

    public Cluster(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public HashMap<String, Integer> getProductOrders() {
        return productOrders;
    }

    public void setProductOrders(HashMap<String, Integer> productOrders) {
        this.productOrders = productOrders;
    }

    public HashMap<String, Integer> getProductCounts() {
        return productCounts;
    }

    public void setProductCounts(HashMap<String, Integer> productCounts) {
        this.productCounts = productCounts;
    }

    public List<String> getWarehouses() {
        return warehouses;
    }

    public void setWarehouses(List<String> warehouses) {
        this.warehouses = warehouses;
    }

    @Override
    public String toString() {
        return "Cluster{" +
                "name='" + name + '\'' +
                ", productOrders=" + productOrders +
                ", productCounts=" + productCounts +
                ", warehouses=" + warehouses +
                '}';
    }
}
