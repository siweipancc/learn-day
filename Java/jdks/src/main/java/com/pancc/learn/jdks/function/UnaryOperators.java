package com.pancc.learn.jdks.function;

import java.util.HashMap;
import java.util.Map;
import java.util.function.UnaryOperator;

/**
 * @author Siweipancc
 */
public class UnaryOperators {


    static UnaryOperator<Integer> get;

    static {
        Map<Integer, Integer> cache = new HashMap<>();
        int count = 12;

        for (int i = 0; i < count; i++) {
            cache.put(i, (i * i));
        }
        get = cache::get;
    }

    public static void main(String[] args) {
        int[] nums = {1, 3, 5, 77};
        for (int nu : nums) {
            System.out.printf("get.apply(%s) = %s\n", nu, get.apply(nu));
        }

    }

}
