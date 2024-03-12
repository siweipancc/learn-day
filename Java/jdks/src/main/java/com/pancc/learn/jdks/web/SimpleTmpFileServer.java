package com.pancc.learn.jdks.web;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.SimpleFileServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.file.Path;

/**
 * @author Siweipancc
 */
public class SimpleTmpFileServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        Path path = Path.of(System.getProperty("java.io.tmpdir"));
        HttpServer server = SimpleFileServer.createFileServer(new InetSocketAddress(9098), path, SimpleFileServer.OutputLevel.VERBOSE);
        server.start();
    }

}
