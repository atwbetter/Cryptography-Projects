package com.atwbetter.crypto;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;

/**
 * SHA-256 hashing utility
 */
public class HashUtils {
    public static byte[] sha256(byte[] data) throws Exception {
        MessageDigest md = MessageDigest.getInstance("SHA-256");
        return md.digest(data);
    }

    public static String sha256Hex(byte[] data) throws Exception {
        byte[] d = sha256(data);
        StringBuilder sb = new StringBuilder();
        for (byte b : d) sb.append(String.format("%02x", b));
        return sb.toString();
    }

    public static String sha256Base64(byte[] data) throws Exception {
        return Base64.getEncoder().encodeToString(sha256(data));
    }

    public static String sha256Hex(String s) throws Exception {
        return sha256Hex(s.getBytes(StandardCharsets.UTF_8));
    }
}
