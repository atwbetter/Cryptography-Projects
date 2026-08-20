# cryptography-sqlite-demo

A minimal Spring Boot 4.1 demo using SQLite (JdbcTemplate).

How to run:

1. cd cryptography-sqlite-demo
2. mvn -U clean package
3. mvn spring-boot:run

API endpoints:
- GET /users
- GET /users/{id}
- POST /users  {"name":"Alice","email":"alice@example.com"}
- PUT /users/{id}
- DELETE /users/{id}

The SQLite file database.db will be created in the project root when the app runs.


# Crypto utilities for cryptography-sqlite-demo

This package contains utility classes for common cryptographic operations requested by the project:

- AES-256-GCM (AESUtils)
- RSA-2048 signing (RSAUtils)
- ECC / ECDSA (ECCUtils)
- HMAC-SHA256 (HmacUtils)
- Password hashing: Argon2 & bcrypt wrappers (PasswordUtils)
- SHA-256 hashing (HashUtils)
- Chinese algorithms (SM2/SM3/SM4) stubs using BouncyCastle (SMUtils)

Dependencies to add to your build (Maven coordinates):

- BouncyCastle: org.bouncycastle:bcprov-jdk15on:1.70 (for SM algorithms and additional crypto)
- Argon2: de.mkammerer:argon2-jvm:2.11
- BCrypt (if not using spring-security): org.springframework.security:spring-security-crypto:6.1.0

Notes:
- AESUtils uses standard Java Cipher (AES/GCM/NoPadding). Use 32-byte key for AES-256.
- RSAUtils generates 2048-bit RSA keys and signs with SHA256withRSA.
- ECCUtils uses curve secp256r1 (prime256v1) and ECDSA with SHA-256.
- PasswordUtils uses reflection to allow optional dependencies; ensure the libraries are present at runtime.
- SMUtils requires BouncyCastle; implementations are partial and intended as a starting point.

Do you want me to also add the required dependencies to the project's build file (pom.xml or build.gradle)?
