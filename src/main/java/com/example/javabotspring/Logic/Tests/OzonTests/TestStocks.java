package com.example.javabotspring.Logic.Tests.OzonTests;

import com.example.javabotspring.Logic.OzonHandler.OzonApi;
import com.example.javabotspring.Logic.OzonHandler.OzonApiToMessage;
import com.opencsv.exceptions.CsvValidationException;

import org.json.JSONException;

import java.io.IOException;

public class TestStocks {
    public static void main(String[] args) throws IOException, InterruptedException, CsvValidationException, JSONException {
        OzonApiToMessage ozonTrade = new OzonApiToMessage("259267","451952b2-a29b-4f7e-819e-3fd96f580fbc");
        ozonTrade.getStocks();
    }
}
