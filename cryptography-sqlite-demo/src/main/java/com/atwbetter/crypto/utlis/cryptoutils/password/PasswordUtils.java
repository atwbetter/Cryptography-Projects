package com.atwbetter.crypto.utlis.cryptoutils.password;


import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;
import org.mindrot.jbcrypt.BCrypt;


public class PasswordUtils {


    /**
     * BCrypt
     */
    public static String bcrypt(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }


    public static boolean checkBCrypt(String password, String hash) {
        return BCrypt.checkpw(password, hash);
    }


    /**
     * Argon2
     */
    public static String argon2(String password) {
        Argon2 argon2 = Argon2Factory.create();
        return argon2.hash(3, 65536, 1, password);
    }


}