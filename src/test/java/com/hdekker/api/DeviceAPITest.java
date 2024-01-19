package com.hdekker.api;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.web.reactive.function.client.WebClient;

import com.hdekker.device.Device;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class DeviceAPITest {
	
	@Autowired
	WebClient wc;
	
	public static final String TEST_DEVICE_NAME = "happy_device";
	
	@Test
	public void canCreateDevice() {
		
		Device device = wc.post()
			.uri(b->{
				b.path(Endpoints.DEVICE_CREATE_NAME);
				return b.build(Map.of("name", TEST_DEVICE_NAME));
		})
		.bodyValue("")
		.exchangeToMono(cr->
			cr.bodyToMono(Device.class)	
		).block(Duration.ofSeconds(10));
		
		assertThat(device.getName()).isEqualTo(TEST_DEVICE_NAME);
		
	}
	
}
