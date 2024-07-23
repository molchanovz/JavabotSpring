package com.example.javabotspring.Logic.Tests.OzonTests;


import com.example.javabotspring.Bot.components.Service;
import com.example.javabotspring.Logic.OzonHandler.OzonApiToEntity;
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
