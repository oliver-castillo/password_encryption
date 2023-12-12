package com.project.password_encryption;

import com.project.password_encryption.services.PasswordEncryptionService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
class PasswordEncryptionApplicationTests {

	private MockMvc mockMvc;

	private PasswordEncryptionService passwordEncryptionService;
	@Test
	void contextLoads() {
	}

}
