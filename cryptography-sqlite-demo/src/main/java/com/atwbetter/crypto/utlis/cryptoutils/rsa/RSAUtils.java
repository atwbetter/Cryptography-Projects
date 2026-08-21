package com.atwbetter.crypto.utlis.cryptoutils.rsa;


import javax.crypto.Cipher;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;


public class RSAUtils {


    public static KeyPair generateKey() throws Exception {
        KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
        generator.initialize(2048);
        return generator.generateKeyPair();

    }


    public static String encrypt(String data, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        return Base64.getEncoder().encodeToString(cipher.doFinal(data.getBytes()));

    }


    public static String decrypt(String data, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA-256AndMGF1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        return new String(cipher.doFinal(Base64.getDecoder().decode(data)));

    }


    public static void main(String[] args) {
        try {
            KeyPair keyPair = RSAUtils.generateKey();
            System.out.println("keyPair = " + keyPair.getPrivate());
            System.out.println("keyPair = " + keyPair.getPublic());
            String data = "hello world";
            String encryptedData = RSAUtils.encrypt(data, keyPair.getPublic());
            String decryptedData = RSAUtils.decrypt(encryptedData, keyPair.getPrivate());
            System.out.println("Original data: " + data);
            System.out.println("Encrypted data: " + encryptedData);
            System.out.println("Decrypted data: " + decryptedData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}