package com.atwbetter.crypto.utlis;


import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;


public final class KeyUtils {


    private KeyUtils() {
    }


    /**
     * 生成 RSA 密钥
     * <p>
     * 默认:
     * RSA-2048
     */
    public static KeyPair generateRSAKeyPair() throws Exception {


        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");


        generator.initialize(2048);


        return generator.generateKeyPair();

    }


    /**
     * 生成 ECC 密钥
     * <p>
     * Curve:
     * secp256r1
     */
    public static KeyPair generateECCKeyPair() throws Exception {


        KeyPairGenerator generator = KeyPairGenerator.getInstance("EC");


        generator.initialize(256);


        return generator.generateKeyPair();

    }


    /**
     * PrivateKey 转 PEM
     */
    public static String privateKeyToPem(PrivateKey privateKey) {


        String base64 = Base64.getMimeEncoder(64, "\n".getBytes()).encodeToString(privateKey.getEncoded());


        return "-----BEGIN PRIVATE KEY-----\n" + base64 + "\n-----END PRIVATE KEY-----";

    }


    /**
     * PublicKey 转 PEM
     */
    public static String publicKeyToPem(PublicKey publicKey) {


        String base64 = Base64.getMimeEncoder(64, "\n".getBytes()).encodeToString(publicKey.getEncoded());


        return "-----BEGIN PUBLIC KEY-----\n" + base64 + "\n-----END PUBLIC KEY-----";

    }


    /**
     * PEM加载PrivateKey
     */
    public static PrivateKey loadPrivateKey(String pem, String algorithm) throws Exception {


        String content = pem.replace("-----BEGIN PRIVATE KEY-----", "").replace("-----END PRIVATE KEY-----", "").replaceAll("\\s+", "");


        byte[] keyBytes = Base64.getDecoder().decode(content);


        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);


        KeyFactory factory = KeyFactory.getInstance(algorithm);


        return factory.generatePrivate(spec);

    }


    /**
     * PEM加载PublicKey
     */
    public static PublicKey loadPublicKey(String pem, String algorithm) throws Exception {


        String content = pem.replace("-----BEGIN PUBLIC KEY-----", "").replace("-----END PUBLIC KEY-----", "").replaceAll("\\s+", "");


        byte[] keyBytes = Base64.getDecoder().decode(content);


        X509EncodedKeySpec spec = new X509EncodedKeySpec(keyBytes);


        KeyFactory factory = KeyFactory.getInstance(algorithm);


        return factory.generatePublic(spec);

    }


    public static void main(String[] args) throws Exception {


        KeyPair pair = KeyUtils.generateRSAKeyPair();


        String privatePem = KeyUtils.privateKeyToPem(pair.getPrivate());


        String publicPem = KeyUtils.publicKeyToPem(pair.getPublic());


        System.out.println(privatePem);

        System.out.println(publicPem);

    }

}