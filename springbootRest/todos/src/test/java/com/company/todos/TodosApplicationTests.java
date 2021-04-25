package com.company.todos;

import com.company.todos.security.Constants;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class TodosApplicationTests {

	@Test
	void contextLoads() {
		// Validate Context is loaded
		assertNotNull(Constants.getTokenSecret());
		assertTrue(Constants.getTokenExpirationTimeInSeconds() != 0);
	}

}
