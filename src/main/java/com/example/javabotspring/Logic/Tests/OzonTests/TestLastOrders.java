package com.example.javabotspring.Logic.Tests.OzonTests;

import com.example.javabotspring.Logic.OzonHandler.OzonApiToMessage;
import com.opencsv.exceptions.CsvValidationException;


import java.io.IOException;

public class TestLastOrders {
    public static void main(String[] args) throws IOException, CsvValidationException, InterruptedException {
        OzonApiToMessage ozonTrade = new OzonApiToMessage("259267","451952b2-a29b-4f7e-819e-3fd96f580fbc");
        ozonTrade.getLastOrders();
    }

}
