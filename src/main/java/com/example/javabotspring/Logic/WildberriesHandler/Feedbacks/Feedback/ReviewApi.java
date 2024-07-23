package com.example.javabotspring.Logic.WildberriesHandler.Feedbacks.Feedback;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class ReviewApi {

    public static JSONArray getFeedbacks_arhive(String key) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {

            // создаем объект клиента
            HttpGet request = new HttpGet("https://feedbacks-api.wb.ru/api/v1/feedbacks/archive");


            URI uri = new URIBuilder(request.getURI())
                    .addParameter("take", "500")
                    .addParameter("skip", "0")
                    .addParameter("order", "dateDesc")
                    .build();
            request.setURI(uri);


            // добавляем заголовки запроса
            request.setHeader("Authorization", key);
            //System.out.println(Arrays.stream(request.getAllHeaders()).toList().toString());
            CloseableHttpResponse response = httpClient.execute(request);

            try {

                // получаем статус ответа
                System.out.println(response.getStatusLine().toString());        // HTTP/1.1 200 OK
                HttpEntity entity = response.getEntity();
                String result = EntityUtils.toString(entity);
                JSONObject object = new JSONObject(result);
                JSONArray arr = object.getJSONObject("data").getJSONArray("feedbacks");
                return arr;

            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                // закрываем соединения
                response.close();
            }
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } finally {
            httpClient.close();
        }
    }
    public static void setAnswer(String key,String id,String text) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {

            // создаем объект клиента
            HttpPatch request = new HttpPatch("https://feedbacks-api.wb.ru/api/v1/feedbacks");

            StringEntity params = new StringEntity("{\n" +
                    "  \"id\": \""+id+"\",\n" +
                    "  \"text\": \""+text+"\"\n" +
                    "}","UTF-8");
            request.setEntity(params);

            // добавляем заголовки запроса
            request.setHeader("Authorization", key);
            //System.out.println(Arrays.stream(request.getAllHeaders()).toList().toString());
            CloseableHttpResponse response = httpClient.execute(request);

            try {
                // получаем статус ответа
                System.out.println(response.getStatusLine().toString());        // HTTP/1.1 200 OK
                HttpEntity entity = response.getEntity();
                System.out.println(entity);

            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                // закрываем соединения
                response.close();
            }
        } finally {
            httpClient.close();
        }
    }

}
