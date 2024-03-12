package com.pancc.learn.jdks.concurrent;

import java.util.concurrent.StructuredTaskScope;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

/**
 * @author Siweipancc
 */
public class StructuredTasks {

    public static void main(String[] args) {
        long st = System.currentTimeMillis();
        try (var taskScope = new StructuredTaskScope.ShutdownOnFailure()) {
            Supplier<String> firstName = taskScope.fork(StructuredTasks::genFirstName);
            Supplier<String> lastName = taskScope.fork(StructuredTasks::genLastName);
            taskScope.join();
            System.out.printf("usedTime : %d ms\n", (System.currentTimeMillis() - st));
            Person person = new Person(firstName.get(), lastName.get());
            System.out.println(person);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }


    record Person(String n, String m) {
    }


    static String genFirstName() {
        try {
            TimeUnit.SECONDS.sleep(3);
            return "Steve";
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    static String genLastName() {
        try {
            TimeUnit.SECONDS.sleep(1);
            return "Jk";
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
