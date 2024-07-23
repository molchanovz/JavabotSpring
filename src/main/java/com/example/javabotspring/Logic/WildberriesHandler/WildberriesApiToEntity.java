package com.example.javabotspring.Logic.WildberriesHandler;

import com.example.javabotspring.Bot.Protection;
import com.example.javabotspring.Bot.components.Service;
import com.example.javabotspring.Logic.Entities.Order;
import com.example.javabotspring.Logic.Entities.Product;
import com.example.javabotspring.Logic.Entities.Sale;
import com.example.javabotspring.Logic.Entities.Stock;
import com.example.javabotspring.Logic.googleSheets.GoogleService;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.*;

public class WildberriesApiToEntity {

    static List<Order> getOrders_All(String key,String date) throws IOException {
        JSONArray jsonArray = WildberriesApi.getOrders_All(key,date);
        //List<Order> orderFBSList = WildberriesApiToEntity.getOrders_FBS();
        List<Order> orderList = new ArrayList<>();

        String path = Protection.fileFBO;
        FileReader fr = new FileReader(path);
        Scanner scan = new Scanner(fr);
        List<String> arrFBO = new ArrayList<>();
        while (scan.hasNextLine()) {
            arrFBO.add(scan.nextLine());
        }
        fr.close();

        String pathFBS = Protection.fileFBS;
        FileReader frFBS = new FileReader(pathFBS);
        Scanner scanFBS = new Scanner(frFBS);
        List<String> arrFBS = new ArrayList<>();
        while (scanFBS.hasNextLine()) {
            arrFBS.add(scanFBS.nextLine());
        }
        frFBS.close();

        for (int i = 0; i < jsonArray.length(); i++) {
            String srid;
            try {
                srid = jsonArray.getJSONObject(i).getString("srid");
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            //ищем только новые заказы
            if (!arrFBO.contains(srid) && (!arrFBS.contains(srid))) {
                arrFBO.add(srid);
                String article;
                double price;
                try {
                    article = jsonArray.getJSONObject(i).getString("supplierArticle");
                    double totalPrice = jsonArray.getJSONObject(i).getDouble("totalPrice");
                    int discountPercent = jsonArray.getJSONObject(i).getInt("discountPercent");
                    price = totalPrice * (1 - discountPercent / 100.0);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                Order order = new Order(article, price);
                orderList.add(order);
            }
        }
        FileWriter nFile = new FileWriter(path);
        String allOrders = "";

        for (String str : arrFBO) {
            allOrders += str + "\n";
        }
        nFile.write(allOrders);

        nFile.close();
        return orderList;
    }

    static List<Order> getOrders_FBS(String key) throws IOException {
        String date1 = Service.getDate(-24);
        String date2 = Service.getDate(0);
        JSONArray jsonArray = WildberriesApi.getOrders_FBS(date1,date2,key);
        List<Order> orderList = new ArrayList<>();
        String path = Protection.fileFBS;
        FileReader fr = new FileReader(path);
        Scanner scan = new Scanner(fr);
        List<String> arr = new ArrayList<>();
        while (scan.hasNextLine()) {
            arr.add(scan.nextLine());
        }
        fr.close();

        for (int i = 0; i < jsonArray.length(); i++) {
            String srid;
            try {
                srid = jsonArray.getJSONObject(i).getString("rid");
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
            //ищем только новые заказы
            if (!arr.contains(srid)) {
                arr.add(srid);
                String article;
                double price;
                try {
                    article = jsonArray.getJSONObject(i).getString("article");
                    double totalPrice = jsonArray.getJSONObject(i).getDouble("convertedPrice");
                    price = totalPrice / 100.0;
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                Order order = new Order(article, price);
                orderList.add(order);
            }
        }
        FileWriter nFile = new FileWriter(path);
        String allOrders = "";

        for (String str : arr) {
            allOrders += str + "\n";
        }
        nFile.write(allOrders);

        nFile.close();
        return orderList;
    }

    public static List<Stock> getStocks(String key) throws IOException {
        JSONArray jsonArray = WildberriesApi.getStocks(key);
        List<Stock> list = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            try {
                String brand = jsonArray.getJSONObject(i).getString("brand");
                String subject = jsonArray.getJSONObject(i).getString("subject");
                String supplierArticle = jsonArray.getJSONObject(i).getString("supplierArticle");
                int nmId = Integer.parseInt(jsonArray.getJSONObject(i).getString("nmId"));
                String lastChangeDate = jsonArray.getJSONObject(i).getString("lastChangeDate");
                String barcode = jsonArray.getJSONObject(i).getString("barcode");
                String techSize = jsonArray.getJSONObject(i).getString("techSize");
                int inWayToClient = Integer.parseInt(jsonArray.getJSONObject(i).getString("inWayToClient"));
                int inWayFromClient = Integer.parseInt(jsonArray.getJSONObject(i).getString("inWayFromClient"));
                int quantity = Integer.parseInt(jsonArray.getJSONObject(i).getString("quantity"));
                String warehouseName = jsonArray.getJSONObject(i).getString("warehouseName");
                int quantityFull = Integer.parseInt(jsonArray.getJSONObject(i).getString("quantityFull"));
                quantityFull = quantityFull-inWayToClient-inWayFromClient;

                Stock stock = new Stock(brand,subject,supplierArticle,nmId,lastChangeDate,barcode,techSize,inWayToClient,inWayFromClient,quantityFull,warehouseName,quantity);
                list.add(stock);
            } catch (JSONException e) {
                throw new RuntimeException(e);
            }
        }
        List<Stock> sortedList = list.stream().sorted(new StockComparator()).toList();
        return sortedList;
    }

    public static List<Order> getLastDayOrders(String key, String type, int deltaHours1, int deltaHours2) throws IOException, InterruptedException {
        JSONArray jsonArray = new JSONArray();
        List<Order> orderList = new ArrayList<>();
        if (type.equals("fbs")){
            String date1 = Service.getDate(deltaHours1);
            String date2 = Service.getDate(deltaHours2);
            jsonArray = WildberriesApi.getOrders_FBS(date1,date2,key);
            for (int i = 0; i < jsonArray.length(); i++) {
                String article;
                double price;
                try {
                    article = jsonArray.getJSONObject(i).getString("article");
                    double totalPrice = jsonArray.getJSONObject(i).getDouble("price");
                    price = totalPrice;
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                Order order = new Order(article, price);
                orderList.add(order);
            }
        } else if (type.equals("all")) {
            String date = Service.getFullDate(deltaHours1);
            jsonArray = WildberriesApi.getOrders_All(key,date);
            while (jsonArray==null){
                jsonArray = WildberriesApi.getOrders_All(key,date);
            }
            for (int i = 0; i < jsonArray.length(); i++) {
                String article;
                double price;
                String orderType;
                try {
                    article = jsonArray.getJSONObject(i).getString("supplierArticle");
                    double totalPrice = jsonArray.getJSONObject(i).getDouble("totalPrice");
                    int discountPercent = jsonArray.getJSONObject(i).getInt("discountPercent");
                    orderType  = jsonArray.getJSONObject(i).getString("orderType");
                    //int discountPercent = 50;
                    price = totalPrice * (1 - discountPercent / 100.0);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
                if(!orderType.contains("Возврат")){
                    Order order = new Order(article, price,1,orderType);
                    orderList.add(order);
                }

            }
        }


        return orderList;
    }

    public static ArrayList<Order> getOrdersInSupply(String supplyNum) throws IOException, JSONException {
        JSONArray arr = WildberriesApi.getOrdersInSupply("WB-GI-54097140");
        int i = 0;
        ArrayList<Order> orders = new ArrayList<>();
        while (!arr.isNull(i)) {
            orders.add(new Order(arr.getJSONObject(i++).getString("article"), 1));
        }
        return orders;
    }

    public static List<Sale> getReturns(String key, int deltaHours) throws IOException, JSONException {
        JSONArray array = WildberriesApi.getSales(key,deltaHours);
        int i = 0;
        ArrayList<Sale> sales = new ArrayList<>();
        while (!array.isNull(i)){
            String saleID = array.getJSONObject(i).getString("saleID");
            if(saleID.contains("R")){
                String article = array.getJSONObject(i).getString("supplierArticle");
                Sale sale = new Sale(article,1,saleID);
                sales.add(sale);
                System.out.println(sale);
            }
            i+=1;
        }
        return sales;
    }

    /*public static ArrayList<Product> getTurnover() throws IOException, InterruptedException, JSONException {
        List<Stock> stocks = getStocks();
        List<Stock> stocksUniq = new ArrayList<>();

        HashMap<String, Integer> uniqStocks = new HashMap<>();
        for (Stock el : stocks) {
            if (uniqStocks.containsKey(el.getName())) {
                uniqStocks.put(el.getName(), uniqStocks.get(el.getName()) + el.getCount());
            } else uniqStocks.put(el.getName(), el.getCount());
        }
        List<Order> allOrders = new ArrayList<>();
        List<Order> orders1 = WildberriesApiToEntity.getLastDayOrders("all",-168,-144);
        allOrders.addAll(orders1);
        Thread.sleep(8000);
        List<Order> orders2 = WildberriesApiToEntity.getLastDayOrders("all",-144,-120);
        allOrders.addAll(orders2);
        Thread.sleep(8000);
        List<Order> orders3 = WildberriesApiToEntity.getLastDayOrders("all",-120,-96);
        allOrders.addAll(orders3);
        Thread.sleep(8000);
        List<Order> orders4 = WildberriesApiToEntity.getLastDayOrders("all",-96,-72);
        allOrders.addAll(orders4);
        Thread.sleep(8000);
        List<Order> orders5 = WildberriesApiToEntity.getLastDayOrders("all",-72,-48);
        allOrders.addAll(orders5);
        Thread.sleep(8000);
        List<Order> orders6 = WildberriesApiToEntity.getLastDayOrders("all",-48,-24);
        allOrders.addAll(orders6);
        Thread.sleep(8000);
        List<Order> orders7 = WildberriesApiToEntity.getLastDayOrders("all",-24,0);
        allOrders.addAll(orders7);



        HashMap<String, Integer> uniqAllOrders = new HashMap<>();
        for (Order el : allOrders) {
            if (uniqAllOrders.containsKey(el.getArticle())) {
                uniqAllOrders.put(el.getArticle(), uniqAllOrders.get(el.getArticle()) + 1);
            } else uniqAllOrders.put(el.getArticle(), 1);
        }



        ArrayList<Product> products = new ArrayList<>();
        for (Map.Entry entry : uniqStocks.entrySet()) {
            double ordersCount = 0;
            if(uniqAllOrders.containsKey(entry.getKey())){
                System.out.println(uniqAllOrders.get(entry.getKey()) / 7);
                ordersCount = Math.ceil((double)uniqAllOrders.get(entry.getKey()) / 7);
                Product product = new Product((String) entry.getKey(), (Integer) entry.getValue(),uniqAllOrders.get(entry.getKey()));
                product.setDayForSale((int) Math.round((int)entry.getValue() / ordersCount));
                products.add(product);
            }else {
                Product product = new Product((String) entry.getKey(), (Integer) entry.getValue(),0);
                product.setDayForSale(-1);
                products.add(product);
            }


        }
        return products;
    }*/
    public static List<Product> getProducts() throws IOException, JSONException {
        JSONArray arr = WildberriesApi.getProducts();
        int i = 0;
        List<Product> products = new ArrayList<>();
        while(!arr.isNull(i)){
            products.add(new Product(arr.getJSONObject(i).getString("vendorCode"),arr.getJSONObject(i).getString("nmID")));
            i++;
        }
        return products;
    }

    public static List<Product> getProductsWithPrice() throws IOException, JSONException {
        List<Product> products = WildberriesApiToEntity.getProducts();
        JSONArray arr = WildberriesApi.getPrice();
        int i = 0;
        while(!arr.isNull(i)){
            String nmId = arr.getJSONObject(i).getString("nmId");
            for (Product product : products) {
                if(product.getNmId().equals(nmId)){
                    product.setPrice(Integer.parseInt(arr.getJSONObject(i).getString("price")));
                }
            }
            i++;
        }
        return products;
    }

    public static List<Product> updatePrices() throws GeneralSecurityException, IOException, JSONException {
        List<Product> products = WildberriesApiToEntity.getProductsWithPrice();

        final String spreadsheetId = "1n1t-se_2Ie9GgNHr2EtH26JDKhiXxi2W53OgGqv6quw";
        final String range = "Wildberries 2.0!G5:CT";
        List<List<Object>> values = GoogleService.read(spreadsheetId,range);
        for (List row : values) {
            String article = String.valueOf(row.get(0));
            String price = String.valueOf(row.get(91));
            if(!row.isEmpty()&&String.valueOf(row.get(91)).contains("₽")&&!row.get(0).equals("")){
                price = price.replace(" ","").replace("₽","").replace("\u00a0","");
                for (Product product : products) {
                    if(product.getArticle().equals(row.get(0))){
                        product.setNewPrice(Integer.parseInt(price));
                    }
                }
            }
        }
        return products;
    }

    static class StockComparator implements Comparator<Stock> {
        @Override
        public int compare(Stock a, Stock b){

            return a.getSupplierArticle().toUpperCase().compareTo(b.getSupplierArticle().toUpperCase());
        }

    }
}

