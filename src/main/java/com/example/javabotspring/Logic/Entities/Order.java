package com.example.javabotspring.Logic.Entities;

public class Order {
    String Article;
    double price;
    int count  = 1;

    String orderType;

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

    @Override
    public String toString() {
        return "Order{" +
                "Article='" + Article + '\'' +
                ", price=" + price +
                ", count=" + count +
                ", orderType='" + orderType + '\'' +
                '}';
    }
}
