package com.example.javabotspring.logic.googleSheets.Tests.OzonTests;

import com.example.javabotspring.Protection;
import com.example.javabotspring.logic.googleSheets.OzonToSheet;
import com.opencsv.exceptions.CsvValidationException;

import org.json.JSONException;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.ParseException;

public class TestLastOrders {
    public static void main(String[] args) throws CsvValidationException, GeneralSecurityException, IOException, InterruptedException, JSONException, ParseException {
        String clientId = Protection.clientId;
        String key = Protection.ozonKey;
        OzonToSheet ozonTrade = new OzonToSheet(clientId,key,"");
        ozonTrade.getLastOrders();
    }
}
