package com.hdekker.image.server.http;

import static org.assertj.core.api.Assertions.assertThat;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.hdekker.TestProfiles;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@ActiveProfiles({TestProfiles.NO_DB_CONFIGURATION, TestProfiles.MOCK_IMAGE_RETRIEVAL_PORT})
public class ImageServerHTTPAdapterTest {
	
	Logger log = LoggerFactory.getLogger(ImageServerHTTPAdapterTest.class);
	ObjectMapper om = new ObjectMapper();
	
	@Autowired
	WebClient webClient;
	
	MediaType mediaType;
	
	@Test
	public void canServeTestImageViaHTTP() throws IOException {
		
		om.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
		
		byte[] resp = webClient.get()
			.uri(urib->{
				urib.path("/image/test.png");
				return urib.build();
			})
			.exchangeToMono(cr -> {
			
				try {
					log.info("" + om.writeValueAsString(cr.headers().asHttpHeaders()));
				} catch (JsonProcessingException e) {
					
					e.printStackTrace();
				}
				mediaType = cr.headers().contentType().get();
		    
		    return cr.bodyToMono(byte[].class);
		  })
		.block();
		
		assertThat(mediaType)
			.isEqualTo(MediaType.IMAGE_PNG);
		
		BufferedImage image = ImageIO.read(new ByteArrayInputStream(resp));
		
		int type = image.getType();
		log.info("Image tyep is " + type);
		
		assertThat(type!=0);

	}

}
