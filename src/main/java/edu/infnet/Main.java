package edu.infnet;

import java.io.IOException;
import java.net.URISyntaxException;

public class Main {

    public static void main(String[] args) {
        try {
            JsonPlaceholderClient client = new JsonPlaceholderClient();
            StringBuilder posts = client.getPosts();
            System.out.println(posts);

        } catch (IOException | URISyntaxException e) {
            System.out.println(e.getMessage());
        }

    }
}