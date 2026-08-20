package com.atwbetter.crypto.utlis;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.security.MessageDigest;
import java.util.Base64;

/**
 * HMAC-SHA256 utility
 */
public class HmacUtils {
    private static final String HMAC_ALGO = "HmacSHA256";

    public static String hmacSha256Base64(byte[] key, byte[] data) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(key, HMAC_ALGO);
        Mac mac = Mac.getInstance(HMAC_ALGO);
        mac.init(keySpec);
        byte[] macBytes = mac.doFinal(data);
        return Base64.getEncoder().encodeToString(macBytes);
    }

    public static boolean verifyHmacBase64(byte[] key, byte[] data, String b64Mac) throws Exception {
        String computed = hmacSha256Base64(key, data);
        return MessageDigest.isEqual(computed.getBytes(), b64Mac.getBytes());
    }
}
