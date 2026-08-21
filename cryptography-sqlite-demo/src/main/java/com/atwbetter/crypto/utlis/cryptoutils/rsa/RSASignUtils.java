package com.atwbetter.crypto.utlis.cryptoutils.rsa;


import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.util.Base64;


public class RSASignUtils {


    public static String sign(String data, PrivateKey privateKey) throws Exception {
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey);
        signature.update(data.getBytes());
        return Base64.getEncoder().encodeToString(signature.sign());

    }


    public static boolean verify(String data, String sign, PublicKey publicKey) throws Exception {
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initVerify(publicKey);
        signature.update(data.getBytes());
        return signature.verify(Base64.getDecoder().decode(sign));

    }

}