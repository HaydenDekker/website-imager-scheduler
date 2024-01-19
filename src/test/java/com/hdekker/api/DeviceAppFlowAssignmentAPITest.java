package com.hdekker.api;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.web.reactive.function.client.WebClient;

import com.hdekker.flowschedules.DeviceAppflowAssignment;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class DeviceAppFlowAssignmentAPITest {

	@Autowired
	WebClient wc;
	
	@Test
	public void canCreateAssignment() {
		
		DeviceAppflowAssignment assignment = new DeviceAppflowAssignment(0, 4, 5);
		
		DeviceAppflowAssignment a = wc.post()
				.uri(b->{
					b.path(Endpoints.DEVICE_APPFLOWS_CREATE);
					return b.build();
				})
				.bodyValue(assignment)
				.exchangeToMono(cr->cr.bodyToMono(DeviceAppflowAssignment.class))
				.block(Duration.ofSeconds(10));
		
		assertThat(a.getAppFlowId()).isEqualTo(5);
		assertThat(a.getDeviceId()).isEqualTo(4);		
	}
	
}
