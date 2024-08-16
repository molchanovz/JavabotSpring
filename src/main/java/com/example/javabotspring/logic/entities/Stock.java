package com.example.javabotspring.logic.entities;

import java.util.HashMap;

public class Stock {

    String brand;
    String subject;
    String supplierArticle;
    int nmId;
    String lastChangeDate;
    String barcode;
    String techSize;
    int inWayToClient;
    int inWayFromClient;
    int quantityFull;
    int count;
    String warehouseName;
    HashMap<String, Integer> warehouseNamesMap;
    String sku;
    String name;
    int reservedCount;

    public Stock() {
    }

    public Stock(String brand, String subject, String supplierArticle, int nmId, String lastChangeDate, String barcode, String techSize, int inWayToClient, int inWayFromClient, int quantityFull, String warehouseName, int count) {
        this.brand = brand;
        this.subject = subject;
        this.supplierArticle = supplierArticle;
        this.nmId = nmId;
        this.lastChangeDate = lastChangeDate;
        this.barcode = barcode;
        this.techSize = techSize;
        this.inWayToClient = inWayToClient;
        this.quantityFull = quantityFull;
        this.inWayFromClient = inWayFromClient;
        this.count = count;
        this.warehouseName = warehouseName;
    }

    public Stock(String sku, String warehouseName, String supplierArticle, String name, int inWayFromClient, int count, int reservedCount) {
        this.supplierArticle = supplierArticle;
        this.inWayFromClient = inWayFromClient;
        this.count = count;
        this.warehouseName = warehouseName;
        this.sku = sku;
        this.name = name;
        this.reservedCount = reservedCount;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getReservedCount() {
        return reservedCount;
    }

    public void setReservedCount(int reservedCount) {
        this.reservedCount = reservedCount;
    }

    public HashMap<String, Integer> getWarehouseNamesMap() {
        return warehouseNamesMap;
    }

    public void setWarehouseNamesMap(HashMap<String, Integer> warehouseNamesMap) {
        this.warehouseNamesMap = warehouseNamesMap;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getSupplierArticle() {
        return supplierArticle;
    }

    public void setSupplierArticle(String supplierArticle) {
        this.supplierArticle = supplierArticle;
    }

    public int getNmId() {
        return nmId;
    }

    public void setNmId(int nmId) {
        this.nmId = nmId;
    }

    public String getLastChangeDate() {
        return lastChangeDate;
    }

    public void setLastChangeDate(String lastChangeDate) {
        this.lastChangeDate = lastChangeDate;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getTechSize() {
        return techSize;
    }

    public void setTechSize(String techSize) {
        this.techSize = techSize;
    }

    public int getInWayToClient() {
        return inWayToClient;
    }

    public void setInWayToClient(int inWayToClient) {
        this.inWayToClient = inWayToClient;
    }

    public int getInWayFromClient() {
        return inWayFromClient;
    }

    public void setInWayFromClient(int inWayFromClient) {
        this.inWayFromClient = inWayFromClient;
    }

    public int getQuantityFull() {
        return quantityFull;
    }

    public void setQuantityFull(int quantityFull) {
        this.quantityFull = quantityFull;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getWarehouseName() {
        return warehouseName;
    }

    public void setWarehouseName(String warehouseName) {
        this.warehouseName = warehouseName;
    }

    @Override
    public String toString() {
        return "Stock{" +
                "brand='" + brand + '\'' +
                ", subject='" + subject + '\'' +
                ", supplierArticle='" + supplierArticle + '\'' +
                ", nmId=" + nmId +
                ", lastChangeDate='" + lastChangeDate + '\'' +
                ", barcode='" + barcode + '\'' +
                ", techSize='" + techSize + '\'' +
                ", inWayToClient='" + inWayToClient + '\'' +
                ", inWayFromClient='" + inWayFromClient + '\'' +
                ", count=" + count +
                ", warehouseName='" + warehouseName + '\'' +
                '}';
    }
}
