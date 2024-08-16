package com.example.javabotspring.logic.googleSheets.Tests.OzonTests;

import com.example.javabotspring.Protection;
import com.example.javabotspring.logic.googleSheets.OzonToSheet;

public class TestStocks {
    public static void main(String[] args) {
        String clientId = Protection.clientId;
        String key = Protection.ozonKey;
        OzonToSheet ozonTrade = new OzonToSheet(clientId,key,"");
        ozonTrade.getStocks();
    }
}
