package com.example.javabotspring.Logic.Tests.OzonTests;

import com.example.javabotspring.Bot.Protection;
import com.example.javabotspring.Logic.OzonHandler.OzonApiToMessage;
import com.opencsv.exceptions.CsvValidationException;

import org.json.JSONException;

import java.io.IOException;

public class TestStocks {
    public static void main(String[] args) throws IOException, InterruptedException, CsvValidationException, JSONException {
        String clientId = Protection.clientId;
        String key = Protection.ozonKey;
        OzonApiToMessage ozonTrade = new OzonApiToMessage(clientId,key);
        ozonTrade.getStocks();
    }
}
