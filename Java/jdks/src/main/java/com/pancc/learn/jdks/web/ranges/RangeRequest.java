package com.pancc.learn.jdks.web.ranges;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.net.HttpHeaders;
import com.pancc.learn.jdks.platform.Processes;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.StructuredTaskScope;

/**
 * @author Siweipancc
 */
public class RangeRequest {
    static String url = "https://bkimg.cdn.bcebos.com/pic/242dd42a2834349b033bb3129abe02ce36d3d5391055";
    static HttpClient httpClient = HttpClient.newHttpClient();

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void main(String[] args) throws IOException, InterruptedException {
        URI uri = URI.create(url);
        HttpResponse<Void> response = httpClient.send(HttpRequest.newBuilder().HEAD().uri(uri).build(), HttpResponse.BodyHandlers.discarding());
        long contentLength = response.headers().firstValueAsLong(HttpHeaders.CONTENT_LENGTH).orElse(-1);
        Preconditions.checkState(contentLength > -1);
        Preconditions.checkState("bytes".equals(response.headers().firstValue(HttpHeaders.ACCEPT_RANGES).orElse(null)));
        long size = 4 * 1024;

        Range[] ranges = createRanges(size, contentLength);
        int minLen = String.valueOf(ranges.length).length() + 1;
        Path tempDirectory = Files.createTempDirectory(null);

        try (StructuredTaskScope.ShutdownOnFailure taskScope = new StructuredTaskScope.ShutdownOnFailure()) {
            for (int i = 0; i < ranges.length; i++) {
                final Range range = ranges[i];
                final String fileName = Strings.padStart(STR."\{i + 1}", minLen, '0').concat(".tmp");
                taskScope.fork(() -> {
                    HttpRequest httpRequest = HttpRequest.newBuilder().GET().uri(uri).header(HttpHeaders.RANGE, STR."bytes=\{range.startInclude()}-\{range.endInclude()}").build();
                    return httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofFile(tempDirectory.resolve(fileName))).body();
                });
            }
            taskScope.join();
        }

        ProcessBuilder builder = new ProcessBuilder();
        builder.directory(tempDirectory.toFile());
        Path resolved = tempDirectory.resolve("qq.png");
        // win cmd
        // builder.command("cmd.exe", "/c", "copy", "/b", "*.tmp", "qq.png");
        // win/linux PowerShell 6.0^
        builder.command("pwsh", "-Command", "Get-Item *.tmp | Get-Content -AsByteStream | Set-Content qq.png -AsByteStream");
        // linux
        // builder.command("bash", "cat *.tmp >> qq.png");
        builder.redirectErrorStream();
        Process process = builder.start();
        Processes.printProcess(process);
        process.waitFor();
        // win
        new ProcessBuilder().command("explorer.exe", "/select,", resolved.toString()).start().waitFor();
        // open in viewer?
        if (Desktop.isDesktopSupported() && !GraphicsEnvironment.isHeadless()) {
            Desktop desktop = Desktop.getDesktop();
            if (desktop.isSupported(Desktop.Action.OPEN)) {
                desktop.open(resolved.toFile());
            }
        }
    }


    public static Range[] createRanges(long blockSize, long contentLength) {
        long divided = contentLength / blockSize;
        long mod = contentLength % blockSize;
        Range[] ranges = new Range[(int) (mod != 0 ? divided + 1 : divided)];
        for (int l = 0; l < divided; l++) {
            long base = l * blockSize;
            long up = base + blockSize - 1;
            Range range = new Range(base, up);
            ranges[l] = range;
        }
        if (mod != 0) {
            Range last = ranges[ranges.length - 2];
            ranges[ranges.length - 1] = new Range(last.endInclude() + 1, contentLength - 1);
        }
        return ranges;
    }
}
