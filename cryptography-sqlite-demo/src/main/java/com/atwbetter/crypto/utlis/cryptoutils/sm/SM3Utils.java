package com.atwbetter.crypto.utlis.cryptoutils.sm;


import org.bouncycastle.crypto.digests.SM3Digest;
import org.bouncycastle.crypto.macs.HMac;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.Security;
import java.util.Base64;


/**
 * SM3国密Hash工具类
 * <p>
 * 功能:
 * <p>
 * 1. 字符串SM3
 * 2. byte数组SM3
 * 3. 文件SM3
 * 4. Base64输出
 * 5. HMAC-SM3
 */
public final class SM3Utils {


    static {

        if (Security.getProvider("BC") == null) {

            Security.addProvider(new BouncyCastleProvider());

        }

    }


    private SM3Utils() {
    }


    /**
     * String -> SM3 Hex
     */
    public static String hash(String data) {

        byte[] result = hash(data.getBytes(StandardCharsets.UTF_8));


        return bytesToHex(result);

    }


    /**
     * byte[] -> SM3
     */
    public static byte[] hash(byte[] data) {


        SM3Digest digest = new SM3Digest();


        digest.update(data, 0, data.length);


        byte[] result = new byte[digest.getDigestSize()];


        digest.doFinal(result, 0);


        return result;

    }


    /**
     * 输出Base64
     */
    public static String hashBase64(String data) {

        byte[] result = hash(data.getBytes(StandardCharsets.UTF_8));


        return Base64.getEncoder().encodeToString(result);

    }


    /**
     * 文件SM3摘要
     */
    public static String fileHash(File file) throws Exception {


        SM3Digest digest = new SM3Digest();


        try (InputStream input = new FileInputStream(file)) {


            byte[] buffer = new byte[8192];


            int length;


            while ((length = input.read(buffer)) > 0) {

                digest.update(buffer, 0, length);

            }


        }


        byte[] result = new byte[digest.getDigestSize()];


        digest.doFinal(result, 0);


        return bytesToHex(result);

    }


    /**
     * HMAC-SM3
     */
    public static String hmac(String data, String secret) {


        HMac mac = new HMac(new SM3Digest());


        mac.init(new KeyParameter(secret.getBytes(StandardCharsets.UTF_8)));


        byte[] bytes = data.getBytes(StandardCharsets.UTF_8);


        mac.update(bytes, 0, bytes.length);


        byte[] result = new byte[mac.getMacSize()];


        mac.doFinal(result, 0);


        return bytesToHex(result);

    }


    /**
     * byte数组转Hex
     */
    private static String bytesToHex(byte[] bytes) {


        StringBuilder builder = new StringBuilder();


        for (byte b : bytes) {


            String hex = Integer.toHexString(b & 0xff);


            if (hex.length() == 1) {

                builder.append('0');

            }


            builder.append(hex);

        }


        return builder.toString();

    }


    public static void main(String[] args) {


        String result = SM3Utils.hash("hello sm3");


        System.out.println(result);


    }


}