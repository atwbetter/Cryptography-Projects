package com.atwbetter.crypto.utlis;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.ByteBuffer;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * AES-256-GCM utility
 */
public class AESUtils {
    private static final String ALGO = "AES/GCM/NoPadding";
    private static final int IV_LENGTH = 12; // 96 bits recommended for GCM
    private static final int TAG_LENGTH = 128; // bits

    private static final SecureRandom RANDOM = new SecureRandom();

    public static String encrypt(byte[] plaintext, byte[] aad, byte[] keyBytes) throws Exception {
        SecretKey key = new SecretKeySpec(keyBytes, "AES");
        byte[] iv = new byte[IV_LENGTH];
        RANDOM.nextBytes(iv);

        Cipher cipher = Cipher.getInstance(ALGO);
        GCMParameterSpec spec = new GCMParameterSpec(TAG_LENGTH, iv);
        cipher.init(Cipher.ENCRYPT_MODE, key, spec);
        if (aad != null) cipher.updateAAD(aad);
        byte[] ciphertext = cipher.doFinal(plaintext);

        ByteBuffer bb = ByteBuffer.allocate(iv.length + ciphertext.length);
        bb.put(iv);
        bb.put(ciphertext);
        return Base64.getEncoder().encodeToString(bb.array());
    }

    public static byte[] decrypt(String b64Input, byte[] aad, byte[]keyBytes) throws Exception {
        byte[] input = Base64.getDecoder().decode(b64Input);
        ByteBuffer bb = ByteBuffer.wrap(input);
        byte[] iv = new byte[IV_LENGTH];
        bb.get(iv);
        byte[] ciphertext = new byte[bb.remaining()];
        bb.get(ciphertext);

        SecretKey key = new SecretKeySpec(keyBytes, "AES");
        Cipher cipher = Cipher.getInstance(ALGO);
        GCMParameterSpec spec = new GCMParameterSpec(TAG_LENGTH, iv);
        cipher.init(Cipher.DECRYPT_MODE, key, spec);
        if (aad != null) cipher.updateAAD(aad);
        return cipher.doFinal(ciphertext);
    }
}
