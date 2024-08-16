package com.example.javabotspring.logic.WildberriesHandler;

import com.example.javabotspring.bot.components.Service;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
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
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class WildberriesApi {

    //Возвращает массив json объектов
    public static JSONArray getOrders_All(String key, String date) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        System.out.println("Отчет за "+date);
        try {

            // создаем объект клиента
            HttpGet request = new HttpGet("https://statistics-api.wildberries.ru/api/v1/supplier/orders");

            URI uri = new URIBuilder(request.getURI())
                    .addParameter("dateFrom", date)
                    .addParameter("flag", "1")
                    .build();
            request.setURI(uri);

            // добавляем заголовки запроса

            request.setHeader("Authorization", key);
            request.addHeader("dateFrom", date);
            request.addHeader("flag", "1");
            //System.out.println(Arrays.stream(request.getAllHeaders()).toList().toString());
            CloseableHttpResponse response = httpClient.execute(request);

            try {

                // получаем статус ответа
                System.out.println(response.getStatusLine().toString());        // HTTP/1.1 200 OK

                HttpEntity entity = response.getEntity();
                String result = EntityUtils.toString(entity);

                //JSONObject object = new JSONObject(result);
                JSONArray arr;
                arr = new JSONArray(result);
                return arr;

            } catch (Exception e) {
                System.out.println("Слишком много запросов");
            } finally {
                // закрываем соединения
                response.close();
            }
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } finally {
            httpClient.close();
        }
        return null;
    }

    public static JSONArray getOrders_FBS(String lastDate, String date, String key) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        String date1="";
        try {
            date1 = lastDate;
            date1 = date1+"T00:00:00";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
            Date dateFrom =  simpleDateFormat.parse(date1);
            Timestamp timestamp = new Timestamp(dateFrom.getTime());

            String date2 = date;
            date2 = date2+"T00:00:00";
            Date dateTo =  simpleDateFormat.parse(date2);
            Timestamp timestamp2 = new Timestamp(dateTo.getTime());
            System.out.println(timestamp.getTime()/1000);
            System.out.println(timestamp2.getTime()/1000);


            // создаем объект клиента
            HttpGet request = new HttpGet("https://suppliers-api.wildberries.ru/api/v3/orders");

            URI uri = new URIBuilder(request.getURI())
                    .addParameter("limit", "1000")
                    .addParameter("next", "0")
                    .addParameter("dateFrom", String.valueOf(timestamp.getTime()/1000))
                    .addParameter("dateTo", String.valueOf(timestamp2.getTime()/1000))
                    .build();
            request.setURI(uri);

            // добавляем заголовки запроса
            request.setHeader("Authorization", key);
            CloseableHttpResponse response = httpClient.execute(request);

            try {
                // получаем статус ответа
                System.out.println(response.getStatusLine().toString());        // HTTP/1.1 200 OK

                HttpEntity entity = response.getEntity();
                String result = EntityUtils.toString(entity);
                JSONObject object = new JSONObject(result);
                JSONArray arr = object.getJSONArray("orders");
                return arr;

            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                // закрываем соединения
                response.close();
            }
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        } finally {
            httpClient.close();
        }
    }

    public static JSONArray getStocks(String key) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        SimpleDateFormat formatterDate = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        //String dateFrom = formatterDate.format(date);
        String dateFrom = "2022-01-01";
        try {

            // создаем объект клиента
            HttpGet request = new HttpGet("https://statistics-api.wildberries.ru/api/v1/supplier/stocks");

            URI uri = new URIBuilder(request.getURI())
                    .addParameter("dateFrom", dateFrom)
                    .build();
            request.setURI(uri);

            // добавляем заголовки запроса
            request.setHeader("Authorization", key);
            request.addHeader("dateFrom", dateFrom);
            //System.out.println(Arrays.stream(request.getAllHeaders()).toList().toString());
            CloseableHttpResponse response = httpClient.execute(request);

            try {

                // получаем статус ответа
                System.out.println(response.getStatusLine().toString());        // HTTP/1.1 200 OK
                HttpEntity entity = response.getEntity();
                String result = EntityUtils.toString(entity);
                //JSONObject object = new JSONObject(result);
                JSONArray arr;
                arr = new JSONArray(result);
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

    public static JSONArray getOrdersInSupply(String supplyNum) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();

        try {

            // создаем объект клиента
            HttpGet request = new HttpGet("https://suppliers-api.wildberries.ru/api/v3/supplies/" + supplyNum + "/orders");

            /*URI uri = new URIBuilder(request.getURI())
                    .addParameter("supplyId", "WB-GI-54097140")
                    .build();
            request.setURI(uri);*/

            // добавляем заголовки запроса
            request.setHeader("Authorization", "");
            //System.out.println(Arrays.stream(request.getAllHeaders()).toList().toString());
            CloseableHttpResponse response = httpClient.execute(request);

            try {

                // получаем статус ответа
                System.out.println(response.getStatusLine().toString());        // HTTP/1.1 200 OK

                HttpEntity entity = response.getEntity();
                String result = EntityUtils.toString(entity);
                JSONObject object = new JSONObject(result);
                JSONArray arr = object.getJSONArray("orders");
                return arr;

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

    public static JSONArray getSales(String key, int deltaHours) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        SimpleDateFormat formatterDate = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        String dateFrom = Service.getDate(deltaHours);
        try {

            // создаем объект клиента
            HttpGet request = new HttpGet("https://statistics-api.wildberries.ru/api/v1/supplier/sales");

            URI uri = new URIBuilder(request.getURI())
                    .addParameter("dateFrom", dateFrom)
                    .addParameter("flag", "1")
                    .build();
            request.setURI(uri);

            // добавляем заголовки запроса
            request.setHeader("Authorization", key);
            request.addHeader("dateFrom", dateFrom);
            request.addHeader("flag", "1");
            //System.out.println(Arrays.stream(request.getAllHeaders()).toList().toString());
            CloseableHttpResponse response = httpClient.execute(request);

            try {

                // получаем статус ответа
                System.out.println(response.getStatusLine().toString());        // HTTP/1.1 200 OK
                HttpEntity entity = response.getEntity();
                String result = EntityUtils.toString(entity);
                //JSONObject object = new JSONObject(result);
                JSONArray arr;
                arr = new JSONArray(result);
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

    public static JSONArray getProducts() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {

            // создаем объект клиента
            HttpPost request = new HttpPost("https://suppliers-api.wildberries.ru/content/v1/cards/cursor/list");

            // добавляем заголовки запроса
            StringEntity params = new StringEntity("{\n" +
                    "          \"sort\": {\n" +
                    "              \"cursor\": {\n" +
                    "                  \"limit\": 1000\n" +
                    "              },\n" +
                    "              \"filter\": {\n" +
                    "                  \"withPhoto\": -1\n" +
                    "              }\n" +
                    "          }\n" +
                    "        }");
            request.setEntity(params);

            // добавляем заголовки запроса
            request.setHeader("Authorization", "");
            //System.out.println(Arrays.stream(request.getAllHeaders()).toList().toString());
            CloseableHttpResponse response = httpClient.execute(request);

            try {

                // получаем статус ответа
                System.out.println(response.getStatusLine().toString());        // HTTP/1.1 200 OK
                HttpEntity entity = response.getEntity();
                String result = EntityUtils.toString(entity);
                //JSONObject object = new JSONObject(result);
                JSONArray arr = new JSONObject(result).getJSONObject("data").getJSONArray("cards");
                return arr;

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

    public static JSONArray getPrice() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {

            // создаем объект клиента
            HttpGet request = new HttpGet("https://suppliers-api.wildberries.ru/public/api/v1/info");


            URI uri = new URIBuilder(request.getURI())
                    .addParameter("quantity", "0")
                    .build();
            request.setURI(uri);


            // добавляем заголовки запроса
            request.setHeader("Authorization", "");
            //System.out.println(Arrays.stream(request.getAllHeaders()).toList().toString());
            CloseableHttpResponse response = httpClient.execute(request);

            try {

                // получаем статус ответа
                System.out.println(response.getStatusLine().toString());        // HTTP/1.1 200 OK
                HttpEntity entity = response.getEntity();
                String result = EntityUtils.toString(entity);
                JSONArray arr = new JSONArray(result);
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

    public static String setPrice(String str) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {

            // создаем объект клиента
            HttpPost request = new HttpPost("https://suppliers-api.wildberries.ru/public/api/v1/prices");

            // добавляем заголовки запроса
            StringEntity params = new StringEntity("[\n" +
                    str +
                    "]");
            request.setEntity(params);

            // добавляем заголовки запроса
            request.setHeader("Authorization", "");
            //System.out.println(Arrays.stream(request.getAllHeaders()).toList().toString());
            CloseableHttpResponse response = httpClient.execute(request);

            try {

                // получаем статус ответа
                System.out.println(response.getStatusLine().toString());        // HTTP/1.1 200 OK
                HttpEntity entity = response.getEntity();
                String result = EntityUtils.toString(entity);

                return result;

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

