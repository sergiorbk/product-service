package com.sergosoft.productservice.util;

import java.util.Base64;

public class Base64Utils {

    public static String encode(String input) {
        return Base64.getEncoder().encodeToString(input.getBytes());
    }

    public static String decode(String base64) {
        byte[] decodedBytes = Base64.getDecoder().decode(base64);
        return new String(decodedBytes);
    }

    public static String encode(byte[] inputBytes) {
        return Base64.getEncoder().encodeToString(inputBytes);
    }

    public static byte[] decodeToBytes(String base64) {
        return Base64.getDecoder().decode(base64);
    }

    public static String encodeUrlSafe(String input) {
        return Base64.getUrlEncoder().withoutPadding().encodeToString(input.getBytes());
    }

    public static String decodeUrlSafe(String base64) {
        byte[] decodedBytes = Base64.getUrlDecoder().decode(base64);
        return new String(decodedBytes);
    }
}
