package com.example.javabotspring.Logic.Entities;

public class Product {
    String article;

    String nmId;
    int stock;
    int stockInWay;
    int countOrders;
    int dayForSale;

    int newPrice;

    String warehouseName;

    int price;

    public Product(String article, int stock, int countOrders) {
        this.article = article;
        this.stock = stock;
        this.countOrders = countOrders;
    }

    public Product(String article, int stock, int stockInWay, int countOrders, String warehouseName) {
        this.article = article;
        this.stock = stock;
        this.stockInWay = stockInWay;
        this.countOrders = countOrders;
        this.warehouseName=warehouseName;
    }

    public Product(String article, String nmId) {
        this.article = article;
        this.nmId = nmId;
    }

    public Product(String nmId, int price) {
        this.nmId = nmId;
        this.price = price;
    }


    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    public int getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(int newPrice) {
        this.newPrice = newPrice;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getNmId() {
        return nmId;
    }

    public void setNmId(String nmId) {
        this.nmId = nmId;
    }

    public int getStockInWay() {
        return stockInWay;
    }

    public void setStockInWay(int stockInWay) {
        this.stockInWay = stockInWay;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getCountOrders() {
        return countOrders;
    }

    public void setCountOrders(int countOrders) {
        this.countOrders = countOrders;
    }

    public int getDayForSale() {
        return dayForSale;
    }

    public void setDayForSale(int dayForSale) {
        this.dayForSale = dayForSale;
    }

    @Override
    public String toString() {
        return "Product{" +
                "article='" + article + '\'' +
                ", nmId='" + nmId + '\'' +
                ", stock=" + stock +
                ", stockInWay=" + stockInWay +
                ", countOrders=" + countOrders +
                ", dayForSale=" + dayForSale +
                ", newPrice=" + newPrice +
                ", price=" + price +
                '}';
    }
}
