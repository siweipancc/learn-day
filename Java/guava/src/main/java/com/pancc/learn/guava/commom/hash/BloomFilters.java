package com.pancc.learn.guava.commom.hash;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.RandomUtil;
import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnel;
import com.google.common.hash.PrimitiveSink;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.function.Supplier;
import java.util.stream.Stream;

/**
 * @author Siweipancc
 */
@SuppressWarnings("UnstableApiUsage")
public class BloomFilters {

    public static void main(String[] args) throws IOException {
        BloomFilter<Person> filter;
        Supplier<BloomFilter<Person>> fallback = () -> BloomFilter.create(PersonFunnel.INSTANCE, 2_000);
        Path path = Path.of(FileUtil.getTmpDirPath());
        Path resolved = path.resolve(STR."\{BloomFilters.class.getName()}.BloomFilter");
        Path candidate = Stream.of(resolved)
                .filter(Files::exists)
                .filter(x -> !Files.isDirectory(x))
                .filter(Files::isReadable)
                .filter(Files::isWritable).limit(1)
                .findFirst().orElse(null);
        if (candidate != null) {
            try (InputStream inputStream = Files.newInputStream(candidate, StandardOpenOption.READ)) {
                filter = BloomFilter.readFrom(inputStream, PersonFunnel.INSTANCE);
            } catch (IOException _) {
                filter = fallback.get();
            }
        } else {
            filter = fallback.get();
        }
        int count = 200;
        for (int i = 0; i < count; i++) {
            Person person = new Person(RandomUtil.randomString("abc", 3), RandomUtil.randomString("abc", 2), RandomUtil.randomInt(4));
            boolean mightContain = filter.mightContain(person);
            boolean put = filter.put(person);
            System.out.println(STR."person: \{person}, mightContain: \{mightContain}, put: \{put}");
        }

        try (OutputStream outputStream = Files.newOutputStream(resolved, StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING)) {
            filter.writeTo(outputStream);
            outputStream.flush();
        }
    }


    public record Person(String firstName, String lastName, int age) {
    }


    public enum PersonFunnel implements Funnel<Person> {
        INSTANCE;

        @Override
        public void funnel(Person person, PrimitiveSink into) {
            into.putUnencodedChars(person.firstName()).putUnencodedChars(person.lastName()).putInt(person.age());
        }
    }
}
