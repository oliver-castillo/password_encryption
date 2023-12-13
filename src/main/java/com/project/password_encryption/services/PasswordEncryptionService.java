package com.project.password_encryption.services;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.password_encryption.exceptions.custom.BadRequestException;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

@Service
public class PasswordEncryptionService implements IPasswordEncryptionService {

  private final Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
  private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
  private final SCryptPasswordEncoder sCryptPasswordEncoder = new SCryptPasswordEncoder();
  private final Pbkdf2PasswordEncoder pbkdf2PasswordEncoder = new Pbkdf2PasswordEncoder();

  @Override
  public Map<String, String> encodePassword(String algorithm, Map<String, Object> params) {
    checkParams(algorithm, params);
    String password = params.get("password").toString();
    String hashedPassword = switch (algorithm) {
      case "bcrypt" -> encodeWithBcrypt(password);
      case "scrypt" -> encodeWithScrypt(password);
      case "pbkdf2" -> encodeWithPbkdf2(password);
      case "argon2id" -> encodeWithArgon2id(params);
      default -> throw new BadRequestException("Unknown algorithm -> " + algorithm);
    };
    return Map.of("encryptedPassword", hashedPassword, "algorithmUsed", algorithm);
  }

  public String encodeWithBcrypt(String password) {
    return bCryptPasswordEncoder.encode(password);
  }

  public String encodeWithScrypt(String password) {
    return sCryptPasswordEncoder.encode(password);
  }

  public String encodeWithPbkdf2(String password) {
    return pbkdf2PasswordEncoder.encode(password);
  }

  public String encodeWithArgon2id(Map<String, Object> params) {
    char[] passwordCharArray = params.get("password").toString().toCharArray();
    Charset charset = StandardCharsets.UTF_8;
    int iterations = (params.get("iterations") != null) ? (int) params.get("iterations") : 1;
    int memory = (params.get("memory") != null) ? (Integer) params.get("memory") : 1024;
    int parallelism = (params.get("parallelism") != null) ? (Integer) params.get("parallelism") : 1;
    String hashedPassword = argon2.hash(iterations, memory, parallelism, passwordCharArray, charset);
    argon2.wipeArray(passwordCharArray);
    return hashedPassword;
  }

  public void checkParams(String algorithm, Map<String, Object> params) throws BadRequestException {
    String password = (params.get("password") != null) ? params.get("password").toString() : "";
    int iterations = (params.get("iterations") != null) ? (int) params.get("iterations") : 1;
    int memory = (params.get("memory") != null) ? (Integer) params.get("memory") : 1024;
    int parallelism = (params.get("parallelism") != null) ? (Integer) params.get("parallelism") : 1;
    if (password.isEmpty()) {
      throw new BadRequestException("Password is required");
    }
    if (algorithm.equals("argon2id")) {
      if (iterations <= 0 || iterations > 20) {
        throw new BadRequestException("Number of iterations must be between 0 and 20");
      }
      if (memory <= 0 || memory > 100000) {
        throw new BadRequestException("Number of kilobytes of memory must be between 0 and 100000");
      }
      if (parallelism <= 0 || parallelism > 10) {
        throw new BadRequestException("Number of parallelism must be between 0 and 10");
      }
    }
  }

}
