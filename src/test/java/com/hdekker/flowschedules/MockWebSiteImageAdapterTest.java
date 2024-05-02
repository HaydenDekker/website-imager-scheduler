package com.hdekker.flowschedules;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import com.hdekker.RuntimeProfiles;
import com.hdekker.domain.AppFlow;
import com.hdekker.domain.ImageRetrievalEvent;
import com.hdekker.domain.WebsiteDisplayConfiguration;
import com.hdekker.flowschedules.FlowSchedulerPort.FlowScheduleEvent;
import com.hdekker.flowschedules.FlowSchedulerPort.FlowScheduleEventType;

@SpringBootTest
@ActiveProfiles({RuntimeProfiles.MOCK_IMAGE_RETRIEVAL_PORT})
@DirtiesContext
public class MockWebSiteImageAdapterTest {
	
	@Autowired
	ImageRetrievalPort port;
	
	@Value("${mock-image-if.static-image}")
	String staticImage;
	
	@Test
	public void whenImageRetrievalPortTriggered_ExpectStaticImageReturned() {
		
		WebsiteDisplayConfiguration wdc = new WebsiteDisplayConfiguration("hdekker.com", 20, List.of());
		
		FlowScheduleEvent evt = new FlowScheduleEvent(
				FlowScheduleEventType.TIMER_TRIGGERED, 
				new AppFlow(1, "HELLO", List.of(wdc)), wdc);
		
		ImageRetrievalEvent ire = port.onEvent(evt);
		
		assertThat(ire.getFileName())
			.isEqualTo(staticImage);
		
		assertThat(ire.getWebsiteName())
			.isEqualTo("hdekker.com");
		
	}

}
