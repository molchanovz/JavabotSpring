package com.example.javabotspring.Logic.Tests.WildberriesTests;



import com.example.javabotspring.Bot.Protection;
import com.example.javabotspring.Logic.WildberriesHandler.Feedbacks.Feedback.ReviewToMessage;

import java.io.IOException;

public class TestFeedback {
    public static void main(String[] args) throws IOException {
        String key = Protection.wbKey;
        System.out.println(ReviewToMessage.setAnswer(key));
    }
}
