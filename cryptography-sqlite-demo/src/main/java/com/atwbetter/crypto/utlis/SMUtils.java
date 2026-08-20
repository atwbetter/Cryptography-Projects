package com.atwbetter.crypto.utlis;

import java.security.MessageDigest;

/**
 * SM2/SM3/SM4 utilities using BouncyCastle.
 * Add dependency: org.bouncycastle:bcprov-jdk15on
 */
public class SMUtils {
    // Note: This class uses BouncyCastle APIs. Ensure BouncyCastle provider is registered.

    public static String sm3HashHex(byte[] data) throws Exception {
        try {
            MessageDigest md = MessageDigest.getInstance("SM3", "BC");
            byte[] d = md.digest(data);
            StringBuilder sb = new StringBuilder();
            for (byte b : d) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (Exception e) {
            throw new IllegalStateException("SM3 not available. Add BouncyCastle and register provider.", e);
        }
    }

    // SM4 ECB encryption (placeholder) — implement block handling per your needs
    public static byte[] sm4EncryptECB(byte[] key, byte[] data) {
        throw new UnsupportedOperationException("SM4 ECB utility requires full block handling. Implement per your needs.");
    }

    // SM2 signature / verify would go here — using BouncyCastle's SM2 support
}
