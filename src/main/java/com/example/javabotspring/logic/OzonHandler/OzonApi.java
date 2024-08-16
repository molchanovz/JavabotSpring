package com.example.javabotspring.logic.OzonHandler;

import com.opencsv.CSVReader;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class OzonApi {
    public static String[] getCSV(String csv) throws IOException {
        CloseableHttpClient httpClient;
        HttpEntity httpEntity;
        HttpGet httpGet;
        CloseableHttpResponse httpResponse;
        CSVReader csvReader;
        String csvFile;
        //Создаем подключение
        httpClient = HttpClients.createDefault();

        //Формируем запрос
        httpGet = new HttpGet(csv);

        //Отправляем запрос на сервер и получаем ответ
        httpResponse = httpClient.execute(httpGet);

        //Достаём наш файл из запроса
        httpEntity = httpResponse.getEntity();
        csvFile = EntityUtils.toString(httpEntity);

        //Выводим все строки файла
        String[] lines = csvFile.split("\n");
        return lines;

    }

    public static String getReportInfo(String clientId, String key, String code) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {

            // создаем объект клиента
            HttpPost request = new HttpPost("https://api-seller.ozon.ru/v1/report/info");

            // добавляем заголовки запроса
            StringEntity params = new StringEntity("{\n" +
                    "  \"code\": \"" + code + "\"\n" +
                    "}");
            request.setEntity(params);

            request.addHeader("Client-Id", clientId);
            request.addHeader("Api-Key", key);


            CloseableHttpResponse response = httpClient.execute(request);

            try {
                // получаем статус ответа
                System.out.println(response.getStatusLine().toString());        // HTTP/1.1 200 OK

                HttpEntity entity = response.getEntity();
                String result = EntityUtils.toString(entity);

                JSONObject object = new JSONObject(result);
                JSONObject obj = object.getJSONObject("result");
                String file = obj.getString("file");
                return file;

            } catch (JSONException e) {
                System.out.println("Что-то с Json");
                new RuntimeException(e);
            } finally {
                // закрываем соединения
                response.close();
            }
        } finally {
            httpClient.close();
        }
        return null;
    }

    public static String getLastOrders_code(String id, String key, String lastDate, String date, String delivery_schema) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {

            // создаем объект клиента
            HttpPost request = new HttpPost("https://api-seller.ozon.ru/v1/report/postings/create");
            // добавляем заголовки запроса
            StringEntity params = new StringEntity("{\n" +
                    "    \"filter\": {\n" +
                    "        \"processed_at_from\": \"" + lastDate + "T00:00:00Z\",\n" +
                    "        \"processed_at_to\": \"" + date + "T00:00:00Z\",\n" +
                    "        \"delivery_schema\": [\n" +
                    "            " + delivery_schema + "\n" +
                    "        ],\n" +
                    "        \"sku\": [],\n" +
                    "        \"cancel_reason_id\": [],\n" +
                    "        \"offer_id\": \"\",\n" +
                    "        \"status_alias\": [],\n" +
                    "        \"statuses\": [],\n" +
                    "        \"title\": \"\"\n" +
                    "    },\n" +
                    "    \"language\": \"DEFAULT\"\n" +
                    "}");
            request.setEntity(params);

            request.addHeader("Client-Id", id);
            request.addHeader("Api-Key", key);


            CloseableHttpResponse response = httpClient.execute(request);

            try {
                // получаем статус ответа
                System.out.println(response.getStatusLine().toString());        // HTTP/1.1 200 OK

                HttpEntity entity = response.getEntity();
                String result = EntityUtils.toString(entity);

                JSONObject object = new JSONObject(result);
                JSONObject obj = object.getJSONObject("result");
                String code = obj.getString("code");
                return code;

            } catch (JSONException e) {
                System.out.println(e.getMessage());
                new RuntimeException(e);
            } finally {
                // закрываем соединения
                response.close();
            }
        } finally {
            httpClient.close();
        }
        return null;
    }

    public static JSONArray getStocks() throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {

            // создаем объект клиента
            HttpPost request = new HttpPost("https://api-seller.ozon.ru/v2/analytics/stock_on_warehouses");
            // добавляем заголовки запроса
            StringEntity params = new StringEntity("{\n" +
                    "\"limit\": 1000,\n" +
                    "\"offset\": 0,\n" +
                    "\"warehouse_type\": \"ALL\"\n" +
                    "}");
            request.setEntity(params);

            request.addHeader("Client-Id", "259267");
            request.addHeader("Api-Key", "451952b2-a29b-4f7e-819e-3fd96f580fbc");


            CloseableHttpResponse response = httpClient.execute(request);

            try {
                // получаем статус ответа
                System.out.println(response.getStatusLine().toString());        // HTTP/1.1 200 OK

                HttpEntity entity = response.getEntity();
                String result = EntityUtils.toString(entity);

                JSONObject object = new JSONObject(result);
                JSONObject obj = object.getJSONObject("result");
                JSONArray arr = obj.getJSONArray("rows");
                return arr;

            } catch (JSONException e) {
                System.out.println("Что-то с Json");
                new RuntimeException(e);
            } finally {
                // закрываем соединения
                response.close();
            }
        } finally {
            httpClient.close();
        }
        return null;
    }

    public static JSONArray getStock_v2(String clientId, String key) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {

            // создаем объект клиента
            HttpPost request = new HttpPost("https://api-seller.ozon.ru/v2/analytics/stock_on_warehouses");
            // добавляем заголовки запроса
            StringEntity params = new StringEntity("{\n" +
                    "  \"limit\": 1000,\n" +
                    "  \"offset\": 0,\n" +
                    "  \"warehouse_type\": \"ALL\"\n" +
                    "}");
            request.setEntity(params);

            request.addHeader("Client-Id", clientId);
            request.addHeader("Api-Key", key);


            CloseableHttpResponse response = httpClient.execute(request);

            try {
                // получаем статус ответа
                System.out.println(response.getStatusLine().toString());        // HTTP/1.1 200 OK

                HttpEntity entity = response.getEntity();
                String result = EntityUtils.toString(entity);

                JSONObject object = new JSONObject(result);
                JSONObject obj = object.getJSONObject("result");
                JSONArray arr = obj.getJSONArray("rows");
                return arr;

            } catch (JSONException e) {
                System.out.println("Что-то с Json");
                new RuntimeException(e);
            } finally {
                // закрываем соединения
                response.close();
            }
        } finally {
            httpClient.close();
        }
        return null;
    }

    public static JSONArray getReturns(String clientId, String key) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {

            // создаем объект клиента
            HttpPost request = new HttpPost("https://api-seller.ozon.ru/v3/returns/company/fbo");
            // добавляем заголовки запроса
            StringEntity params = new StringEntity("{\n" +
                    "  \"filter\": {\n" +
                    "    \"status\": [\n" +
                    "      \"ReturnedToOzon\"\n" +
                    "    ]\n" +
                    "  },\n" +
                    "  \"limit\": 1000\n" +
                    "}");
            request.setEntity(params);

            request.addHeader("Client-Id", clientId);
            request.addHeader("Api-Key", key);


            CloseableHttpResponse response = httpClient.execute(request);

            try {
                // получаем статус ответа
                System.out.println(response.getStatusLine().toString());        // HTTP/1.1 200 OK

                HttpEntity entity = response.getEntity();
                String result = EntityUtils.toString(entity);

                JSONObject object = new JSONObject(result);
                JSONArray arr = object.getJSONArray("returns");
                return arr;

            } catch (JSONException e) {
                System.out.println("Что-то с Json");
                new RuntimeException(e);
            } finally {
                // закрываем соединения
                response.close();
            }
        } finally {
            httpClient.close();
        }
        return null;
    }

    public static JSONObject getProduct(String clientId, String key, String sku) throws IOException {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {

            // создаем объект клиента
            HttpPost request = new HttpPost("https://api-seller.ozon.ru/v2/product/info");
            // добавляем заголовки запроса
            StringEntity params = new StringEntity("{\n" +
                    "  \"sku\": " + sku + "\n" +
                    "}");
            request.setEntity(params);

            request.addHeader("Client-Id", clientId);
            request.addHeader("Api-Key", key);


            CloseableHttpResponse response = httpClient.execute(request);

            try {
                // получаем статус ответа
                System.out.println(response.getStatusLine().toString());        // HTTP/1.1 200 OK

                HttpEntity entity = response.getEntity();
                String result = EntityUtils.toString(entity);

                JSONObject object = new JSONObject(result);
                JSONObject object1 = object.getJSONObject("result");
                return object1;

            } catch (JSONException e) {
                System.out.println("Что-то с Json");
                new RuntimeException(e);
            } finally {
                // закрываем соединения
                response.close();
            }
        } finally {
            httpClient.close();
        }
        return null;
    }

    public static JSONObject getOrders_FBO(String clientId, String key) {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        // создаем объект клиента
        HttpPost request = new HttpPost("https://api-seller.ozon.ru/v2/posting/fbo/list");
        // добавляем заголовки запроса
        StringEntity params = null;
        try {
            params = new StringEntity("{\n" +
                    "  \"dir\": \"ASC\",\n" +
                    "  \"filter\": {\n" +
                    "    \"since\": \"2024-08-13T00:00:00.000Z\",\n" +
                    "    \"status\": \"\",\n" +
                    "    \"to\": \"2024-08-16T00:00:00.000Z\"\n" +
                    "  },\n" +
                    "  \"limit\": 1000,\n" +
                    "  \"offset\": 0,\n" +
                    "  \"translit\": true,\n" +
                    "  \"with\": {\n" +
                    "    \"analytics_data\": true,\n" +
                    "    \"financial_data\": true\n" +
                    "  }\n" +
                    "}");
        } catch (UnsupportedEncodingException e) {
            new RuntimeException(e);
        }
        request.setEntity(params);

        request.addHeader("Client-Id", clientId);
        request.addHeader("Api-Key", key);


        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(request);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


        // получаем статус ответа
        System.out.println(response.getStatusLine().toString());        // HTTP/1.1 200 OK

        HttpEntity entity = response.getEntity();
        String result = null;
        try {
            result = EntityUtils.toString(entity);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        JSONObject object = null;
        try {
            object = new JSONObject(result);

        } catch (JSONException e) {
            new RuntimeException(e);
        }

        return object;

    }
}


