package com.example.javabotspring.Logic.Entities;

public class Return {
    String sku;
    String article;
    String posting_number;
    String returned_to_ozon_moment;
    String status_name;

    public Return(String sku, String posting_number, String returned_to_ozon_moment, String status_name) {
        this.sku = sku;
        this.posting_number = posting_number;
        this.returned_to_ozon_moment = returned_to_ozon_moment;
        this.status_name = status_name;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getPosting_number() {
        return posting_number;
    }

    public void setPosting_number(String posting_number) {
        this.posting_number = posting_number;
    }

    public String getReturned_to_ozon_moment() {
        return returned_to_ozon_moment;
    }

    public void setReturned_to_ozon_moment(String returned_to_ozon_moment) {
        this.returned_to_ozon_moment = returned_to_ozon_moment;
    }

    public String getStatus_name() {
        return status_name;
    }

    public void setStatus_name(String status_name) {
        this.status_name = status_name;
    }

    @Override
    public String toString() {
        return "Return{" +
                "sku='" + sku + '\'' +
                ", posting_number='" + posting_number + '\'' +
                ", returned_to_ozon_moment='" + returned_to_ozon_moment + '\'' +
                ", status_name='" + status_name + '\'' +
                '}';
    }
}
