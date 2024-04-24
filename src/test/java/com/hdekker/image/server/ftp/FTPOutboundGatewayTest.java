package com.hdekker.image.server.ftp;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.io.InputStream;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import com.hdekker.TestProfiles;
import reactor.util.function.Tuple2;

@SpringBootTest
@ActiveProfiles({"ftp", TestProfiles.NO_IMAGE_RETRIVAL_PORT})
@DirtiesContext
public class FTPOutboundGatewayTest {
	
	Logger log = LoggerFactory.getLogger(FTPOutboundGatewayTest.class);
	
	@Autowired
	FTPFileGetter ftpFileGetter;

	@Test
	public void whenGatewayTriggered_ExpectFileReturned() throws InterruptedException, IOException {
		
		Tuple2<String, InputStream> resp = ftpFileGetter.get("test.png")
			.blockFirst();
		
		assertThat(resp.getT1()).isEqualTo("test.png");
		assertThat(resp.getT2().available()).isGreaterThan(1);
		
	
	}
	
}
