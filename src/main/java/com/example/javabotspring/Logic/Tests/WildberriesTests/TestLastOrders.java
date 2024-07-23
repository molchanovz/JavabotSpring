package com.example.javabotspring.Logic.Tests.WildberriesTests;

import com.example.javabotspring.Logic.WildberriesHandler.WildberriesApiToMessage;
import org.json.JSONException;

import java.io.IOException;

public class TestLastOrders {
    public static void main(String[] args) throws IOException, JSONException, InterruptedException {
        String key = "eyJhbGciOiJFUzI1NiIsImtpZCI6IjIwMjQwNTA2djEiLCJ0eXAiOiJKV1QifQ" +
                ".eyJlbnQiOjEsImV4cCI6MTczNDAzNDI5NSwiaWQiOiI4NTFjNmU0Yy1hMTI2LTRiOTg" +
                "tYWM2ZC0yNWU0YmNjNGU4YTUiLCJpaWQiOjQ2MTk4NjUsIm9pZCI6Mjc1NjYsInMiOjQw" +
                "OTQsInNpZCI6IjYzYTk2NDNlLWM3NTYtNTQ0Yy1iZTkzLTRhM2ExMzI2Njg5MyIsInQiO" +
                "mZhbHNlLCJ1aWQiOjQ2MTk4NjV9.Kg9wjfJiN19xBXDr90IdEqC3Vtpvrdxboyzxr4JS9" +
                "KkMl-goanoW_cEWYXo21zrH3ZBN0Q4Pdsm6CaHacyjhsg";
        WildberriesApiToMessage.getLastDayOrders(key);
    }

}
