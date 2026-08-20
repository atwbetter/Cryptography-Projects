package com.atwbetter.crypto;

import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.util.Base64;

/**
 * ECC utility (ECDSA with secp256r1 / prime256v1)
 */
public class ECCUtils {
    public static KeyPair generateKeyPair() throws Exception {
        KeyPairGenerator kpg = KeyPairGenerator.getInstance("EC");
        ECGenParameterSpec ecSpec = new ECGenParameterSpec("secp256r1");
        kpg.initialize(ecSpec, new SecureRandom());
        return kpg.generateKeyPair();
    }

    public static String sign(PrivateKey priv, byte[] data) throws Exception {
        Signature sig = Signature.getInstance("SHA256withECDSA");
        sig.initSign(priv);
        sig.update(data);
        byte[] s = sig.sign();
        return Base64.getEncoder().encodeToString(s);
    }

    public static boolean verify(PublicKey pub, byte[] data, String b64Sig) throws Exception {
        Signature sig = Signature.getInstance("SHA256withECDSA");
        sig.initVerify(pub);
        sig.update(data);
        byte[] s = Base64.getDecoder().decode(b64Sig);
        return sig.verify(s);
    }
}
