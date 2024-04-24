package com.hdekker.api;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;
import java.util.Map;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.web.reactive.function.client.WebClient;

import com.hdekker.domain.AppFlow;

@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
@DirtiesContext
public class AppFlowApiTest {

	private static final String TEST_APP_FLOW = "TestAppFlow";
	
	@Autowired
	WebClient wc;
	
	@Test
	public void canCreateNewAppFlow() {
		
		AppFlow appFlow = wc.post()
			.uri(b->{
				b.path("appflow/create");
				b.path("/{name}");
				return b.build(Map.of("name", TEST_APP_FLOW));
			})
			.bodyValue("")
			.exchangeToMono(cr->
				cr.bodyToMono(AppFlow.class)	
			).block(Duration.ofSeconds(10));
		
		assertThat(appFlow).isNotNull();
		assertThat(appFlow.getName()).isEqualTo(TEST_APP_FLOW);
		
	}
	
}
