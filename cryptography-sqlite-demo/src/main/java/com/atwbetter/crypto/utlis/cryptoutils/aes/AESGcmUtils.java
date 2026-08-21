package com.atwbetter.crypto.utlis.cryptoutils.aes;


import javax.crypto.Cipher;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;


public class AESGcmUtils {

    private static final String AES = "AES";
    private static final int TAG_LENGTH = 128;


    /**
     * 加密
     */
    public static String encrypt(String plainText, byte[] key) throws Exception {
        byte[] iv = new byte[12];
        SecureRandom random = new SecureRandom();
        random.nextBytes(iv);
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        SecretKeySpec keySpec = new SecretKeySpec(key, AES);
        GCMParameterSpec spec = new GCMParameterSpec(TAG_LENGTH, iv);
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, spec);
        byte[] encrypted = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));
        byte[] result = new byte[iv.length + encrypted.length];
        System.arraycopy(iv, 0, result, 0, iv.length);
        System.arraycopy(encrypted, 0, result, iv.length, encrypted.length);
        return Base64.getEncoder().encodeToString(result);
    }


    /**
     * 解密
     */
    public static String decrypt(String cipherText, byte[] key) throws Exception {
        byte[] data = Base64.getDecoder().decode(cipherText);
        byte[] iv = new byte[12];
        System.arraycopy(data, 0, iv, 0, 12);
        byte[] encrypted = new byte[data.length - 12];
        System.arraycopy(data, 12, encrypted, 0, encrypted.length);
        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(key, AES), new GCMParameterSpec(TAG_LENGTH, iv));
        return new String(cipher.doFinal(encrypted), StandardCharsets.UTF_8);
    }


/**
 * 主方法，演示加密和解密过程
 * @param args 命令行参数，本程序未使用
 */
    public static void main(String[] args) {
        try {
            // 明文数据
            String plainText = "hello.";
            // 加密密钥，16字节长度  AES-128 (16字节);AES-192 (24字节);AES-256 (32字节)
            byte[] key = "1234567890123456".getBytes();
            // 使用加密方法对明文进行加密
            String cipherText = encrypt(plainText, key);
            // 输出加密后的密文
            System.out.println("cipherText: " + cipherText);
            // 使用解密方法对密文进行解密
            String decryptedText = decrypt(cipherText, key);
            // 输出解密后的明文
            System.out.println("decryptedText: " + decryptedText);
        } catch (Exception e) {
            // 捕获并打印异常信息
            e.printStackTrace();
        }
    }


}