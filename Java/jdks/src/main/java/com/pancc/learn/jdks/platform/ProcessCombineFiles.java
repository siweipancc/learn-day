package com.pancc.learn.jdks.platform;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.stream.Stream;

import static com.pancc.learn.jdks.platform.Processes.printProcess;

/**
 * <a href="https://learn.microsoft.com/en-us/windows-server/administration/windows-commands/copy">copy</a>.
 *
 * @author Siweipancc
 */
public class ProcessCombineFiles {
    static StringBuffer sb = new StringBuffer();

    /**
     * 在临时目录创建多个临时文件 *.tmp.
     *
     * @return 临时目录
     * @throws IOException IOException
     */
    static Path createFiles() throws IOException {
        Path tempFile = Files.createTempFile(null, ".txt");
        long words = 22_222;
        for (long l = 0; l < words; l++) {
            sb.append(l).append('_');
            if (l % 10 == 0) {
                sb.append("\n");
            }
        }
        Files.writeString(tempFile, sb, StandardOpenOption.APPEND);
        Path directory = Files.createTempDirectory(null);
        int idx = 1;
        try (SeekableByteChannel channel = Files.newByteChannel(tempFile, StandardOpenOption.READ)) {
            ByteBuffer buffer = ByteBuffer.allocate(2 * 1024);
            while (true) {
                int read = channel.read(buffer);
                buffer.flip();
                if (read <= 0) {
                    break;
                }

                Path path = directory.resolve("%03d.tmp".formatted(idx++));
                try (SeekableByteChannel byteChannel = Files.newByteChannel(path, StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE)) {
                    byteChannel.write(buffer);
                }
                buffer.rewind();
            }
        }
        Files.deleteIfExists(tempFile);
        return directory;
    }

    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void main(String[] args) throws IOException, InterruptedException {
        Path dir = createFiles();
        Path resolved = dir.resolve("out.txt");
        ProcessBuilder builder = new ProcessBuilder().directory(dir.toFile()).command("cmd", "/c", "copy", "/b", "*.tmp", resolved.getFileName().toString());
        Process process = builder.start();
        builder.redirectErrorStream();
        printProcess(process);
        process.waitFor();
        String readString = Files.readString(resolved);
        // never happens
        if (!readString.contentEquals(sb)) {
            throw new IllegalStateException();
        }
        Stream.concat(Files.list(dir), Stream.of(dir)).forEach(x -> {
            try {
                Files.deleteIfExists(x);
            } catch (IOException ignore) {
            }
        });
        Files.deleteIfExists(resolved);
    }

}
