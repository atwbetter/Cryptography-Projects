package com.atwbetter.crypto.utlis.cryptoutils.sm;


import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Base64;


/**
 * SM4国密对称加密工具
 * <p>
 * 支持:
 * <p>
 * SM4/ECB/PKCS7Padding
 * <p>
 * SM4/CBC/PKCS7Padding
 * <p>
 * SM4/GCM/NoPadding
 */
public final class SM4Utils {


    private static final String PROVIDER = "BC";


    private static final String ALGORITHM = "SM4";


    static {

        if (Security.getProvider(PROVIDER) == null) {

            Security.addProvider(new BouncyCastleProvider());

        }

    }


    private SM4Utils() {
    }


    /**
     * ECB 加密
     */
    public static String encryptECB(String data, byte[] key) throws Exception {


        validateKey(key);


        Cipher cipher = Cipher.getInstance("SM4/ECB/PKCS7Padding", PROVIDER);


        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, ALGORITHM));


        byte[] result = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));


        return Base64.getEncoder().encodeToString(result);

    }


    /**
     * ECB 解密
     */
    public static String decryptECB(String cipherText, byte[] key) throws Exception {


        validateKey(key);


        Cipher cipher = Cipher.getInstance("SM4/ECB/PKCS7Padding", PROVIDER);


        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, ALGORITHM));


        byte[] result = cipher.doFinal(Base64.getDecoder().decode(cipherText));


        return new String(result, StandardCharsets.UTF_8);

    }


    /**
     * CBC 加密
     * <p>
     * 返回:
     * <p>
     * IV + CipherText
     */
    public static String encryptCBC(String data, byte[] key) throws Exception {


        validateKey(key);


        byte[] iv = randomIV();


        Cipher cipher = Cipher.getInstance("SM4/CBC/PKCS7Padding", PROVIDER);


        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, ALGORITHM), new IvParameterSpec(iv));


        byte[] encrypted = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));


        byte[] result = new byte[iv.length + encrypted.length];


        System.arraycopy(iv, 0, result, 0, iv.length);


        System.arraycopy(encrypted, 0, result, iv.length, encrypted.length);


        return Base64.getEncoder().encodeToString(result);

    }


    /**
     * CBC 解密
     */
    public static String decryptCBC(String cipherText, byte[] key) throws Exception {


        validateKey(key);


        byte[] data = Base64.getDecoder().decode(cipherText);


        byte[] iv = new byte[16];


        System.arraycopy(data, 0, iv, 0, 16);


        byte[] encrypted = new byte[data.length - 16];


        System.arraycopy(data, 16, encrypted, 0, encrypted.length);


        Cipher cipher = Cipher.getInstance("SM4/CBC/PKCS7Padding", PROVIDER);


        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, ALGORITHM), new IvParameterSpec(iv));


        return new String(cipher.doFinal(encrypted), StandardCharsets.UTF_8);

    }


    /**
     * SM4-GCM加密
     * <p>
     * 推荐模式
     */
    public static String encryptGCM(String data, byte[] key) throws Exception {


        validateKey(key);


        byte[] iv = randomIV();


        Cipher cipher = Cipher.getInstance("SM4/GCM/NoPadding", PROVIDER);


        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(key, ALGORITHM), new GCMParameterSpec(128, iv));


        byte[] encrypted = cipher.doFinal(data.getBytes(StandardCharsets.UTF_8));


        byte[] result = new byte[iv.length + encrypted.length];


        System.arraycopy(iv, 0, result, 0, iv.length);


        System.arraycopy(encrypted, 0, result, iv.length, encrypted.length);


        return Base64.getEncoder().encodeToString(result);

    }


    /**
     * SM4-GCM解密
     */
    public static String decryptGCM(String cipherText, byte[] key) throws Exception {


        validateKey(key);


        byte[] data = Base64.getDecoder().decode(cipherText);


        byte[] iv = new byte[12];


        System.arraycopy(data, 0, iv, 0, 12);


        byte[] encrypted = new byte[data.length - 12];


        System.arraycopy(data, 12, encrypted, 0, encrypted.length);


        Cipher cipher = Cipher.getInstance("SM4/GCM/NoPadding", PROVIDER);


        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, ALGORITHM), new GCMParameterSpec(128, iv));


        return new String(cipher.doFinal(encrypted), StandardCharsets.UTF_8);

    }


    /**
     * 生成SM4 Key
     * <p>
     * 16字节
     */
    public static byte[] generateKey() {


        byte[] key = new byte[16];


        new SecureRandom().nextBytes(key);


        return key;

    }


    /**
     * 生成IV
     */
    private static byte[] randomIV() {


        byte[] iv = new byte[16];


        new SecureRandom().nextBytes(iv);


        return iv;

    }


    /**
     * 校验Key长度
     */
    private static void validateKey(byte[] key) {


        if (key == null || key.length != 16) {

            throw new IllegalArgumentException("SM4 key必须为16字节");

        }

    }


}