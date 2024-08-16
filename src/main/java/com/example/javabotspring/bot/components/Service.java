package com.example.javabotspring.bot.components;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Service {
    public static String getFullDate(int deltaHours) {
        SimpleDateFormat formatterDate = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formatterTime = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        date.setTime(date.getTime() + (deltaHours * 3600000));
        return formatterDate.format(date) + "T" + formatterTime.format(date);
    }

    public static int getDayDate(int deltaHours) {
        SimpleDateFormat formatterDate = new SimpleDateFormat("dd");
        Date date = new Date();
        date.setTime(date.getTime() + (deltaHours * 3600000));
        return Integer.parseInt(formatterDate.format(date));
    }

    public static String getDate(int deltaHours) {
        SimpleDateFormat formatterDate = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        date.setTime(date.getTime() + (deltaHours * 3600000));
        return formatterDate.format(date);
    }

    public static Workbook createExcel_LastDayOrders(String sheetName,String type, String lastDate) throws IOException {
        Workbook book = new HSSFWorkbook();
        Sheet sheet = book.createSheet(sheetName);

        // Нумерация начинается с нуля
        Row row2 = sheet.createRow(0);
        Cell date = row2.createCell(0);
        date.setCellValue("Отчет за "+lastDate);

        Row row = sheet.createRow(1);
        Cell name = row.createCell(0);
        name.setCellValue("Артикул");

        Cell order = row.createCell(1);
        order.setCellValue("Заказано всего");

        Cell nameFBO = row.createCell(3);
        nameFBO.setCellValue("Артикул");

        Cell orderFBO = row.createCell(4);
        orderFBO.setCellValue("Заказано "+type);

        Cell nameReturn = row.createCell(6);
        nameReturn.setCellValue("Артикул");

        Cell orderReturn = row.createCell(7);
        orderReturn.setCellValue("Возвращено");

        // Меняем размер столбца
        return book;
    }

    public static Workbook createExcel_Stocks(String sheetName) throws IOException {
        Workbook book = new HSSFWorkbook();
        Sheet sheet = book.createSheet(sheetName);

        // Нумерация начинается с нуля
        Row row = sheet.createRow(0);
        Cell name = row.createCell(0);
        name.setCellValue("Артикул");

        Cell count = row.createCell(1);
        count.setCellValue("Остаток на "+getDate(0));

        Cell countInWay = row.createCell(2);
        countInWay.setCellValue("В пути");

        // Меняем размер столбца
        return book;
    }

    public static Workbook createExcel_Turnover(String sheetName) throws IOException {
        Workbook book = new HSSFWorkbook();
        Sheet sheet = book.createSheet(sheetName);

        // Нумерация начинается с нуля
        Row row = sheet.createRow(0);
        Cell name = row.createCell(0);
        name.setCellValue("Артикул");

        Cell art1 = row.createCell(1);
        art1.setCellValue("Остаток");

        Cell art2 = row.createCell(2);
        art2.setCellValue("Едет на склад");

        Cell art3 = row.createCell(3);
        art3.setCellValue("Кол-во заказов за 7 дней");

        Cell art4 = row.createCell(4);
        art4.setCellValue("Хватит на столько дней");

        Cell art5 = row.createCell(5);
        art5.setCellValue("Склад");

        Cell art6 = row.createCell(6);
        art6.setCellValue("Вердикт");

        // Меняем размер столбца
        return book;
    }

    public static Workbook createExcel_ordersBySupply(String sheetName, String supplyNum) throws IOException {
        Workbook book = new HSSFWorkbook();
        Sheet sheet = book.createSheet(sheetName);

        // Нумерация начинается с нуля
        Row row = sheet.createRow(0);
        Cell name = row.createCell(0);
        name.setCellValue("Поставка");

        Cell art = row.createCell(1);
        art.setCellValue(supplyNum);

        Row row1 = sheet.createRow(1);
        Cell name1 = row1.createCell(0);
        name1.setCellValue("Артикул");

        Cell art1 = row1.createCell(1);
        art1.setCellValue("Кол-во");

        // Меняем размер столбца
        return book;
    }
}

