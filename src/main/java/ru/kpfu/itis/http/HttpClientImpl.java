package ru.kpfu.itis.http;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class HttpClientImpl implements HttpClient {

    public static void main(String[] args) {
        HttpClient client = new HttpClientImpl();

        // Get - запрос
        Map<String, String> headers = Map.of("Content-Type", "application/json");
        String getResponse = client.get("https://jsonplaceholder.typicode.com/posts/1/comments?postId=1", headers, null);
        System.out.println(getResponse);

        // Post - запрос
        Map<String, String> postHeaders = Map.of(
                "Content-Type", "application/json",
                "Accept", "application/json",
                "Authorization", "Bearer 58762cdab4e248c10d165f6bbe89d18a444dff00267b6cfcec49acf9dceb94b7"
        );
        Map<String, String> postData = Map.of(
                "name", "Sen. Anala Iyer",
                "email", "dsen_anala_iyer123@stroman-leannon.test",
                "gender", "female",
                "status", "active"
        );
        String postResponse = client.post("https://jsonplaceholder.typicode.com/posts", postHeaders, postData);
        System.out.println(postResponse);

        // Put - запрос
        Map<String, String> putHeaders = Map.of(
                "Content-Type", "application/json",
                "Accept", "application/json",
                "Authorization", "Bearer 58762cdab4e248c10d165f6bbe89d18a444dff00267b6cfcec49acf9dceb94b7"
        );
        Map<String, String> putData = Map.of(
                "name", "Updated Name",
                "email", "updated_email@example.com",
                "gender", "female",
                "status", "active"
        );
        String putResponse = client.put("https://jsonplaceholder.typicode.com/posts/1", putHeaders, putData);
        System.out.println(putResponse);

        // Delete - запрос
        Map<String, String> deleteHeaders = Map.of(
                "Content-Type", "application/json",
                "Accept", "application/json",
                "Authorization", "Bearer 58762cdab4e248c10d165f6bbe89d18a444dff00267b6cfcec49acf9dceb94b7"
        );
        String deleteResponse = client.delete("https://jsonplaceholder.typicode.com/posts/1", deleteHeaders, null);
        System.out.println(deleteResponse);
    }

    private static String readResponse(HttpURLConnection connection) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            StringBuilder content = new StringBuilder();
            String input;
            while ((input = reader.readLine()) != null) {
                content.append(input);
            }
            return content.toString();
        }
    }

    @Override
    public String get(String url, Map<String, String> headers, Map<String, String> params) {
        try {
            URL url1 = new URL(url);
            HttpURLConnection connection = (HttpURLConnection) url1.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    connection.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }

            // Ожидание ответа от сервера
            connection.connect();
            String response = readResponse(connection);
            connection.disconnect();
            return response;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String post(String url, Map<String, String> headers, Map<String, String> data) {
        try {
            URL postUrl = new URL(url);
            HttpURLConnection postConnection = (HttpURLConnection) postUrl.openConnection();
            postConnection.setRequestMethod("POST");
            postConnection.setDoOutput(true);

            // Устанавливаем заголовки
            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    postConnection.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }

            // Запись данных в тело запроса
            if (data != null) {
                String jsonInput = mapToJsonFile(data);
                try (OutputStream outputStream = postConnection.getOutputStream()) {
                    byte[] input = jsonInput.getBytes(StandardCharsets.UTF_8);
                    outputStream.write(input, 0, input.length);
                }
            }

            // Ожидание ответа от сервера
            postConnection.connect();
            String response = readResponse(postConnection);
            postConnection.disconnect();
            return response;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String put(String url, Map<String, String> headers, Map<String, String> data) {
        try {
            URL putUrl = new URL(url);
            HttpURLConnection putConnection = (HttpURLConnection) putUrl.openConnection();
            putConnection.setRequestMethod("PUT");
            putConnection.setDoOutput(true);

            // Устанавливаем заголовки
            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    putConnection.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }

            // Запись данных в тело запроса
            if (data != null) {
                String jsonInput = mapToJsonFile(data);
                try (OutputStream outputStream = putConnection.getOutputStream()) {
                    byte[] input = jsonInput.getBytes(StandardCharsets.UTF_8);
                    outputStream.write(input, 0, input.length);
                }
            }

            // Ожидание ответа от сервера
            putConnection.connect();
            String response = readResponse(putConnection);
            putConnection.disconnect();
            return response;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String delete(String url, Map<String, String> headers, Map<String, String> data) {
        try {
            URL deleteUrl = new URL(url);
            HttpURLConnection deleteConnection = (HttpURLConnection) deleteUrl.openConnection();
            deleteConnection.setRequestMethod("DELETE");

            // Устанавливаем заголовки
            if (headers != null) {
                for (Map.Entry<String, String> entry : headers.entrySet()) {
                    deleteConnection.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }

            // Запись данных в тело запроса
            if (data != null) {
                String jsonInput = mapToJsonFile(data);
                try (OutputStream outputStream = deleteConnection.getOutputStream()) {
                    byte[] input = jsonInput.getBytes(StandardCharsets.UTF_8);
                    outputStream.write(input, 0, input.length);
                }
            }

            // Ожидание ответа от сервера
            deleteConnection.connect();
            String response = readResponse(deleteConnection);
            deleteConnection.disconnect();
            return response;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String mapToJsonFile(Map<String, String> map) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
        return objectMapper.writeValueAsString(map);
    }
}
