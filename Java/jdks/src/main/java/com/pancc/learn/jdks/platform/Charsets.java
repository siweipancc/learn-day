package com.pancc.learn.jdks.platform;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author Siweipancc
 */
public class Charsets {
    /**
     * JDK 17.
     */
    static String name = "native.encoding";

    /**
     * 获取系统默认编码.
     *
     * @param fallback fallback
     * @return 编码
     */
    public static Charset platformCharset(Charset fallback) {
        try {
            return Charset.forName(System.getProperty(name), fallback);
        } catch (IllegalArgumentException ignore) {
            return fallback;
        }
    }

    public static void main(String[] args) {
        System.out.println(STR."platformCharset(StandardCharsets.UTF_16) = \{platformCharset(StandardCharsets.UTF_16)}");

        // 临时更改
        String set = System.setProperty(name, StandardCharsets.UTF_8.name());
        System.out.println(STR."platformCharset(StandardCharsets.UTF_16) = \{platformCharset(StandardCharsets.UTF_16)}");
        // 恢复
        if (set != null) {
            System.setProperty(name, set);
        }
    }
}
