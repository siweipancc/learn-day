package com.pancc.learn.jdks.web;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author Siweipancc
 */
public class HttpClients {
    public static void main(String[] args) throws IOException, InterruptedException {
        Path tempFile = Files.createTempFile(null, ".png");
        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpResponse<Path> response = client.send(HttpRequest.newBuilder().GET().uri(URI.create("https://openjdk.org/images/openjdk-small.png")).build(), HttpResponse.BodyHandlers.ofFile(tempFile));
            System.out.println(STR."png saved to: \{response.body()}");
        }
    }
}
