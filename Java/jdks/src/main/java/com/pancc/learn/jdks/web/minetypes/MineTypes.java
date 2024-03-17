package com.pancc.learn.jdks.web.minetypes;

import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.tika.Tika;

import java.io.IOException;
import java.io.InputStream;
import java.net.FileNameMap;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

/**
 * @author Siweipancc
 */
@Slf4j
public class MineTypes {

    static FileNameMap fileNameMap = URLConnection.getFileNameMap();
    static Tika tika = new Tika();

    public static void main(String[] args) throws URISyntaxException, IOException {
        String pattern = "%-40s\t%-40s\t%-40s\t%-40s\t%-40s\n";
        URL resource = MineTypes.class.getResource("");
        assert resource != null;
        Path path = Path.of(resource.toURI());
        System.out.printf(pattern, "[filename]", "[Jdk]", "[Tika]", "[JdkStream]", "[TikaStream]");
        System.out.println("+".repeat(200));
        @Cleanup Stream<Path> list = Files.list(path);
        list.forEach(c -> {
            String filename = c.getFileName().toString();
            System.out.printf(pattern, filename,
                    nullWithWarn(usingJdk(filename)),
                    nullWithWarn(usingTika(filename)),
                    nullWithWarn(usingJdkStream(filename)),
                    nullWithWarn(usingTikaStream(filename)));
            System.out.println("-".repeat(200));
        });
    }

    static String nullWithWarn(Object o) {
        if (o == null) {
            return "❌null";
        }
        return STR."✅\{o}";
    }


    private static String usingJdk(String fileName) {
        return fileNameMap.getContentTypeFor(fileName);
    }

    @SneakyThrows
    private static String usingJdkStream(String fileName) {
        @Cleanup InputStream inputStream = resolvePath(fileName);
        return URLConnection.guessContentTypeFromStream(inputStream);
    }

    private static String usingTika(String fileName) {
        return tika.detect(fileName);
    }

    @SneakyThrows
    private static String usingTikaStream(String fileName) {
        @Cleanup InputStream inputStream = resolvePath(fileName);
        return tika.detect(inputStream);
    }


    public static InputStream resolvePath(String fileName) {
        return MineTypes.class.getResourceAsStream(fileName);

    }

}
