package com.example.javabotspring.Logic.WildberriesHandler.Feedbacks.Feedback;



import com.example.javabotspring.Logic.Entities.Review;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReviewToMessage {
    public static String setAnswer(String key) {
        List<Review> arrayList = ReviewToEntity.getFeedbacks_arhive(key);
        if(!arrayList.isEmpty()){
            int i=0;
            ArrayList<String> himiya = readArticle(System.getProperty("user.dir") + "/Feedbacks/Himiya.txt");
            ArrayList<String> shalognya = readArticle(System.getProperty("user.dir") + "/Feedbacks/Shalognya.txt");
            ArrayList<String> filters = readArticle(System.getProperty("user.dir") + "/Feedbacks/Filters.txt");
            ArrayList<String> osushitels = readArticle(System.getProperty("user.dir") + "/Feedbacks/Osushitel.txt");
            for (Review review : arrayList) {
                if (review.getProductValuation().equals("5")||review.getProductValuation().equals("4")) {
                    if (review.getText().isEmpty()) {
                        i++;
                        try {
                            if (himiya.contains(review.getProductArticle())) {
                                ReviewApi.setAnswer(key,review.getId(), Review.getHimiya());
                            } else if (shalognya.contains(review.getProductArticle())) {
                                ReviewApi.setAnswer(key,review.getId(), Review.getShalognya());

                            } else if (filters.contains(review.getProductArticle())) {
                                ReviewApi.setAnswer(key,review.getId(), Review.getFilters());

                            } else if (osushitels.contains(review.getProductArticle())) {
                                ReviewApi.setAnswer(key,review.getId(), Review.getOsushitel());
                            } else
                                ReviewApi.setAnswer(key,review.getId(), "Добрый день, благодарим вас за оставленный отзыв. " +
                                        "С Уважением, команда Vommy ☀️");

                        } catch (IOException e) {
                            new RuntimeException(e);
                        }
                    }
                }
            }
            return "Обработано отзывов: "+i;
        } else return "Новых отзывов нет";

    }

    private static ArrayList<String> readArticle(String fileName) {
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line = reader.readLine();
            while (line != null) {
                arrayList.add(line);
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return arrayList;
    }
}
