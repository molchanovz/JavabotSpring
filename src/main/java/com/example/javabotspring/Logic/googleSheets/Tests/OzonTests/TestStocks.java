package com.example.javabotspring.Logic.googleSheets.Tests.OzonTests;

import com.example.javabotspring.Bot.Protection;
import com.example.javabotspring.Logic.googleSheets.OzonToSheet;

public class TestStocks {
    public static void main(String[] args) {
        String clientId = Protection.clientId;
        String key = Protection.ozonKey;
        OzonToSheet ozonTrade = new OzonToSheet(clientId,key,"");
        ozonTrade.getStocks();
    }
}
