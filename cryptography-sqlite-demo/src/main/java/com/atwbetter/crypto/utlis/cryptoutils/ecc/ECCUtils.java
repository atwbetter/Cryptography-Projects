package com.atwbetter.crypto.utlis.cryptoutils.ecc;


import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.Signature;


public class ECCUtils {


    public static KeyPair generate() throws Exception {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("EC");
        generator.initialize(256);
        return generator.generateKeyPair();
    }


    public static byte[] sign(byte[] data, PrivateKey key) throws Exception {
        Signature signature = Signature.getInstance("SHA256withECDSA");
        signature.initSign(key);
        signature.update(data);
        return signature.sign();

    }


    public static void main(String[] args) {
        try {
            KeyPair keyPair = generate();
            System.out.println("私钥："+keyPair.getPrivate());
            System.out.println("公钥："+keyPair.getPublic());

            String data = "helloWorld";
            byte[] sign = sign(data.getBytes(), keyPair.getPrivate());
            System.out.println("加密后内容："+sign);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}