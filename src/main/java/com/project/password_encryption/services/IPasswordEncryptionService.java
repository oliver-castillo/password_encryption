package com.project.password_encryption.services;

import java.util.Map;

public interface IPasswordEncryptionService {
  public String encodeWithBcrypt(String password);

  public String encodeWithScrypt(String password);

  public String encodeWithPbkdf2(String password);

  public String encodeWithAlgorithm(String password, String algorithm);

  public String encodeWithArgon2id(String password, Map<String, Integer> params);
}
