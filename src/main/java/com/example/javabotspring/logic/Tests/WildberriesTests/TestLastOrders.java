package com.example.javabotspring.logic.Tests.WildberriesTests;

import com.example.javabotspring.Protection;
import com.example.javabotspring.logic.WildberriesHandler.WildberriesApiToMessage;
import org.json.JSONException;

import java.io.IOException;

public class TestLastOrders {
    public static void main(String[] args) throws IOException, JSONException, InterruptedException {
        String key = Protection.wbKey;
        WildberriesApiToMessage.getLastDayOrders(key);
    }

}
