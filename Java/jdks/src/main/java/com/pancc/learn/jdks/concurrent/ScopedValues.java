package com.pancc.learn.jdks.concurrent;

/**
 * @author Siweipancc
 */
public class ScopedValues {
    static ScopedValue<Wrapper> value = ScopedValue.newInstance();
    static ScopedValue<Integer> id = ScopedValue.newInstance();


    static void closeMethod() {
        System.out.printf("id: %d, value: %s\n", id.get(), value.get());
    }

    public static void main(String[] args) {
        ScopedValue.where(id, 2).where(value, new Wrapper(1)).run(ScopedValues::closeMethod);
        ScopedValue.where(id, 3).where(value, new Wrapper(2)).run(ScopedValues::closeMethod);
    }

    record Wrapper(int id) {
    }
}
