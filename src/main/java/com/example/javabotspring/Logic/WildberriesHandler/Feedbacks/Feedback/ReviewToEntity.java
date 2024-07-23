package com.example.javabotspring.Logic.WildberriesHandler.Feedbacks.Feedback;

import com.example.javabotspring.Logic.Entities.Review;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ReviewToEntity {
    public static List<Review> getFeedbacks_arhive(String key) {
        List<Review> arrayList = new ArrayList<>();
        JSONArray array;

        // Берем все отзывы
        try {
            array = ReviewApi.getFeedbacks_arhive(key);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Ищем не просмотренные и добавляем в массив
        for (int i = 0; i < array.length(); i++) {
            Review review;
            JSONObject ob = new JSONObject();
            try {
                ob = array.getJSONObject(i);
                review = new Review(ob.getString("id"),ob.getString("userName"),ob.getString("text"),ob.getString("productValuation"),ob.getString("answer"),ob.getJSONObject("productDetails").getString("productName"), ob.getJSONObject("productDetails").getString("nmId"));
                if (review.getAnswer().equals("null")){
                    arrayList.add(review);
                }
            } catch (JSONException e) {
                new RuntimeException(e);
            }
        }
        return arrayList;
    }

}
