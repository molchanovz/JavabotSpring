package com.example.javabotspring.logic.googleSheets.Tests.WildberriesTests;

import com.example.javabotspring.logic.googleSheets.WildberriesToSheet;

import java.io.IOException;

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

