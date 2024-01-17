package com.hdekker.deviceappflows;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;
import java.time.LocalTime;
import java.time.OffsetTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hdekker.api.AppFlowAPI;
import com.hdekker.appflow.AppFlow;
import com.hdekker.appflow.AppFlow.WebsiteDisplayConfiguration;
import com.hdekker.device.Device;
import com.hdekker.deviceappflows.AppflowSchedulerPort.FlowSchedule;
import com.hdekker.deviceappflows.AppflowSchedulerPort.FlowScheduleEvent;
import com.hdekker.deviceappflows.AppflowSchedulerPort.FlowScheduleEventType;

import reactor.core.publisher.Mono;

@SpringBootTest
public class ImageSchedulerTest {

	@Autowired
	AppflowSchedulerPort appflowSchedulerPort;
	
	@Test
	public void canDetectEmptyFlow() {
	
		AppFlow flow = new AppFlow("Sweet", List.of());
		flow.setId(1);
		Device d = new Device(2, "TEST_DEVICE");
		ImageScheduleRequest request = new ImageScheduleRequest(d, flow);
		Optional<FlowSchedule> sch = appflowSchedulerPort.schedule(request);
		assertThat(sch.isEmpty()).isTrue();
		
	}
	
	@Test
	public void schedulesFlow() {
		
		AppFlow flow = new AppFlow("Blog", List.of(
				new WebsiteDisplayConfiguration("http://hdekker.com", 30, List.of(OffsetTime.now().plusMinutes(1)))
		));
		flow.setId(1);
		Device d = new Device(2, "TEST_DEVICE");
		ImageScheduleRequest request = new ImageScheduleRequest(d, flow);
		Optional<FlowSchedule> sch = appflowSchedulerPort.schedule(request);
		assertThat(sch.isPresent()).isTrue();
		
		// TODO
		// assert timer is present to retrieve image
		// assert timer triggers image call.
		
	}
	
	@Test
	public void whenFlowIsScheduled_expectTimerPresent() {
		
	}
	
	@Test
	public void whenFlowTimerExpires_expectEventEmitted() {
		
	}
	
	@Test
	public void canRetrieveImage() {
		
	}
	
}
