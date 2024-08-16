package com.example.javabotspring.logic.OzonHandler;

import com.example.javabotspring.bot.components.Service;
import com.example.javabotspring.logic.entities.Order;
import com.example.javabotspring.logic.entities.Product;
import com.example.javabotspring.logic.entities.Return;
import com.example.javabotspring.logic.entities.Stock;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;

public class OzonApiToEntity {

    public static ArrayList<Order> getLastOrders(String lastDate, String date, String delivery_schema, String clientId, String key) throws IOException {
        String code = null;
        while ((code == null)) {
            code = OzonApi.getLastOrders_code(clientId, key, lastDate, date, delivery_schema);
        }

        String link = "";
        while (link.equals("")) {
            System.out.println(code);
            link = OzonApi.getReportInfo(clientId, key, code);
        }

        String[] lines = OzonApi.getCSV(link);

        ArrayList<Order> orders = new ArrayList<>();
        for (int i = 1; i < lines.length; i++) {
            String[] orderArr = lines[i].split(";");
            String status = orderArr[4];
            if (!status.equals("\"Отменен\"")) {
                Order order = new Order(orderArr[11].replaceAll("\"", ""), Double.parseDouble(orderArr[7].replaceAll("\"", "")), Integer.parseInt(orderArr[16].replaceAll("\"", "")));
                orders.add(order);
            }

        }

        return orders;

    }


    public static ArrayList<Product> getTurnover(String clientId, String key) throws IOException, InterruptedException, JSONException {
        ArrayList<Stock> stocks = OzonApiToEntity.getStock_v2(clientId, key);
        ArrayList<Stock> stocksInWay = OzonApiToEntity.getStock_v2(clientId, key);
        String lastDate = Service.getDate(-168);
        String date = Service.getDate(0);
        ArrayList<Order> ordersFBO = OzonApiToEntity.getLastOrders(lastDate, date, "\"fbo\"", clientId, key);
        ArrayList<Order> ordersFBS = OzonApiToEntity.getLastOrders(lastDate, date, "\"fbs\"", clientId, key);


        ArrayList<Product> products = new ArrayList<>();

        /*for (Stock stock : stocks) {
            System.out.println(stock.getWarehouseName());
            double count = 0;
            int countInWay = 0;
            for (Order order : ordersFBO) {
                if (stock.getName().equals(order.getArticle())) {
                    count += order.getCount();
                }
            }
            for (Order order : ordersFBS) {
                if (stock.getName().equals(order.getArticle())) {
                    count += order.getCount();
                }
            }
            for (Stock stock1 : stocksInWay) {
                if (stock.getName().equals(stock1.getName())) {
                    countInWay += stock1.getCountInWay();
                }
            }
            count = Math.ceil(count / 7);
            Product product = new Product(stock.getName(), stock.getCount(), countInWay, (int) count, stock.getWarehouseName());
            product.setDayForSale((int) Math.round(stock.getCount() / count));
            products.add(product);
        }*/
        return products;
    }

    public static ArrayList<Return> getReturns(String clientId, String key) throws IOException, InterruptedException, JSONException {
        JSONArray array = OzonApi.getReturns(clientId, key);
        int i = 0;
        ArrayList<Return> returns = new ArrayList<>();
        while (!array.isNull(i)) {
            String sku = array.getJSONObject(i).getString("sku");
            String posting_number = array.getJSONObject(i).getString("posting_number");
            String returned_to_ozon_moment = array.getJSONObject(i).getString("returned_to_ozon_moment");
            String status_name = array.getJSONObject(i).getString("status_name");
            Return return1 = new Return(sku, posting_number, returned_to_ozon_moment, status_name);
            returns.add(return1);
            i += 1;
        }
        return returns;
    }

    public static ArrayList<Stock> getStock_v2(String clientId, String key) throws IOException, InterruptedException, JSONException {
        JSONArray array = null;
        while ((array == null)) {
            array = OzonApi.getStock_v2(clientId, key);
        }
        int i = 0;
        ArrayList<Stock> stocks = new ArrayList<>();
        while (!array.isNull(i)) {
            String sku = array.getJSONObject(i).getString("sku");
            String warehouseName = array.getJSONObject(i).getString("warehouse_name");
            String item_code = array.getJSONObject(i).getString("item_code");
            String item_name = array.getJSONObject(i).getString("item_name");
            int promised_amount = Integer.parseInt(array.getJSONObject(i).getString("promised_amount"));
            int count = Integer.parseInt(array.getJSONObject(i).getString("free_to_sell_amount"));
            int reserved_amount = Integer.parseInt(array.getJSONObject(i).getString("reserved_amount"));
            Stock stock = new Stock(sku, warehouseName, item_code, item_name, promised_amount, count, reserved_amount);
            stocks.add(stock);
            i += 1;
        }
        return stocks;
    }

    public static String getProduct(String clientId, String key, String sku) throws IOException, JSONException {
        String article = OzonApi.getProduct(clientId, key, sku).getString("offer_id");
        return article;
    }


}
