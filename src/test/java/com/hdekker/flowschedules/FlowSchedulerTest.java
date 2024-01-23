package com.hdekker.flowschedules;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.OffsetTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hdekker.domain.AppFlow;
import com.hdekker.domain.Device;
import com.hdekker.domain.AppFlow.WebsiteDisplayConfiguration;
import com.hdekker.flowschedules.FlowSchedulerPort.FlowSchedule;

@SpringBootTest
public class FlowSchedulerTest {

	@Autowired
	FlowSchedulerPort appflowSchedulerPort;
	
	@Test
	public void canDetectEmptyFlow() {
	
		AppFlow flow = new AppFlow("Sweet", List.of());
		flow.setId(1);
		Optional<FlowSchedule> sch = appflowSchedulerPort.schedule(flow);
		assertThat(sch.isEmpty()).isTrue();
		
	}
	
	@Autowired
	FlowScheduler scheduler;
	
	@Test
	public void whenWebsitesPresent_SchedulesFlow() {
		
		AppFlow flow = new AppFlow("Blog", List.of(
				new WebsiteDisplayConfiguration("http://hdekker.com", 30, List.of(OffsetTime.now().plusMinutes(1)))
		));
		Optional<FlowSchedule> sch = appflowSchedulerPort.schedule(flow);
		assertThat(sch.isPresent()).isTrue();
		assertThat(scheduler.schedulerState
				.getFlowTimers()
				.get(sch.get()))
			.isNotNull();
	
	}

	
}
