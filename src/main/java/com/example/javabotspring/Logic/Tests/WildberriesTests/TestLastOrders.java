package com.example.javabotspring.Logic.Tests.WildberriesTests;

import com.example.javabotspring.Bot.Protection;
import com.example.javabotspring.Logic.WildberriesHandler.WildberriesApiToMessage;
import org.json.JSONException;

import java.io.IOException;

public class TestLastOrders {
    public static void main(String[] args) throws IOException, JSONException, InterruptedException {
        String key = Protection.wbKey;
        WildberriesApiToMessage.getLastDayOrders(key);
    }

}
