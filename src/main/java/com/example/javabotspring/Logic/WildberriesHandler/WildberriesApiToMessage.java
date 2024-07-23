package com.example.javabotspring.Logic.WildberriesHandler;

import com.example.javabotspring.Bot.Protection;
import com.example.javabotspring.Bot.components.Service;
import com.example.javabotspring.Logic.Entities.Order;
import com.example.javabotspring.Logic.Entities.Product;
import com.example.javabotspring.Logic.Entities.Sale;
import com.opencsv.exceptions.CsvValidationException;
import org.apache.poi.ss.usermodel.*;
import org.json.JSONException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.*;

import static com.example.javabotspring.Logic.googleSheets.GoogleService.write;

public class WildberriesApiToMessage {
    public static String getOrders_All(String key) throws IOException, InterruptedException {
        //String date = Service.getDate(0);
        String date = "2023-10-02T11:40:01";
        System.out.println(date);
        /** получаем массив заказов **/
        List<Order> orderList = WildberriesApiToEntity.getOrders_All(key,date);
        /** считаем количество уникальных артикулов в заказах **/
        HashMap<String, Integer> uniqElements = new HashMap<>();
        double price = 0;
        for (Order el : orderList) {
            price += el.getPrice();
            if (uniqElements.containsKey(el.getArticle())) {
                uniqElements.put(el.getArticle(), uniqElements.get(el.getArticle()) + 1);
            } else uniqElements.put(el.getArticle(), 1);
        }
        /** генерируем сообщения для отправки **/
        if (orderList.size() > 0) {
            String message = "Новый FBO заказ на вб:\n\n";
            for (Map.Entry entry : uniqElements.entrySet()) {
                message += "• " + entry + "шт\n\n";
            }
            String resultPrice = String.format("%.2f", price);
            message += "Всего: " + resultPrice + "₽";
            return message;
        } else
            System.out.println("Пусто");
        return "";


    }

    public static String getOrders_FBS(String key) throws IOException {
        /** получаем массив заказов **/
        List<Order> orderList = WildberriesApiToEntity.getOrders_FBS(key);
        /** считаем количество уникальных артикулов в заказах **/
        HashMap<String, Integer> uniqElements = new HashMap<>();
        double price = 0;
        for (Order el : orderList) {
            price += el.getPrice();
            if (uniqElements.containsKey(el.getArticle())) {
                uniqElements.put(el.getArticle(), uniqElements.get(el.getArticle()) + 1);
            } else uniqElements.put(el.getArticle(), 1);
        }
        /** генерируем сообщения для отправки **/
        if (orderList.size() > 0) {
            String message = "Новый FBS заказ на вб:\n\n";
            for (Map.Entry entry : uniqElements.entrySet()) {
                message += "• " + entry + "шт\n\n";
            }
            String resultPrice = String.format("%.2f", price);
            message += "Всего: " + resultPrice + "₽";
            return message;
        } else
            return "";


    }

    public static String getLastDayOrders(String key) throws IOException, JSONException, InterruptedException {
        /** задаем параметры даты **/
        int deltaHours1 = -24;
        int deltaHours2 = 0;

        /** получаем массив всех заказов **/
        List<Order> orderListALL = WildberriesApiToEntity.getLastDayOrders(key,"all", deltaHours1, deltaHours2);
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
        List<Order> orderListFBS = WildberriesApiToEntity.getLastDayOrders(key,"fbs", deltaHours1, deltaHours2);
        /** считаем количество уникальных артикулов в заказах **/
        HashMap<String, Integer> uniqElements2 = new HashMap<>();
        for (Order el : orderListFBS) {
            if (uniqElements2.containsKey(el.getArticle())) {
                uniqElements2.put(el.getArticle(), uniqElements2.get(el.getArticle()) + 1);
            } else uniqElements2.put(el.getArticle(), 1);
        }

        /** получаем массив возвратов **/
        List<Sale> returns = WildberriesApiToEntity.getReturns(key,deltaHours1);
        /** считаем количество уникальных артикулов в заказах **/
        HashMap<String, Integer> uniqElements3 = new HashMap<>();
        for (Sale el : returns) {
            if (uniqElements3.containsKey(el.getArticle())) {
                uniqElements3.put(el.getArticle(), uniqElements3.get(el.getArticle()) + 1);
            } else uniqElements3.put(el.getArticle(), 1);
        }

        String sheetName = "Заказы WB-" + Service.getDayDate(deltaHours1);
        Workbook book = Service.createExcel_LastDayOrders(sheetName, "фбс", Service.getDate(deltaHours1));
        String file = Protection.lastOrdersWB;
        Sheet sheet = book.getSheet(sheetName);
        int i = 2;
        if (orderListALL.size() > 0) {
            String message = "Заказы за вчера:\n\n";
            for (Map.Entry entry : uniqElements.entrySet()) {
                Row row0 = sheet.createRow(i++);
                Cell art1 = row0.createCell(0);
                art1.setCellValue(String.valueOf(entry.getKey()));
                Cell art2 = row0.createCell(1);
                art2.setCellValue(String.valueOf(entry.getValue()));
            }
            int j = 2;
            for (Map.Entry entry : uniqElements2.entrySet()) {
                Row row0 = sheet.getRow(j++);
                Cell art1 = row0.createCell(3);
                art1.setCellValue(String.valueOf(entry.getKey()));
                Cell art2 = row0.createCell(4);
                art2.setCellValue(String.valueOf(entry.getValue()));
            }
            int k = 2;
            if (!uniqElements3.isEmpty()) {
                for (Map.Entry entry : uniqElements3.entrySet()) {
                    Row row0 = sheet.getRow(k++);
                    Cell art1 = row0.createCell(6);
                    art1.setCellValue(String.valueOf(entry.getKey()));
                    Cell art2 = row0.createCell(7);
                    art2.setCellValue(String.valueOf(entry.getValue()));
                }
            } else {
                Row row0 = sheet.getRow(k);
                Cell art1 = row0.createCell(7);
                art1.setCellValue("Нет возвратов");
            }


            String resultPrice = String.format("%.2f", price);
            Row row0 = sheet.createRow(i);
            Cell art1 = row0.createCell(0);
            art1.setCellValue("Всего");
            Cell art2 = row0.createCell(1);
            art2.setCellValue(resultPrice + "₽");
            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);
            sheet.autoSizeColumn(3);
            sheet.autoSizeColumn(4);
            sheet.autoSizeColumn(6);
            sheet.autoSizeColumn(7);
            book.write(new FileOutputStream(file));
            book.close();
            return message;
        } else
            return "";
    }

    public static String getOrdersInSupply(String supplyNum) throws IOException, JSONException {
        /** получаем массив заказов **/
        ArrayList<Order> orders = WildberriesApiToEntity.getOrdersInSupply(supplyNum);
        /** считаем количество уникальных артикулов в заказах **/
        HashMap<String, Integer> uniqElements = new HashMap<>();
        for (Order el : orders) {
            if (uniqElements.containsKey(el.getArticle())) {
                uniqElements.put(el.getArticle(), uniqElements.get(el.getArticle()) + 1);
            } else uniqElements.put(el.getArticle(), 1);
        }

        String sheetName = "Заказы WB-" + Service.getDayDate(-24);
        Workbook book = Service.createExcel_ordersBySupply(sheetName, supplyNum);
        String file = Protection.ordersBySupplyWB;
        Sheet sheet = book.getSheet(sheetName);
        int i = 2;
        if (orders.size() > 0) {

            for (Map.Entry entry : uniqElements.entrySet()) {
                Row row0 = sheet.createRow(i++);
                Cell art1 = row0.createCell(0);
                art1.setCellValue(String.valueOf(entry.getKey()));
                Cell art2 = row0.createCell(1);
                art2.setCellValue(String.valueOf(entry.getValue()));
            }
            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);
            book.write(new FileOutputStream(file));
            book.close();
            String message = "Заказы в поставке";
            return message;
        } else
            return "";
    }

    public static String getTurnover() throws CsvValidationException, IOException, InterruptedException, JSONException {
        /** получаем массив остатков **/
        ArrayList<Product> products = null;
        //WildberriesApiToEntity.getTurnover();

        Workbook book = Service.createExcel_Turnover("Оборачиваемость Ozon");

        String file = Protection.turnoverWB;
        Sheet sheet = book.getSheet("Оборачиваемость Ozon");
        int i = 1;
        if (products.size() > 0) {
            String message = "Оборачиваемость:\n\n";
            for (Product product : products) {
                String article = product.getArticle();
                int count = product.getCountOrders();
                int stock = product.getStock();
                int stockInWay = product.getStockInWay();
                int days = product.getDayForSale();

                Row row0 = sheet.createRow(i++);
                Cell art = row0.createCell(0);
                art.setCellValue(article);

                Cell art1 = row0.createCell(1);
                art1.setCellValue(stock);

                Cell art2 = row0.createCell(2);
                art2.setCellValue(stockInWay);

                Cell art3 = row0.createCell(3);
                art3.setCellValue(count);

                Cell art4 = row0.createCell(4);
                art4.setCellValue(days);

                Cell art5 = row0.createCell(5);

                if (stock < 1 & count > 0) {
                    art5.setCellValue("Нет на складе, но есть продажи");
                } else if (stock < 1 & count == 0) {
                    art5.setCellValue("Нет на складе, нет продаж");
                } else if (count < 1) {
                    art5.setCellValue("Нет продаж");
                } else if (days < 20) {
                    art5.setCellValue("Поставить на склад");
                } else if (days >= 10 & days <= 60) {
                    art5.setCellValue("Норм");
                } else if (days > 60) {
                    art5.setCellValue("Неликвид");
                } else art5.setCellValue("Ошибка");
            }

            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);
            sheet.autoSizeColumn(2);
            sheet.autoSizeColumn(3);
            sheet.autoSizeColumn(4);
            sheet.autoSizeColumn(5);
            book.write(new FileOutputStream(file));
            book.close();
            return message;
        } else
            return "";
    }

    public static List<Product> updatePrices() throws GeneralSecurityException, IOException, JSONException {
        List<Product> products = WildberriesApiToEntity.updatePrices();

        int deltaHours2=0;
        String date = Service.getDate(deltaHours2);
        //https://docs.google.com/spreadsheets/d/1_fbbuQF5KFVVGsM9tVrJ-4kt-GnUz2bUTimxanq70PY/edit?usp=sharing
        String spreadsheetId = "1_fbbuQF5KFVVGsM9tVrJ-4kt-GnUz2bUTimxanq70PY";
        String listName = "Wildberries";
        String range = listName+"!A:D";

        List<List<Object>> stroki = new ArrayList<>();
        List<Object> dateStr = new ArrayList<>();
        dateStr.add("Обновление цен на "+date);
        List<Object> article = new ArrayList<>();
        article.add("Артикул");
        article.add("Номенклатура");
        article.add("Старая цена");
        article.add("Новая цена");
        stroki.add(dateStr);
        stroki.add(article);



        for (Product product : products) {
            if(product.getNewPrice()>0){
                String str = product.getArticle()+" "+product.getNmId()+" "+product.getPrice()+" "+product.getNewPrice();
                List<Object> stroka = new ArrayList<>();
                stroka.add(product.getArticle());
                stroka.add(product.getNmId());
                stroka.add(product.getPrice()/2);
                stroka.add(product.getNewPrice()/2);
                stroki.add(stroka);
            }
        }

        write(spreadsheetId,range,stroki);

        return products;

        /*for (Product product : products) {
            if (product.getNewPrice()>0) {
                String prod = "{\n" +
                        "\"nmId\":"+product.getNmId()+",\n" +
                        "\"price\": 5000\n" +
                        "}\n";
                System.out.println(product);
            }
        }*/
    }

    public static String updatePrices_allow() throws GeneralSecurityException, IOException, JSONException {

        List<Product> products= updatePrices();
        System.out.println("https://docs.google.com/spreadsheets/d/1_fbbuQF5KFVVGsM9tVrJ-4kt-GnUz2bUTimxanq70PY/edit?usp=sharing");
        System.out.println("Все верно? (y/n)" );
        Scanner sc = new Scanner(System.in);
        //List<String> str = new ArrayList<>();
        String str = "";
        String answer = sc.nextLine();
        int k = 0;
        if (answer.equals("y")){
            str+= "{\n" +
                    "\"nmId\":"+products.get(0).getNmId()+",\n" +
                    "\"price\": "+products.get(0).getNewPrice()+"\n" +
                    "}";

            for (int i = k+1; i < products.size(); i++) {
                if (products.get(i).getNewPrice()>0) {
                    str+= ",\n{\n" +
                            "\"nmId\":"+products.get(i).getNmId()+",\n" +
                            "\"price\": "+products.get(i).getNewPrice()+"\n" +
                            "}";
                }

            }

            System.out.println(str);
            WildberriesApi.setPrice(str);
        }
        else System.out.println("Не делаем");
        return "OK";
    }
}
