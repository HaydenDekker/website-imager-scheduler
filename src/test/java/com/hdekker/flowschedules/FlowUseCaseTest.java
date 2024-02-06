package com.hdekker.flowschedules;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.OffsetTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.hdekker.TestProfiles;
import com.hdekker.domain.AppFlow;
import com.hdekker.domain.WebsiteDisplayConfiguration;

@SpringBootTest
@ActiveProfiles(value = {TestProfiles.MOCK_IMAGE_RETRIEVAL_PORT, TestProfiles.MOCK_IMAGE_EVENT_DB_ADAPTER})
public class FlowUseCaseTest {

	@Autowired
	FlowSchedulerPort flowSchedulerPort;
	
	@Autowired
	ImageRetrievalPortMocks imageRetrievalPortMocks;
	
	@Autowired
	FlowScheduler flowScheduler;
	
	@Test
	public void whenFlowProvided_ExpectImageRetrievalInitiated() throws InterruptedException {
		
		AppFlow appFlow = new AppFlow(1, "HELLO", 
				List.of(new WebsiteDisplayConfiguration("hdekker.com", 
				20, 
				List.of(OffsetTime.now().plusSeconds(1)))));
		flowSchedulerPort.schedule(appFlow);
		Thread.sleep(2000);
		assertThat(imageRetrievalPortMocks.evts.size())
			.isEqualTo(1);
		
	}
	
}
