package com.example.ShopNet.configurations;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ApacheHttpClientExample {

    private final String productName;

    public ApacheHttpClientExample(String productName) {
        this.productName = productName;
    }

    public void executeRequest() {
        CloseableHttpClient httpClient = HttpClients.createDefault();
        try {
            // Создаем HTTP GET запрос к вашему локальному серверу Apache
            HttpGet request = new HttpGet("http://localhost/unity/startArchive/" + productName);

            // Выполняем запрос
            HttpResponse response = httpClient.execute(request);

            // Получаем ответ
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent()))) {
                    String line;
                    StringBuilder responseContent = new StringBuilder();
                    while ((line = reader.readLine()) != null) {
                        responseContent.append(line);
                    }
                    System.out.println("Response content:\n" + responseContent.toString());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                // Закрываем HttpClient после завершения
                httpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

