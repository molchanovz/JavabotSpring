package com.example.javabotspring.logic.OzonHandler;

import com.example.javabotspring.Protection;
import com.example.javabotspring.bot.components.Service;
import com.example.javabotspring.logic.entities.Order;
import com.example.javabotspring.logic.entities.Product;
import com.example.javabotspring.logic.entities.Return;
import com.example.javabotspring.logic.entities.Stock;
import com.opencsv.exceptions.CsvValidationException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.json.JSONException;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class OzonApiToMessage {
    String clientId;
    String key;

    public OzonApiToMessage(String clientId, String key) {
        this.clientId = clientId;
        this.key = key;
    }

    public  String getLastOrders() throws CsvValidationException, IOException, InterruptedException {
        String lastDate = Service.getDate(-24);
        String date = Service.getDate(0);

        /** получаем массив всех заказов **/
        ArrayList<Order> ordersFBO = OzonApiToEntity.getLastOrders(lastDate,date,"\"fbo\"",clientId,key);
        /** считаем количество уникальных артикулов в заказах **/
        HashMap<String, Integer> uniqElementsAll = new HashMap<>();
        double price = 0;
        for (Order el : ordersFBO) {
            price+=el.getPrice();
            if (uniqElementsAll.containsKey(el.getArticle())) {
                uniqElementsAll.put(el.getArticle(), uniqElementsAll.get(el.getArticle()) + el.getCount());
            } else uniqElementsAll.put(el.getArticle(), el.getCount());
        }

        /** получаем массив всех заказов **/
        ArrayList<Order> ordersFBS = OzonApiToEntity.getLastOrders(lastDate,date,"\"fbs\"", clientId, key);
        /** считаем количество уникальных артикулов в заказах **/
        HashMap<String, Integer> uniqElementsAll2 = new HashMap<>();
        for (Order el : ordersFBS) {
            if (uniqElementsAll2.containsKey(el.getArticle())) {
                uniqElementsAll2.put(el.getArticle(), uniqElementsAll2.get(el.getArticle()) + el.getCount());
            } else uniqElementsAll2.put(el.getArticle(), el.getCount());
        }

        Workbook book = Service.createExcel_LastDayOrders("Продажи Ozon","фбо",lastDate);
        String file = Protection.lastOrdersOzon;
        Sheet sheet = book.getSheet("Продажи Ozon");
        int i = 2;
        if(ordersFBO.size()>0){
            String message = "Заказы за вчера:\n\n";
            for (Map.Entry entry : uniqElementsAll.entrySet()) {
                Row row0 = sheet.createRow(i++);
                Cell art1 = row0.createCell(0);
                art1.setCellValue(String.valueOf(entry.getKey()));
                Cell art2 = row0.createCell(1);
                art2.setCellValue(String.valueOf(entry.getValue()));
            }
            int j = 2;
            for (Map.Entry entry : uniqElementsAll2.entrySet()) {
                Row row0 = sheet.getRow(j++);
                Cell art1 = row0.createCell(3);
                art1.setCellValue(String.valueOf(entry.getKey()));
                Cell art2 = row0.createCell(4);
                art2.setCellValue(String.valueOf(entry.getValue()));
            }
            String resultPrice = String.format("%.2f",price);
            Row row0 = sheet.createRow(i);
            Cell art1 = row0.createCell(0);
            art1.setCellValue("Всего");
            Cell art2 = row0.createCell(1);
            art2.setCellValue(resultPrice+"₽");

            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);
            sheet.autoSizeColumn(3);
            sheet.autoSizeColumn(4);
            book.write(new FileOutputStream(file));
            book.close();
            return message;
        }else
            return "";
    }
    public String getStocks() throws CsvValidationException, IOException, InterruptedException, JSONException {
        /** получаем массив остатков **/
        ArrayList<Stock> stocks = OzonApiToEntity.getStock_v2(clientId, key);
        /** считаем количество уникальных артикулов в остатках **/
        HashMap<String, Integer> uniqElements = new HashMap<>();
        double price = 0;
        /*for (Stock el : stocks) {
            if (uniqElements.containsKey(el.getName())) {
                uniqElements.put(el.getName(), uniqElements.get(el.getName()) + el.getCount());
            } else uniqElements.put(el.getName(), el.getCount());
        }*/
        /** получаем массив остатков **/
        ArrayList<Stock> stocks2 = OzonApiToEntity.getStock_v2(clientId, key);
        /** считаем количество уникальных артикулов в остатках **/
        HashMap<String, Integer> uniqElements2 = new HashMap<>();
        /*for (Stock el : stocks2) {
            if (uniqElements2.containsKey(el.getName())) {
                uniqElements2.put(el.getName(), uniqElements2.get(el.getName()) + el.getCountInWay());
            } else uniqElements2.put(el.getName(), el.getCountInWay());
        }*/

        Workbook book = Service.createExcel_Stocks("Остатки Ozon");
        String file = Protection.stocksOzon;
        Sheet sheet = book.getSheet("Остатки Ozon");
        int i = 1;
        if(stocks.size()>0){
            String message = "Остатки:\n\n";
            for (Map.Entry entry : uniqElements.entrySet()) {
                Row row0 = sheet.createRow(i++);
                Cell art1 = row0.createCell(0);
                art1.setCellValue(String.valueOf(entry.getKey()));
                Cell art2 = row0.createCell(1);
                art2.setCellValue(String.valueOf(entry.getValue()));
                Cell art3 = row0.createCell(2);
                if(uniqElements2.containsKey(entry.getKey())){
                    art3.setCellValue(uniqElements2.get(entry.getKey()));
                }else {
                    art3.setCellValue("0");
                }

            }

            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);
            sheet.autoSizeColumn(3);
            book.write(new FileOutputStream(file));
            book.close();
            return message;
        }else
            return "";
    }
    public String getTurnover() throws CsvValidationException, IOException, InterruptedException, JSONException {
        /** получаем массив остатков **/
        ArrayList<Product> products = OzonApiToEntity.getTurnover(clientId,key);

        Workbook book = Service.createExcel_Turnover("Оборачиваемость Ozon");

        String file = Protection.turnoverOzon;
        Sheet sheet = book.getSheet("Оборачиваемость Ozon");
        int i = 1;
        if(products.size()>0){
            String message = "Оборачиваемость:\n\n";
            for (Product product : products) {
                String article = product.getArticle();
                int count = product.getCountOrders();
                int stock = product.getStock();
                int stockInWay = product.getStockInWay();
                String warehouseName = product.getWarehouseName();
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
                art5.setCellValue(warehouseName);

                Cell art6 = row0.createCell(6);

                if (stock<1&count>0) {
                    art6.setCellValue("Нет на складе, но есть продажи");
                }else if (stock<1&count==0) {
                    art6.setCellValue("Нет на складе, нет продаж");
                } else if (count<1){
                    art6.setCellValue("Нет продаж");
                } else if (days<20) {
                    art6.setCellValue("Поставить на склад");
                } else if (days>=10&days<=60) {
                    art6.setCellValue("Норм");
                } else if (days>60) {
                    art6.setCellValue("Неликвид");
                }else art6.setCellValue("Ошибка");
            }

            sheet.autoSizeColumn(0);
            sheet.autoSizeColumn(1);
            sheet.autoSizeColumn(2);
            sheet.autoSizeColumn(3);
            sheet.autoSizeColumn(4);
            sheet.autoSizeColumn(5);
            sheet.autoSizeColumn(6);
            book.write(new FileOutputStream(file));
            book.close();
            return message;
        }else
            return "";
    }
    public List<Return> getReturns(int deltaHours1, int deltaHours2) throws JSONException, IOException, InterruptedException, ParseException {
        ArrayList<Return> returns = OzonApiToEntity.getReturns(clientId,key);
        ArrayList<Return> returns2 = new ArrayList<>();
        String dateStr = Service.getFullDate(deltaHours1);;
        SimpleDateFormat formatterDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        Date now = formatterDate.parse(dateStr);
        String dateStr2 = Service.getFullDate(deltaHours2);;
        Date now2 = formatterDate.parse(dateStr2);

        for (Return aReturn : returns) {
            String dateStr3 = aReturn.getReturned_to_ozon_moment();
            Date date = formatterDate.parse(dateStr3);

            if (date.after(now)&&date.before(now2)){
                returns2.add(aReturn);
            }

        }
        return returns2;
    }
}
