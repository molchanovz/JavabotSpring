package com.example.javabotspring.Logic.Tests.WildberriesTests;

import com.example.javabotspring.Logic.WildberriesHandler.WildberriesApiToMessage;
import org.json.JSONException;

import java.io.IOException;
import java.security.GeneralSecurityException;

public class TestPriceUpload {
    public static void main(String[] args) throws IOException, GeneralSecurityException, JSONException {

        WildberriesApiToMessage.updatePrices();
    }
}
