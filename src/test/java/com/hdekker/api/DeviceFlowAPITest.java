package com.hdekker.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class DeviceFlowAPITest {
	
	@Autowired
	WebClient wc;

	@Test
	public void whenSubscriptionOccurs_ExpectAsyncSet() {
		
		wc.get()
			.uri(b->{
				b.path(Endpoints.DEVICEFLOWS_SUBSCRIBE);
				return b.build();
			});
		
	}
	
}
