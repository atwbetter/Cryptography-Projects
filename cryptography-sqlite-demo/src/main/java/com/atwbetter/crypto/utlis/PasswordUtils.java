package com.atwbetter.crypto.utlis;

/**
 * Password utilities: bcrypt and Argon2 wrappers.
 *
 * Note: This class delegates to external libraries. Add the following dependencies to your build:
 *  - BCrypt: org.springframework.security:spring-security-crypto OR org.mindrot:jbcrypt
 *  - Argon2: de.mkammerer:argon2-jvm
 */
public class PasswordUtils {
    // Bcrypt using Spring Security's BCryptPasswordEncoder if available
    public static String hashBcrypt(String password) {
        try {
            // Try Spring Security
            Class<?> cls = Class.forName("org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder");
            Object encoder = cls.getConstructor().newInstance();
            return (String) cls.getMethod("encode", CharSequence.class).invoke(encoder, password);
        } catch (Exception e) {
            throw new IllegalStateException("BCrypt encoder not available. Add spring-security-crypto or jbcrypt to the classpath.", e);
        }
    }

    public static boolean verifyBcrypt(String password, String hash) {
        try {
            Class<?> cls = Class.forName("org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder");
            Object encoder = cls.getConstructor().newInstance();
            return (Boolean) cls.getMethod("matches", CharSequence.class, String.class).invoke(encoder, password, hash);
        } catch (Exception e) {
            throw new IllegalStateException("BCrypt encoder not available. Add spring-security-crypto or jbcrypt to the classpath.", e);
        }
    }

    // Argon2 using de.mkammerer.argon2
    public static String hashArgon2(String password) {
        try {
            Class<?> factory = Class.forName("de.mkammerer.argon2.Argon2Factory");
            Object argon2 = factory.getMethod("create").invoke(null);
            // argon2.hash(iterations, memory, parallelism, password)
            return (String) argon2.getClass().getMethod("hash", int.class, int.class, int.class, char[].class)
                    .invoke(argon2, 3, 65536, 1, password.toCharArray());
        } catch (Exception e) {
            throw new IllegalStateException("Argon2 library not available. Add de.mkammerer:argon2-jvm to the classpath.", e);
        }
    }

    public static boolean verifyArgon2(String password, String hash) {
        try {
            Class<?> factory = Class.forName("de.mkammerer.argon2.Argon2Factory");
            Object argon2 = factory.getMethod("create").invoke(null);
            return (Boolean) argon2.getClass().getMethod("verify", String.class, char[].class).invoke(argon2, hash, password.toCharArray());
        } catch (Exception e) {
            throw new IllegalStateException("Argon2 library not available. Add de.mkammerer:argon2-jvm to the classpath.", e);
        }
    }
}
