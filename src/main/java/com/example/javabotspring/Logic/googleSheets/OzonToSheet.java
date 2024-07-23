package com.example.javabotspring.Logic.googleSheets;

import com.example.javabotspring.Bot.components.Service;
import com.example.javabotspring.Logic.Entities.Order;
import com.example.javabotspring.Logic.Entities.Return;
import com.example.javabotspring.Logic.Entities.Stock;
import com.example.javabotspring.Logic.OzonHandler.OzonApiToEntity;
import com.example.javabotspring.Logic.OzonHandler.OzonApiToMessage;
import com.opencsv.exceptions.CsvValidationException;
import org.json.JSONException;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.javabotspring.Logic.googleSheets.GoogleService.write;


public class OzonToSheet {
    String clientId;
    String key;
    String listName;

    public OzonToSheet(String clientId, String key, String listName) {
        this.clientId = clientId;
        this.key = key;
        this.listName = listName;
    }

    public String getLastOrders() {

        int lastDay = 2;

        int deltaHours1 = lastDay * -24;

        int deltaHours2 = (lastDay - 1) * -24;


        String lastDate = Service.getDate(deltaHours1);
        String date = Service.getDate(deltaHours2);
        /** получаем массив всех заказов **/
        ArrayList<Order> ordersFBO = null;
        ArrayList<Order> ordersFBS = null;
        try {
            ordersFBO = OzonApiToEntity.getLastOrders(lastDate, date, "\"fbo\"", clientId, key);
            ordersFBS = OzonApiToEntity.getLastOrders(lastDate, date, "\"fbs\"", clientId, key);
        } catch (IOException e) {
            new RuntimeException(e);
        }

        /** считаем количество уникальных артикулов в заказах **/
        HashMap<String, Integer> uniqElementsAll = new HashMap<>();
        double price = 0;
        for (Order el : ordersFBO) {
            price += el.getPrice();
            if (uniqElementsAll.containsKey(el.getArticle())) {
                uniqElementsAll.put(el.getArticle(), uniqElementsAll.get(el.getArticle()) + el.getCount());
            } else uniqElementsAll.put(el.getArticle(), el.getCount());
        }
        for (Order el : ordersFBS) {
            price += el.getPrice();
            if (uniqElementsAll.containsKey(el.getArticle())) {
                uniqElementsAll.put(el.getArticle(), uniqElementsAll.get(el.getArticle()) + el.getCount());
            } else uniqElementsAll.put(el.getArticle(), el.getCount());
        }

        /** получаем массив всех заказов **/
        ArrayList<Order> orders2 = null;
        try {
            orders2 = OzonApiToEntity.getLastOrders(lastDate, date, "\"fbo\"", clientId, key);
        } catch (IOException e) {
            new RuntimeException(e);
            return e.getMessage();
        }
        /** считаем количество уникальных артикулов в заказах **/
        HashMap<String, Integer> uniqElementsAll2 = new HashMap<>();
        for (Order el : orders2) {
            if (uniqElementsAll2.containsKey(el.getArticle())) {
                uniqElementsAll2.put(el.getArticle(), uniqElementsAll2.get(el.getArticle()) + el.getCount());
            } else uniqElementsAll2.put(el.getArticle(), el.getCount());
        }

        /** получаем массив возвратов **/
        List<Return> returns = null;
        OzonApiToMessage ozonTrade = new OzonApiToMessage(clientId, key);
        try {
            returns = ozonTrade.getReturns(deltaHours1, deltaHours2);
        } catch (JSONException e) {
            new RuntimeException(e);
            return e.getMessage();
        } catch (IOException e) {
            new RuntimeException(e);
            return e.getMessage();
        } catch (InterruptedException e) {
            new RuntimeException(e);
            return e.getMessage();
        } catch (ParseException e) {
            new RuntimeException(e);
            return e.getMessage();
        }
        /** считаем количество уникальных артикулов в заказах **/
        HashMap<String, Integer> uniqElements3 = new HashMap<>();



        //https://docs.google.com/spreadsheets/d/1FHUsiBT_SPicYIEsrAnej1gOXo3DhQJ-1K4IAZsqjOw/edit?usp=sharing
        //https://docs.google.com/spreadsheets/d/1-fpKvUjHj7EIMWxdonjq26uqUgvXb-vGWxiT4lWdmmE/edit?usp=sharing
        //https://docs.google.com/spreadsheets/d/1TfYJ3Rn-lW8X2Fo4GMJwQqxDiGMdrUfiv5qwlSCShFA/edit?usp=sharing
        //https://docs.google.com/spreadsheets/d/16Adux1rLMuT5vA99jucyjORBl6JE0IaJ26xa_bA9y80/edit#gid=0
        //https://docs.google.com/spreadsheets/d/1eQGQltHkl7ncgsNNLULKTm84nIfucTo-fwc_GCgZzoo/edit?usp=sharing
        String spreadsheetId = "1eQGQltHkl7ncgsNNLULKTm84nIfucTo-fwc_GCgZzoo";
        String listName = "Заказы OZON-" + Service.getDayDate(deltaHours1) + this.listName;
        String range = listName + "!A:B";
        String range2 = listName + "!D2:E";
        String range3 = listName + "!G2:H";

        List<List<Object>> stroki = new ArrayList<>();
        List<Object> dateStr = new ArrayList<>();
        dateStr.add("Отчет за " + lastDate);
        List<Object> article = new ArrayList<>();
        article.add("Артикул");
        article.add("Заказано всего");
        stroki.add(dateStr);
        stroki.add(article);


        for (Map.Entry entry : uniqElementsAll.entrySet()) {
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
        article2.add("Заказано фбо");
        stroki2.add(article2);
        for (Map.Entry entry : uniqElementsAll2.entrySet()) {
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
        System.out.println("Заказы были внесены в лист " + listName);
        return "Заказы были внесены в лист " + listName;
    }

    public String getStocks(){
        List<Stock> stocks = new ArrayList<>();
        try {
            stocks = OzonApiToEntity.getStock_v2(clientId,key);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        //https://docs.google.com/spreadsheets/d/1aHPfCPLWizFniKgWAF4T_-U_zDy573Yl-EBKezIt6wU/edit?usp=sharing
        //https://docs.google.com/spreadsheets/d/1C4vxtOpAYadmmBUFsbD32qeN7SQhuv7xMTiGPAzoUeE/edit?usp=sharing
        //https://docs.google.com/spreadsheets/d/1E9VWA_HIBkKoprc6e1GSwZ-Ar8XAEHukvgQp0996x88/edit?gid=1957459558#gid=1957459558
        String spreadsheetId = "1E9VWA_HIBkKoprc6e1GSwZ-Ar8XAEHukvgQp0996x88";
        String listName = "Отчет-" + Service.getDayDate(0) + this.listName;
        String range = listName + "!A:Z";

        List<List<Object>> stroki = new ArrayList<>();
        List<Object> str = new ArrayList<>();
        str.add("Отчёт по остаткам и товарам в пути на склады Ozon");
        stroki.add(str);

        str = new ArrayList<>();
        str.add("Дата формирования");
        str.add(Service.getDate(0));
        stroki.add(str);
        List<Object> article = new ArrayList<>();
        article.add("SKU");
        article.add("Название склада");
        article.add("Артикул");
        article.add("Название товара");
        article.add("Товары в пути");
        article.add("Доступный к продаже товар");
        article.add("Резерв");
        stroki.add(article);

        for (Stock stock : stocks) {
            article = new ArrayList<>();
            article.add(stock.getSku());
            article.add(stock.getWarehouseName());
            article.add(stock.getSupplierArticle());
            article.add(stock.getName());
            article.add(stock.getInWayFromClient());
            article.add(stock.getCount());
            article.add(stock.getReservedCount());
            stroki.add(article);
        }

        try {
            write(spreadsheetId, range, stroki);
        } catch (GeneralSecurityException e) {
            new RuntimeException(e);
            return e.getMessage();
        } catch (IOException e) {
            new RuntimeException(e);
            return e.getMessage();
        }
        return "";
    }

}
