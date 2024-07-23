package com.example.javabotspring.Logic.Tests.OzonTests;

import com.example.javabotspring.Bot.Protection;
import com.example.javabotspring.Logic.OzonHandler.OzonApiToMessage;
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
