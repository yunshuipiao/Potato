package com.swensun.potato;

public class NullPointClass {

    private static String str;

    public static String nullPointTest() {
        return str.substring(0, 1);
    }
}
