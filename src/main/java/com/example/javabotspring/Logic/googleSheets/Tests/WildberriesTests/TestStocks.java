package com.example.javabotspring.Logic.googleSheets.Tests.WildberriesTests;

import com.example.javabotspring.Bot.components.Service;
import com.example.javabotspring.Logic.Entities.Stock;
import com.example.javabotspring.Logic.WildberriesHandler.WildberriesApi;
import com.example.javabotspring.Logic.WildberriesHandler.WildberriesApiToEntity;
import com.example.javabotspring.Logic.googleSheets.WildberriesToSheet;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import static com.example.javabotspring.Logic.googleSheets.GoogleService.write;

public class TestStocks {
    public static void main(String[] args) throws IOException {
        String key = "eyJhbGciOiJFUzI1NiIsImtpZCI6IjIwMjMxMDI1" +
                "djEiLCJ0eXAiOiJKV1QifQ.eyJlbnQiOjEsImV4cCI6MTcxODE0MTE0NCwiaWQiOiJmYWQwNTFjMC1m" +
                "MTc1LTRjMzktOWQ4Yy0zNzc2NDgzMGY5OGEiLCJpaWQiOjQ2MTk4NjUsIm9pZCI6Mjc1NjYsInMiOjQ" +
                "0Niwic2FuZGJveCI6ZmFsc2UsInNpZCI6IjYzYTk2NDNlLWM3NTYtNTQ0Yy1iZTkzLTRhM2ExMzI2Nj" +
                "g5MyIsInVpZCI6NDYxOTg2NX0.ncQDNGh1vg-10tfwfDP7CoDsEa-XDfkIIqaGNSgRPW32rpl92UDWX" +
                "0dgN_SEM1fuZkD6Ce3ZszJ0sQWYv2OzsA";


        WildberriesToSheet trade = new WildberriesToSheet(key,"");
        trade.getStocks();
    }
}

