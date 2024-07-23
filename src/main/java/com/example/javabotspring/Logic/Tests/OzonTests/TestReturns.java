package com.example.javabotspring.Logic.Tests.OzonTests;


import com.example.javabotspring.Bot.Protection;
import com.example.javabotspring.Logic.OzonHandler.OzonApiToMessage;
import org.json.JSONException;

import java.io.IOException;
import java.text.ParseException;

public class TestReturns {
    public static void main(String[] args) throws JSONException, IOException, ParseException, InterruptedException {
        String clientId = Protection.clientId;
        String key = Protection.ozonKey;
        OzonApiToMessage ozonTrade = new OzonApiToMessage(clientId,key);
        System.out.println(ozonTrade.getReturns(-24,0));
    }
}
