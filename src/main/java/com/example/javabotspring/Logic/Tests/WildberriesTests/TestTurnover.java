package com.example.javabotspring.Logic.Tests.WildberriesTests;

import com.example.javabotspring.Logic.WildberriesHandler.WildberriesApiToMessage;
import com.opencsv.exceptions.CsvValidationException;
import org.json.JSONException;

import java.io.IOException;

public class TestTurnover {
    public static void main(String[] args) throws IOException, JSONException, InterruptedException, CsvValidationException {
        WildberriesApiToMessage.getTurnover();
    }
}
