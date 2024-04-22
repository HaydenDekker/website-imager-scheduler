package com.hdekker.image.server.http;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import com.hdekker.TestProfiles;

@SpringBootTest
@ActiveProfiles({TestProfiles.NO_DB_CONFIGURATION, TestProfiles.MOCK_IMAGE_RETRIEVAL_PORT})
@DirtiesContext
public class ImageServerTest {
	
	Logger log = LoggerFactory.getLogger(ImageServerTest.class);
	
	@Autowired
	ImageServer imageServer;
	
	@Autowired
	ImageServerConfig imageServerConfig;
	
	@Test
	public void canRetrieveImageFromFileSystem() {
		
		String testImage = "grayscale_image.png";
		Optional<byte[]> image = imageServer.getPNGImage(testImage);
		assertThat(image.isPresent())
			.isTrue();
	}
	
}
