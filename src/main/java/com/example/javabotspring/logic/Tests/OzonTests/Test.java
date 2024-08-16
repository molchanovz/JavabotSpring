package com.example.javabotspring.logic.Tests.OzonTests;


import com.example.javabotspring.bot.components.Service;
import org.json.JSONException;

import java.io.IOException;
import java.text.ParseException;

public class Test {
    public static void main(String[] args) throws IOException, JSONException, InterruptedException, ParseException {
        String lastDate = Service.getDate(-24);
        String date = Service.getDate(0);

        //System.out.println(OzonApiToEntity.getLastOrders(lastDate, date, "\"fbo\""));
        }
}
