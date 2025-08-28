package edu.infnet;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

public class JsonPlaceholderClient {
    private final HttpsURLConnection connection;
    StringBuilder response = null;

    JsonPlaceholderClient() throws URISyntaxException, IOException {
        String baseUrl = "https://jsonplaceholder.typicode.com";
        String resource = "/posts";
        String endpoint = baseUrl + resource;
        URI uri = new URI(endpoint);
        URL url = uri.toURL();

        connection = (HttpsURLConnection) url.openConnection();
    }

    public StringBuilder getPosts() throws IOException {
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Accept", "application/json");
        connection.setConnectTimeout(5000);

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpsURLConnection.HTTP_OK) {

            this.response = new StringBuilder();
            InputStreamReader inputStreamReader = new InputStreamReader(connection.getInputStream());
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String responseLine;
            while ((responseLine = bufferedReader.readLine()) != null) {
                this.response.append(responseLine.trim());
            }
        } else {
            System.out.println("Connection failed with response code: " + responseCode);
        }
        return this.response;
    }
}
