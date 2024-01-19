package com.hdekker;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.hdekker.flowschedules.DeviceAppflowAssignment;
import com.hdekker.image.TestImagePort;

@SpringBootTest
@ActiveProfiles(value = {"image-port"})
public class FlowUseCaseTest {

	@Autowired
	FlowUseCase flowUseCase;
	
	@Autowired
	TestImagePort testImagePort;
	
	@Test
	public void whenFlowProvided_ExpectImageRetrievalInitiated() throws InterruptedException {
		
		DeviceAppflowAssignment assign = new DeviceAppflowAssignment(1, 2, 3);
		flowUseCase.scheduleFlow(assign);
		Thread.sleep(2000);
		assertThat(testImagePort.evts.size())
			.isEqualTo(1);
		
	}
	
}
