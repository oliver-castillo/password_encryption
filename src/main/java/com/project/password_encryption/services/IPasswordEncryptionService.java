package com.project.password_encryption.services;

import java.util.Map;

public interface IPasswordEncryptionService {

    Map<String, String> encodePassword(String algorithm, Map<String, Object> params);

}
