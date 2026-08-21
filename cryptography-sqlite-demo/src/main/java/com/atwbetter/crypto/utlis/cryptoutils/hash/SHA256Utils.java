package com.atwbetter.crypto.utlis.cryptoutils.hash;


import java.security.MessageDigest;
import java.util.HexFormat;


public class SHA256Utils {


    public static String hash(String data) throws Exception {


        MessageDigest digest = MessageDigest.getInstance("SHA-256");


        byte[] result = digest.digest(data.getBytes());


        return HexFormat.of().formatHex(result);

    }

}