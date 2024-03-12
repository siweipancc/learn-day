package com.pancc.learn.jdks.concurrent;

import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * @author Siweipancc
 */
public class ScopedStructuredTask {

    static ScopedValue<String> nameScope = ScopedValue.newInstance();

    public static void main(String[] args) {
        long st = System.currentTimeMillis();
        ScopedValue.runWhere(nameScope, "Steve Jk", () -> {
            try (StructuredTaskScope<Object> taskScope = new StructuredTaskScope.ShutdownOnFailure()) {
                Supplier<String> firstName = taskScope.fork(ScopedStructuredTask::genFirstName);
                Supplier<String> lastName = taskScope.fork(ScopedStructuredTask::genLastName);
                taskScope.join();
                System.out.println(STR."taskScope.isShutdown() = \{taskScope.isShutdown()}");
                System.out.println(STR."firstName.get() = \{firstName.get()}");
                System.out.println(STR."lastName.get() = \{lastName.get()}");
                System.out.printf("usedTime : %d", (System.currentTimeMillis() - st));
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        });


    }

    static String genFirstName() {
        String string = nameScope.get().split(" ")[0];
        try {
            TimeUnit.SECONDS.sleep(string.length());
            return string;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    static String genLastName() {
        String string = nameScope.get().split(" ")[1];
        try {
            TimeUnit.SECONDS.sleep(string.length());
            return string;
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
