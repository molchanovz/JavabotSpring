package com.example.javabotspring.logic.entities;

public class Sale extends Order{
    String saleID;
    public Sale(String article, int count, String saleID) {
        super(article, count);
        this.saleID = saleID;
    }

    public String getSaleID() {
        return saleID;
    }

    public void setSaleID(String saleID) {
        this.saleID = saleID;
    }
}
