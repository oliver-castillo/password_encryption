package com.project.password_encryption.services;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

@Service
public class PasswordEncoderService {

  Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
  BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
  SCryptPasswordEncoder sCryptPasswordEncoder = new SCryptPasswordEncoder();
  Pbkdf2PasswordEncoder pbkdf2PasswordEncoder = new Pbkdf2PasswordEncoder();

  public String encodeWithBcrypt(String password) {
    return bCryptPasswordEncoder.encode(password);
  }

  public String encodeWithArgon2(String password) {
    char[] passwordCharArray = password.toCharArray();
    Charset charset = StandardCharsets.UTF_8;
    String hashedPassword = argon2.hash(1, 1024, 1, passwordCharArray, charset);
    argon2.wipeArray(passwordCharArray);
    return hashedPassword;
  }

  public String encodeWithScrypt(String password) {
    return sCryptPasswordEncoder.encode(password);
  }

  public String encodeWithPbkdf2(String password) {
    return pbkdf2PasswordEncoder.encode(password);
  }

  public String encodeWithSHA2(String password) {
    try {
      MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
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
}
