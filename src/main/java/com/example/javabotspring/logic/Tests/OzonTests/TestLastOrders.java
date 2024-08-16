package com.example.javabotspring.logic.Tests.OzonTests;

import com.example.javabotspring.Protection;
import com.example.javabotspring.logic.OzonHandler.OzonApiToMessage;
import com.opencsv.exceptions.CsvValidationException;


import java.io.IOException;

public class TestLastOrders {
    public static void main(String[] args) throws IOException, CsvValidationException, InterruptedException {
        String clientId = Protection.clientId;
        String key = Protection.ozonKey;
        OzonApiToMessage ozonTrade = new OzonApiToMessage(clientId,key);
        ozonTrade.getLastOrders();
    }

}
