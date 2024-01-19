package com.hdekker;
import org.junit.jupiter.api.Test;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class SpringTest {

	@Test
	public void canRun() {
		LoggerFactory.getLogger(SpringTest.class).info("Sweet.");
	}
	
}
