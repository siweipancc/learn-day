package com.pancc.learn.jdks.switchs;

import java.util.Random;

/**
 * @author Siweipancc
 */
public class SwitchPatterns {


    enum Color {RED, GREEN, BLUE}


    static String formatterPatternSwitch(Object obj) {
        return switch (obj) {
            case Integer i when i > 20 -> String.format("int %d (gt 20)", i); // 收窄
            case Integer i when i < -1 -> String.format("int %d (lt -1)", i); // 收窄
            case Integer i -> String.format("int %d", i);
            case Long l -> String.format("long %d", l);
            case Double d -> String.format("double %f", d);
            case String s -> String.format("String %s", s);
            case Color c -> String.format("Color %s", c);
            case null -> "Null";
            default -> obj.toString();
        };
    }


    public static void main(String[] args) {
        Object[] data = {12, 12L, 12.0, "12", Color.RED, 27, -2, new Random(), null};
        for (Object datum : data) {
            System.out.println(formatterPatternSwitch(datum));
        }
        System.out.println("=================================");

        String result = formatterPatternSwitch(34);
        System.out.println(STR."result = \{result}");
    }


}
