package com.project.password_encryption.services;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

@Service
public class PasswordEncryptionService implements IPasswordEncryptionService {

  Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
  BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
  SCryptPasswordEncoder sCryptPasswordEncoder = new SCryptPasswordEncoder();
  Pbkdf2PasswordEncoder pbkdf2PasswordEncoder = new Pbkdf2PasswordEncoder();

  @Override
  public String encodeWithBcrypt(String password) {
    return bCryptPasswordEncoder.encode(password);
  }

  @Override
  public String encodeWithScrypt(String password) {
    return sCryptPasswordEncoder.encode(password);
  }

  @Override
  public String encodeWithPbkdf2(String password) {
    return pbkdf2PasswordEncoder.encode(password);
  }

  @Override
  public String encodeWithAlgorithm(String password, String algorithm) {
    try {
      MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
      byte[] hashedBytes = messageDigest.digest(password.getBytes(StandardCharsets.UTF_8));
      StringBuilder hexStringBuilder = new StringBuilder();
      for (byte b : hashedBytes) {
        hexStringBuilder.append(String.format("%02x", b));
      }
      return hexStringBuilder.toString();
    } catch (NoSuchAlgorithmException e) {
      e.printStackTrace();
      return null;
    }
  }

  @Override
  public String encodeWithArgon2id(String password, Map<String, Integer> params) {
    char[] passwordCharArray = password.toCharArray();
    Charset charset = StandardCharsets.UTF_8;
    String hashedPassword = argon2.hash(
        params.get("iterations"),
        params.get("memory"),
        params.get("parallelism"),
        passwordCharArray,
        charset);
    argon2.wipeArray(passwordCharArray);
    System.out.println(params.get("memory"));
    return hashedPassword;
  }
}
