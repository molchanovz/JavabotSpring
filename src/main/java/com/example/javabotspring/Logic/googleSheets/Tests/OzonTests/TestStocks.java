package com.example.javabotspring.Logic.googleSheets.Tests.OzonTests;

import com.example.javabotspring.Logic.googleSheets.OzonToSheet;

public class TestStocks {
    public static void main(String[] args) {
        OzonToSheet ozonTrade = new OzonToSheet("259267","451952b2-a29b-4f7e-819e-3fd96f580fbc","");
        ozonTrade.getStocks();
    }
}
