package com.project.password_encryption.services;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.logging.Logger;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.password_encryption.exceptions.custom.BadRequestException;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

@Service
public class PasswordEncryptionService implements IPasswordEncryptionService {

  Argon2 argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2id);
  BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
  SCryptPasswordEncoder sCryptPasswordEncoder = new SCryptPasswordEncoder();
  Pbkdf2PasswordEncoder pbkdf2PasswordEncoder = new Pbkdf2PasswordEncoder();

  Logger logger = Logger.getLogger(PasswordEncryptionService.class.getName());

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
  public String encodeWithArgon2id(Map<String, Object> params) {
    checkParams(params);
    char[] passwordCharArray = params.get("password").toString().toCharArray();
    Charset charset = StandardCharsets.UTF_8;
    String hashedPassword = argon2.hash(
        (Integer) params.get("iterations"),
        (Integer) params.get("memory"),
        (Integer) params.get("parallelism"),
        passwordCharArray,
        charset);
    argon2.wipeArray(passwordCharArray);
    return hashedPassword;
  }

  public void checkParams(Map<String, Object> params) throws BadRequestException {
    int iterations = (Integer) params.get("iterations");
    int memory = (Integer) params.get("memory");
    int parallelism = (Integer) params.get("parallelism");
    if (iterations <= 0 || iterations > 20) {
      throw new BadRequestException("The number of iterations must be greater than 0 and less than or equal to 20");
    }
    if (memory <= 0 || memory > 100000) {
      throw new BadRequestException(
          "The number of kilobytes of memory must be greater than 0 and less than or equal to 100000");
    }
    if (parallelism <= 0 || parallelism > 10) {
      throw new BadRequestException("The parallelism number must be greater than 0 and less than or equal to 10");
    }
  }

}
