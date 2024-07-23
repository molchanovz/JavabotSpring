package com.example.javabotspring.Logic.Tests.WildberriesTests;

import com.example.javabotspring.Bot.components.Service;

public class TestDate {
    public static void main(String[] args) {
        int date = Service.getDayDate(24);
        System.out.print(date);
    }
}
