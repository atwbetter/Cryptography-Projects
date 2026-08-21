package com.atwbetter.crypto.utlis.cryptoutils.hmac;


import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;


public class HmacSHA256Utils {


    public static String sign(String data, String secret) throws Exception {


        Mac mac = Mac.getInstance("HmacSHA256");


        mac.init(new SecretKeySpec(secret.getBytes(), "HmacSHA256"));


        return Base64.getEncoder().encodeToString(mac.doFinal(data.getBytes()));

    }

}