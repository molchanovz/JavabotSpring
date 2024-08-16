package com.example.javabotspring.logic.googleSheets;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.*;

public class OzonAnalyze {
    public static List<Product> getOrders(List<Product> products) throws GeneralSecurityException, IOException {
        String listName = "Заказы";
        String range = listName + "!L2:AA";
        List<List<Object>> lists = GoogleService.read("1eCTS34i-FZxS_MRrFMs8rphVgpWXt3WNe5XGnqras80", range);
        List<ProductCluster> productClusters = new ArrayList<>();
        for (List<Object> list : lists) {
            productClusters.add(new ProductCluster(String.valueOf(list.get(0)), String.valueOf(list.get(15))));
        }
        List<ProductCluster> productClustersSorted = productClusters.stream().sorted(new Comparator<ProductCluster>() {
            @Override
            public int compare(ProductCluster o1, ProductCluster o2) {
                return o1.toString().compareTo(o2.toString());
            }
        }).toList();
        int orders = 0;
        Product product = new Product();
        product.setArticle(productClustersSorted.get(0).getArticle());
        product.getAllClusters(productClustersSorted);
        product.getProductCluster().put(productClustersSorted.get(0).getCluster(), 1);
        orders += 1;

        for (int i = 1; i < productClustersSorted.size(); i++) {
                if (product.getArticle().equals(productClustersSorted.get(i).getArticle())) {
                product.getProductCluster().put(productClustersSorted.get(i).getCluster(), product.getProductCluster().get(productClustersSorted.get(i).getCluster()) + 1);
                orders+=1;
            } else {
                product.setOrders(orders);
                products.add(product);
                orders=0;
                product = new Product(productClustersSorted.get(i).getArticle());
                product.getAllClusters(productClustersSorted);
                product.getProductCluster().put(productClustersSorted.get(i).getCluster(), product.getProductCluster().get(productClustersSorted.get(i).getCluster()) + 1);
            }
        }
        product.setOrders(orders);
        products.add(product);
        return products;
    }
}

class ProductCluster {
    String article;
    String cluster;

    public ProductCluster(String article, String cluster) {
        this.article = article;
        this.cluster = cluster;
    }

    public String getArticle() {
        return article;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public String getCluster() {
        return cluster;
    }

    public void setCluster(String cluster) {
        this.cluster = cluster;
    }

    @Override
    public String toString() {
        return "ProductCluster{" +
                "article='" + article + '\'' +
                ", cluster='" + cluster + '\'' +
                '}';
    }
}

