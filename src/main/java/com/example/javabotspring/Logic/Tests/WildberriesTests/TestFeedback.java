package com.example.javabotspring.Logic.Tests.WildberriesTests;



import com.example.javabotspring.Logic.WildberriesHandler.Feedbacks.Feedback.ReviewToMessage;

import java.io.IOException;

public class TestFeedback {
    public static void main(String[] args) throws IOException {
        String key = "eyJhbGciOiJFUzI1NiIsImtpZCI6IjIwMjMxMDI1djEiLCJ0eXAiOiJKV1QifQ." +
                "eyJlbnQiOjEsImV4cCI6MTcxODE0MTE0NCwiaWQiOiJmYWQwNTFjMC1mMTc1LTRjMzkt" +
                "OWQ4Yy0zNzc2NDgzMGY5OGEiLCJpaWQiOjQ2MTk4NjUsIm9pZCI6Mjc1NjYsInMiOjQ0" +
                "Niwic2FuZGJveCI6ZmFsc2UsInNpZCI6IjYzYTk2NDNlLWM3NTYtNTQ0Yy1iZTkzLTRh" +
                "M2ExMzI2Njg5MyIsInVpZCI6NDYxOTg2NX0.ncQDNGh1vg-10tfwfDP7CoDsEa-XDfkII" +
                "qaGNSgRPW32rpl92UDWX0dgN_SEM1fuZkD6Ce3ZszJ0sQWYv2OzsA";
        System.out.println(ReviewToMessage.setAnswer(key));
    }
}
