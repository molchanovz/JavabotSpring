package com.example.javabotspring.Logic.Tests.OzonTests;


import com.example.javabotspring.Logic.OzonHandler.OzonApiToMessage;
import org.json.JSONException;

import java.io.IOException;
import java.text.ParseException;

public class TestReturns {
    public static void main(String[] args) throws JSONException, IOException, ParseException, InterruptedException {
        OzonApiToMessage ozonTrade = new OzonApiToMessage("259267","451952b2-a29b-4f7e-819e-3fd96f580fbc");
        System.out.println(ozonTrade.getReturns(-24,0));
    }
}
