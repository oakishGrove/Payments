package com.payments.aop;

public class StringUtils {

    public static boolean nullOrEmpty(String str) {
        return str == null || str.trim().isEmpty();
    }
}
