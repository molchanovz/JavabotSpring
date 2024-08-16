package com.example.javabotspring.logic.googleSheets;

import com.example.javabotspring.bot.components.Service;
import com.example.javabotspring.logic.entities.Order;
import com.example.javabotspring.logic.entities.Sale;
import com.example.javabotspring.logic.entities.Stock;
import com.example.javabotspring.logic.WildberriesHandler.WildberriesApiToEntity;
import org.json.JSONException;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.javabotspring.logic.googleSheets.GoogleService.write;

public class WildberriesToSheet {

    String key;

    String listName;

    public WildberriesToSheet(String key, String listName) {
        this.key = key;
        this.listName = listName;
    }

    public String getLastDayOrders() {
        /** задаем параметры даты **/
        int lastDay = 1;

        int deltaHours1 = lastDay * -24;
        int deltaHours2 = (lastDay - 1) * -24;


        /** получаем массив всех заказов **/
        List<Order> orderListALL = null;
        try {
            orderListALL = WildberriesApiToEntity.getLastDayOrders(key, "all", deltaHours1, deltaHours2);
        } catch (IOException e) {
            new RuntimeException(e);
            return e.getMessage();
        } catch (InterruptedException e) {
            new RuntimeException(e);
            return e.getMessage();
        }
        /** считаем количество уникальных артикулов в заказах **/
        HashMap<String, Integer> uniqElements = new HashMap<>();
        double price = 0;
        for (Order el : orderListALL) {
            price += el.getPrice();
            if (uniqElements.containsKey(el.getArticle())) {
                uniqElements.put(el.getArticle(), uniqElements.get(el.getArticle()) + 1);
            } else uniqElements.put(el.getArticle(), 1);
        }

        /** получаем массив заказов фбс **/
        List<Order> orderListFBS = null;
        try {
            orderListFBS = WildberriesApiToEntity.getLastDayOrders(key, "fbs", deltaHours1, deltaHours2);
        } catch (IOException e) {
            new RuntimeException(e);
            return e.getMessage();
        } catch (InterruptedException e) {
            new RuntimeException(e);
            return e.getMessage();
        }
        /** считаем количество уникальных артикулов в заказах **/
        HashMap<String, Integer> uniqElements2 = new HashMap<>();
        for (Order el : orderListFBS) {
            if (uniqElements2.containsKey(el.getArticle())) {
                uniqElements2.put(el.getArticle(), uniqElements2.get(el.getArticle()) + 1);
            } else uniqElements2.put(el.getArticle(), 1);
        }

        /** получаем массив возвратов **/

        for (Order el : orderListALL) {
            if(el.getOrderType().contains("Возврат")){
                System.out.println(el.getArticle()+" "+el.getOrderType());
            }

        }

        List<Sale> returns = null;
        try {
            returns = WildberriesApiToEntity.getReturns(key, deltaHours1);
        } catch (IOException e) {
            new RuntimeException(e);
            return e.getMessage();
        } catch (JSONException e) {
            new RuntimeException(e);
            return e.getMessage();
        }
        /** считаем количество уникальных артикулов в заказах **/
        HashMap<String, Integer> uniqElements3 = new HashMap<>();
        for (Sale el : returns) {
            if (uniqElements3.containsKey(el.getArticle())) {
                uniqElements3.put(el.getArticle(), uniqElements3.get(el.getArticle()) + 1);
            } else uniqElements3.put(el.getArticle(), 1);
        }


        //https://docs.google.com/spreadsheets/d/1nCwABYOyrnFWDLnI5UmqFFrwbGN-RlLtSG-LmPspQ0U/edit?usp=sharing
        //https://docs.google.com/spreadsheets/d/1dZqPNaSMrzEWb8dns1jPQRTqJN5CEnvdANzCRQtwvJo/edit?usp=sharing
        //https://docs.google.com/spreadsheets/d/1oRCYqNPXZyKgOSM-T8D4JbKBDVTaL0jNLfdLf0od2gw/edit#gid=0
        //https://docs.google.com/spreadsheets/d/1UIBQo-ZEFNcV0iI4CF-RzQVsVcoteSFdpQ_KSZb5gGM/edit?usp=sharing
        String spreadsheetId = "1UIBQo-ZEFNcV0iI4CF-RzQVsVcoteSFdpQ_KSZb5gGM";
        String listName = "Заказы WB-" + Service.getDayDate(deltaHours1) + this.listName;
        String range = listName + "!A:B";
        String range2 = listName + "!D2:E";
        String range3 = listName + "!G2:H";

        List<List<Object>> stroki = new ArrayList<>();
        List<Object> date = new ArrayList<>();
        date.add("Отчет за " + Service.getDate(deltaHours1));
        List<Object> article = new ArrayList<>();
        article.add("Артикул");
        article.add("Заказано всего");
        stroki.add(date);
        stroki.add(article);


        for (Map.Entry entry : uniqElements.entrySet()) {
            List<Object> stroka = new ArrayList<>();
            stroka.add(String.valueOf(entry.getKey()));
            stroka.add(String.valueOf(entry.getValue()));
            stroki.add(stroka);
        }
        List<Object> priceStr = new ArrayList<>();
        priceStr.add("Всего");
        priceStr.add((int) price + "₽");
        stroki.add(priceStr);
        try {
            write(spreadsheetId, range, stroki);
        } catch (GeneralSecurityException e) {
            new RuntimeException(e);
            return e.getMessage();
        } catch (IOException e) {
            new RuntimeException(e);
            return e.getMessage();
        }

        List<List<Object>> stroki2 = new ArrayList<>();
        List<Object> article2 = new ArrayList<>();
        article2.add("Артикул");
        article2.add("Заказано фбс");
        stroki2.add(article2);
        for (Map.Entry entry : uniqElements2.entrySet()) {
            List<Object> stroka = new ArrayList<>();
            stroka.add(String.valueOf(entry.getKey()));
            stroka.add(String.valueOf(entry.getValue()));
            stroki2.add(stroka);
        }
        try {
            write(spreadsheetId, range2, stroki2);
        } catch (GeneralSecurityException e) {
            new RuntimeException(e);
            return e.getMessage();
        } catch (IOException e) {
            new RuntimeException(e);
            return e.getMessage();
        }

        List<List<Object>> stroki3 = new ArrayList<>();
        List<Object> article3 = new ArrayList<>();
        article3.add("Артикул");
        article3.add("Возвращено");
        stroki3.add(article3);
        if (!uniqElements3.isEmpty()) {
            for (Map.Entry entry : uniqElements3.entrySet()) {
                List<Object> stroka = new ArrayList<>();
                stroka.add(String.valueOf(entry.getKey()));
                stroka.add(String.valueOf(entry.getValue()));
                stroki3.add(stroka);
            }
        } else {
            List<Object> stroka = new ArrayList<>();
            stroka.add("");
            stroka.add("Нет возвратов");
            stroki3.add(stroka);
        }
        try {
            write(spreadsheetId, range3, stroki3);
        } catch (GeneralSecurityException e) {
            new RuntimeException(e);
            return e.getMessage();
        } catch (IOException e) {
            new RuntimeException(e);
            return e.getMessage();
        }

        return "Заказы были внесены в лист " + listName;
    }

    public String getStocks() throws IOException {

        List<Stock> stocks = WildberriesApiToEntity.getStocks(key);

        /**Уникальные склады*/
        HashMap<String, Integer> warehouseNamesMap = new HashMap<>();
        warehouseNamesMap.put(stocks.get(0).getWarehouseName(), 0);
        for (Stock stock : stocks) {
            if (!warehouseNamesMap.containsKey(stock.getWarehouseName())) {
                warehouseNamesMap.put(stock.getWarehouseName(), 0);
            }
        }
        for (Stock stock : stocks) {
            stock.setWarehouseNamesMap(warehouseNamesMap);
        }

        List<Stock> stocksNew = new ArrayList<>();

        stocksNew.add(stocks.get(0));
        HashMap<String, Integer> map = new HashMap<>();
        map.putAll(stocksNew.get(0).getWarehouseNamesMap());
        map.put(stocks.get(0).getWarehouseName(), stocksNew.get(0).getWarehouseNamesMap().get(stocks.get(0).getWarehouseName()) + stocks.get(0).getCount());
        stocksNew.get(0).setWarehouseNamesMap(map);
        int i = 0;
        for (Stock stock : stocks) {
            if (!stocksNew.get(i).getSupplierArticle().equals(stock.getSupplierArticle())) {
                i++;
                stocksNew.add(stock);
                HashMap<String, Integer> map2 = new HashMap<>();
                map2.putAll(stocksNew.get(i).getWarehouseNamesMap());
                int oldS = stocksNew.get(i).getWarehouseNamesMap().get(stock.getWarehouseName());
                int newS = stock.getCount();
                map2.put(stock.getWarehouseName(), oldS + newS);
                stocksNew.get(i).setWarehouseNamesMap(map2);
                stocksNew.get(i).setCount(stock.getCount());
                stocksNew.get(i).setInWayFromClient(stock.getInWayFromClient());
                stocksNew.get(i).setInWayToClient(stock.getInWayToClient());
            } else {
                HashMap<String, Integer> map2 = new HashMap<>();
                map2.putAll(stocksNew.get(i).getWarehouseNamesMap());
                map2.put(stock.getWarehouseName(), stocksNew.get(i).getWarehouseNamesMap().get(stock.getWarehouseName()) + stock.getCount());
                stocksNew.get(i).setWarehouseNamesMap(map2);
                stocksNew.get(i).setCount(stocksNew.get(i).getCount() + stock.getCount());
                stocksNew.get(i).setInWayFromClient(stocksNew.get(i).getInWayFromClient() + stock.getInWayFromClient());
                stocksNew.get(i).setInWayToClient(stocksNew.get(i).getInWayToClient() + stock.getInWayToClient());
            }
        }

        //https://docs.google.com/spreadsheets/d/1cJJzzRhxtugHTQ0WPJAXU9amY29DFio12B-8wSEkiG8/edit?usp=sharing
        String spreadsheetId = "1cJJzzRhxtugHTQ0WPJAXU9amY29DFio12B-8wSEkiG8";
        //String listName = "Отчет-" + Service.getDayDate(deltaHours1)+this.listName;
        String listName = "Отчет-"+Service.getDayDate(1)+ this.listName;

        String range = listName + "!A:AZ";

        List<List<Object>> stroki = new ArrayList<>();
        List<Object> article = new ArrayList<>();
        article.add("Бренд");
        article.add("Предмет");
        article.add("Артикул продавца");
        article.add("Артикул WB");
        article.add("Последнее изменение");
        article.add("Баркод");
        article.add("Размер вещи");
        article.add("В пути до клиента");
        article.add("В пути от клиента");
        article.add("Итого по складам");
        for (String value : warehouseNamesMap.keySet()) {
            article.add(value);
        }
        stroki.add(article);

        for (Stock stock : stocksNew) {
            article = new ArrayList<>();
            article.add(stock.getBrand());
            article.add(stock.getSubject());
            article.add(stock.getSupplierArticle());
            article.add(stock.getNmId());
            article.add(stock.getLastChangeDate());
            article.add(stock.getBarcode());
            article.add(stock.getTechSize());
            article.add(stock.getInWayToClient());
            article.add(stock.getInWayFromClient());
            article.add(stock.getCount());
            for (Integer value : stock.getWarehouseNamesMap().values()) {
                article.add(value);
            }
            stroki.add(article);
        }

        try {
            write(spreadsheetId, range, stroki);
        } catch (GeneralSecurityException e) {
            throw new RuntimeException(e);
        }


        return "Заказы были внесены в лист " + listName;


    }
}
