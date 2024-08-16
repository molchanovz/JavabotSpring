package com.example.javabotspring.logic.Tests.WildberriesTests;



import com.example.javabotspring.Protection;
import com.example.javabotspring.logic.WildberriesHandler.Feedbacks.Feedback.ReviewToMessage;

import java.io.IOException;

public class TestFeedback {
    public static void main(String[] args) throws IOException {
        String key = Protection.wbKey;
        System.out.println(ReviewToMessage.setAnswer(key));
    }
}
