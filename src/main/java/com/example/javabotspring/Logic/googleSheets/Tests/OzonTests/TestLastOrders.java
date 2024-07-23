package com.example.javabotspring.Logic.googleSheets.Tests.OzonTests;

import com.example.javabotspring.Bot.Protection;
import com.example.javabotspring.Logic.googleSheets.OzonToSheet;
import com.opencsv.exceptions.CsvValidationException;

import org.json.JSONException;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.ParseException;

public class TestLastOrders {
    public static void main(String[] args) throws CsvValidationException, GeneralSecurityException, IOException, InterruptedException, JSONException, ParseException {
        String clientId = Protection.clientId;
        String key = Protection.ozonKey;
        OzonToSheet ozonTrade = new OzonToSheet(key,key,"");
        ozonTrade.getLastOrders();
    }
}
