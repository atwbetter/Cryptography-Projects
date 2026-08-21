package com.atwbetter.crypto.utlis.cryptoutils.sm;


import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;


/**
 * SM2国密算法工具类
 * <p>
 * 功能：
 * 1. SM2密钥生成
 * 2. SM2公钥加密
 * 3. SM2私钥解密
 * 4. SM2签名
 * 5. SM2验签
 */
public final class SM2Utils {


    private static final String PROVIDER = "BC";


    private static final String CURVE = "sm2p256v1";


    static {

        if (Security.getProvider(PROVIDER) == null) {

            Security.addProvider(new BouncyCastleProvider());

        }

    }


    private SM2Utils() {
    }


    /**
     * 生成SM2密钥对
     */
    public static KeyPair generateKeyPair() throws Exception {


        KeyPairGenerator generator = KeyPairGenerator.getInstance("EC", PROVIDER);


        generator.initialize(new ECGenParameterSpec(CURVE), new SecureRandom());


        return generator.generateKeyPair();

    }


    /**
     * SM2公钥加密
     */
    public static String encrypt(String plainText, PublicKey publicKey) throws Exception {


        Cipher cipher = Cipher.getInstance("SM2", PROVIDER);


        cipher.init(Cipher.ENCRYPT_MODE, publicKey);


        byte[] result = cipher.doFinal(plainText.getBytes(StandardCharsets.UTF_8));


        return Base64.getEncoder().encodeToString(result);

    }


    /**
     * SM2私钥解密
     */
    public static String decrypt(String cipherText, PrivateKey privateKey) throws Exception {


        Cipher cipher = Cipher.getInstance("SM2", PROVIDER);


        cipher.init(Cipher.DECRYPT_MODE, privateKey);


        byte[] result = cipher.doFinal(Base64.getDecoder().decode(cipherText));


        return new String(result, StandardCharsets.UTF_8);

    }


    /**
     * SM2签名
     */
    public static String sign(String data, PrivateKey privateKey) throws Exception {


        Signature signature = Signature.getInstance("SM3withSM2", PROVIDER);


        signature.initSign(privateKey);


        signature.update(data.getBytes(StandardCharsets.UTF_8));


        byte[] sign = signature.sign();


        return Base64.getEncoder().encodeToString(sign);

    }


    /**
     * SM2验签
     */
    public static boolean verify(String data, String sign, PublicKey publicKey) throws Exception {


        Signature signature = Signature.getInstance("SM3withSM2", PROVIDER);


        signature.initVerify(publicKey);


        signature.update(data.getBytes(StandardCharsets.UTF_8));


        return signature.verify(Base64.getDecoder().decode(sign));

    }


    /**
     * PrivateKey转Base64
     */
    public static String privateKeyToBase64(PrivateKey key) {

        return Base64.getEncoder().encodeToString(key.getEncoded());

    }


    /**
     * PublicKey转Base64
     */
    public static String publicKeyToBase64(PublicKey key) {

        return Base64.getEncoder().encodeToString(key.getEncoded());

    }


    /**
     * Base64加载PrivateKey
     */
    public static PrivateKey loadPrivateKey(String base64) throws Exception {


        byte[] bytes = Base64.getDecoder().decode(base64);


        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(bytes);


        KeyFactory factory = KeyFactory.getInstance("EC", PROVIDER);


        return factory.generatePrivate(spec);

    }


    /**
     * Base64加载PublicKey
     */
    public static PublicKey loadPublicKey(String base64) throws Exception {


        byte[] bytes = Base64.getDecoder().decode(base64);


        X509EncodedKeySpec spec = new X509EncodedKeySpec(bytes);


        KeyFactory factory = KeyFactory.getInstance("EC", PROVIDER);


        return factory.generatePublic(spec);

    }


    public static void main(String[] args) throws Exception {


        KeyPair pair = SM2Utils.generateKeyPair();


        String privateKey = SM2Utils.privateKeyToBase64(pair.getPrivate());


        String publicKey = SM2Utils.publicKeyToBase64(pair.getPublic());


        System.out.println("private=" + privateKey);


        System.out.println("public=" + publicKey);

    }

}