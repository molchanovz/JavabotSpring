package com.example.javabotspring.Logic.googleSheets.Tests.OzonTests;

import com.example.javabotspring.Logic.googleSheets.OzonToSheet;
import com.opencsv.exceptions.CsvValidationException;

import org.json.JSONException;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.text.ParseException;

public class TestLastOrders {
    public static void main(String[] args) throws CsvValidationException, GeneralSecurityException, IOException, InterruptedException, JSONException, ParseException {
        OzonToSheet ozonTrade = new OzonToSheet("259267","451952b2-a29b-4f7e-819e-3fd96f580fbc","");
        ozonTrade.getLastOrders();
    }
}
