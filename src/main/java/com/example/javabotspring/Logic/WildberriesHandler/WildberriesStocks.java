package com.example.javabotspring.Logic.WildberriesHandler;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;


import java.io.IOException;

public class WildberriesStocks {
    /*public static void stocks(String key) throws IOException {
        Workbook book = createExcel();
        String file = Protection.stocksWB;
        Sheet sheet = book.getSheet("Остатки Wildberries");
        List<Stock> arr = null;
        try {
            arr = WildberriesApiToEntity.getStocks(key);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        List<Stock> sortedArr = arr.stream().sorted(new StockComparator()).toList();
        System.out.println(sortedArr);

        String nameInMemory = sortedArr.get(0).getName();
        Row row0 = sheet.createRow(1);
        Cell art0 = row0.createCell(0);
        art0.setCellValue(sortedArr.get(0).getName());
        int i = 2;
        int fullCount = 0;
        for (Stock obj : sortedArr) {
            String name = obj.getName();

            if(!name.equals(nameInMemory)){
                row0 = sheet.createRow(i++);
                Cell art = row0.createCell(0);
                art.setCellValue(name);

                switch (obj.getWarehouseName()) {
                    case ("Коледино"):
                        Cell koledino = row0.createCell(1);
                        koledino.setCellValue(obj.getCount());
                        break;
                    case ("Белые столбы"):
                        Cell stolb = row0.createCell(2);
                        stolb.setCellValue(obj.getCount());
                        break;
                    case ("Тула"):
                        Cell tula = row0.createCell(3);
                        tula.setCellValue(obj.getCount());
                        break;
                    case ("Краснодар"):
                        Cell krasnodar = row0.createCell(4);
                        krasnodar.setCellValue(obj.getCount());
                        break;
                    case ("Казань"):
                        Cell kazan = row0.createCell(5);
                        kazan.setCellValue(obj.getCount());
                        break;
                    case ("Шушары"):
                        Cell shushar = row0.createCell(8);
                        shushar.setCellValue(obj.getCount());
                        break;
                    case ("Екатерингбург"):
                        Cell ekb = row0.createCell(7);
                        ekb.setCellValue(obj.getCount());
                        break;
                    case ("Новосибирск"):
                        Cell novosib = row0.createCell(6);
                        novosib.setCellValue(obj.getCount());
                        break;


                }
                fullCount=obj.getCount();
                nameInMemory = name;
            }else {
                fullCount+=obj.getCount();
                switch (obj.getWarehouseName()) {
                    case  ("Коледино"):
                        Cell koledino = row0.createCell(1);
                        koledino.setCellValue(obj.getCount());
                        break;
                    case ("Казань"):
                        Cell kazan = row0.createCell(2);
                        kazan.setCellValue(obj.getCount());
                        break;
                    case ("Краснодар"):
                        Cell krasnodar = row0.createCell(3);
                        krasnodar.setCellValue(obj.getCount());
                        break;
                    case ("Тула"):
                        Cell tula = row0.createCell(4);
                        tula.setCellValue(obj.getCount());
                        break;
                    case ("Электросталь"):
                        Cell electro = row0.createCell(5);
                        electro.setCellValue(obj.getCount());
                        break;
                }
            }
            Cell full = row0.createCell(6);
            full.setCellValue(fullCount);
        }

        sheet.autoSizeColumn(0);
        sheet.autoSizeColumn(3);
        book.write(new FileOutputStream(file));
        book.close();
    }*/
    public static Workbook createExcel() throws IOException {
        Workbook book = new HSSFWorkbook();
        Sheet sheet = book.createSheet("Остатки Wildberries");

        // Нумерация начинается с нуля
        Row row = sheet.createRow(0);
        Cell name = row.createCell(0);
        name.setCellValue("Артикул");

        Cell koledino = row.createCell(1);
        koledino.setCellValue("Коледино");

        Cell kazan = row.createCell(2);
        kazan.setCellValue("Казань");

        Cell krasnodar = row.createCell(3);
        krasnodar.setCellValue("Краснодар");

        Cell tula = row.createCell(4);
        tula.setCellValue("Тула");

        Cell electro = row.createCell(5);
        electro.setCellValue("Электросталь");

        Cell full = row.createCell(6);
        full.setCellValue("Всего");

        // Меняем размер столбца
        return book;
    }
}

/*class StockComparator implements Comparator<Stock> {
    @Override
    public int compare(Stock a, Stock b){

        return a.getName().toUpperCase().compareTo(b.getName().toUpperCase());
    }

}*/
