package com.pancc.learn.jdks.web;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Siweipancc
 */
public class SimpleWebServer {
    static Logger logger = Logger.getLogger(SimpleWebServer.class.getName());

    public static void main(String[] args) throws IOException, InterruptedException {
        HttpServer httpServer = HttpServer.create(new InetSocketAddress(9099), 0);
        //  打印日期
        httpServer.createContext("/").setHandler(exchange -> {
            logger.log(Level.INFO, "begin react to path {0}", exchange.getHttpContext().getPath());
            byte[] bytes = "Hi? %s".formatted(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))).getBytes(StandardCharsets.UTF_8);
            exchange.sendResponseHeaders(200, bytes.length);
            try (OutputStream stream = exchange.getResponseBody()) {
                stream.write(bytes);
                stream.flush();
            }
            logger.log(Level.INFO, "end react to path {0}", exchange.getHttpContext().getPath());
        });
        // 回显任何内容
        httpServer.createContext("/echo").setHandler(exchange -> {
            byte[] bytes = exchange.getRequestBody().readAllBytes();
            logger.log(Level.INFO, "begin react to path {0}", exchange.getHttpContext().getPath());
            logger.log(Level.INFO, "and got request body: {0}", new String(bytes));
            exchange.sendResponseHeaders(200, bytes.length);
            try (OutputStream stream = exchange.getResponseBody()) {
                stream.write(bytes);
                stream.flush();
            }
            logger.log(Level.INFO, "end react to path {0}", exchange.getHttpContext().getPath());
        });
        httpServer.start();

        makeRequests();

    }

    private static void makeRequests() throws IOException, InterruptedException {
        try (HttpClient client = HttpClient.newHttpClient()) {
            logger.log(Level.INFO, "begin request get: {0}", "/");
            HttpResponse<Stream<String>> response = client.send(HttpRequest.newBuilder().GET().uri(URI.create("http://localhost:9099/")).build(), HttpResponse.BodyHandlers.ofLines());
            logger.log(Level.INFO, "end request get: {0}", "/");
            String string = response.body().collect(Collectors.joining());
            String expected = "Hi? %s".formatted(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
            if (!expected.equals(string)) {
                throw new IllegalStateException();
            }

            logger.log(Level.INFO, "begin request Post: {0}", "/echo");
            HttpResponse<Stream<String>> echoResponse = client.send(HttpRequest.newBuilder().POST(HttpRequest.BodyPublishers.ofString("Greetings!")).uri(URI.create("http://localhost:9099/echo")).build(), HttpResponse.BodyHandlers.ofLines());
            String b2 = echoResponse.body().collect(Collectors.joining());
            logger.log(Level.INFO, "end request Post: {0}, got response body: {1}", new Object[]{"/echo", b2});
            if (!"Greetings!".equals(b2)) {
                throw new IllegalStateException();
            }
        }
    }
}
