package com.pancc.learn.jdks.platform;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;

/**
 * 调用 windows cmd .
 * <p>
 * 见 jdk.internal.util.StaticProperty.
 *
 * @author Siweipancc
 */
public class Processes {

    public static void main(String[] args) throws IOException, InterruptedException {
        Path tmp = Path.of(System.getProperty("java.io.tmpdir"));
        ProcessBuilder builder = new ProcessBuilder();
        //noinspection ResultOfMethodCallIgnored
        builder.redirectErrorStream();
        builder.command("cmd", "/c", "dir");
        builder.directory(tmp.toFile());
        Process process = builder.start();
        printProcess(process);
        process.waitFor();
    }

    public static void printProcess(Process started) throws IOException {
        Charset nativeEncoding = Charsets.platformCharset(StandardCharsets.UTF_8);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(started.getInputStream(), nativeEncoding));
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            System.out.println(line);
        }
        bufferedReader.close();
    }
}
