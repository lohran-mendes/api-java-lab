package edu.infnet;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class JsonPlaceholderClient {
    String baseUrl = "https://jsonplaceholder.typicode.com";

    private HttpsURLConnection gerarHttpConnection(String resource, String method) throws URISyntaxException, IOException {
        String endpoint = baseUrl + resource;
        URI uri = new URI(endpoint);
        URL url = uri.toURL();

        HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
        connection.setRequestMethod(method);
        connection.setRequestProperty("Accept", "application/json");
        connection.setConnectTimeout(5000);
        return connection;
    }

    public StringBuilder getPosts() throws IOException, URISyntaxException {
        StringBuilder response = null;
        HttpsURLConnection connection = gerarHttpConnection("/posts", "GET");

        int responseCode = connection.getResponseCode();

        if (responseCode == HttpsURLConnection.HTTP_OK) {
            response = new StringBuilder();
            InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String responseLine;

            while ((responseLine = bufferedReader.readLine()) != null) {
                response.append(responseLine.trim());
            }
        } else {
            System.out.println("Connection failed with response code: " + responseCode);
        }
        connection.disconnect();
        return response;
    }

    public StringBuilder getPost(int id) throws IOException, URISyntaxException {
        StringBuilder response = null;
        HttpsURLConnection connection = gerarHttpConnection("/posts/" + id, "GET");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpsURLConnection.HTTP_OK) {

            response = new StringBuilder();
            InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String responseLine;
            while ((responseLine = bufferedReader.readLine()) != null) {
                response.append(responseLine.trim());
            }
        } else {
            System.out.println("Connection failed with response code: " + responseCode);
        }
        connection.disconnect();
        return response;
    }

    public StringBuilder deletePost(int id) throws IOException, URISyntaxException {
        StringBuilder response = null;
        HttpsURLConnection connection = gerarHttpConnection("/posts/" + id, "DELETE");

        int responseCode = connection.getResponseCode();

        if (responseCode == HttpsURLConnection.HTTP_OK) {
            response = new StringBuilder();
            InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String responseLine;
            while ((responseLine = bufferedReader.readLine()) != null) {
                response.append(responseLine.trim());
            }
        } else if (responseCode == HttpsURLConnection.HTTP_NOT_FOUND) {
            throw new RuntimeException("Postagem com o id: " + id + " não encontrado.");
        } else {
            System.out.println("Connection failed with response code: " + responseCode);
        }
        connection.disconnect();
        return response;
    }
}
