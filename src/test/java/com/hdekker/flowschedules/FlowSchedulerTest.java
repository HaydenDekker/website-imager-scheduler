package com.hdekker.flowschedules;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.OffsetTime;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.hdekker.RuntimeProfiles;
import com.hdekker.TestProfiles;
import com.hdekker.domain.AppFlow;
import com.hdekker.domain.WebsiteDisplayConfiguration;

@SpringBootTest
@ActiveProfiles({RuntimeProfiles.POSTGRESS, TestProfiles.MOCK_IMAGE_RETRIEVAL_PORT})
public class FlowSchedulerTest {

	@Autowired
	FlowSchedulerPort appflowSchedulerPort;
	
	@Test
	public void canDetectEmptyFlow() {
	
		AppFlow flow = new AppFlow(null, "Sweet", List.of());
		flow.setId(1);
		Optional<FlowTimer> sch = appflowSchedulerPort.schedule(flow);
		assertThat(sch.isEmpty()).isTrue();
	}
	
	@Autowired
	FlowScheduler scheduler;
	
	@Test
	public void whenWebsitesPresent_SchedulesFlow() {
		
		AppFlow flow = new AppFlow(1, "Blog", List.of(
				new WebsiteDisplayConfiguration("http://hdekker.com", 30, List.of(OffsetTime.now().plusMinutes(1)))
		));
		Optional<FlowTimer> sch = appflowSchedulerPort.schedule(flow);
		assertThat(sch.isPresent()).isTrue();
		assertThat(scheduler.schedulerState
				.getFlowTimers()
				.get(sch.get().flow()))
			.isNotNull();
	
	}
	
	@Test
	public void whenAppFlowUpdated_RefreshesTimers() {
		
		// Reset state.
		scheduler.schedulerState.flowTimers = new HashMap<>();
		
		AppFlow flow1 = new AppFlow(1, "Blog", List.of(
				new WebsiteDisplayConfiguration("http://hdekker.com", 30, List.of(OffsetTime.now().plusMinutes(1)))
		));
		
		AppFlow flow1Updated = new AppFlow(1, "Blog", List.of(
				new WebsiteDisplayConfiguration("http://hdekker.com", 20, List.of(
						OffsetTime.now().plusMinutes(1), OffsetTime.now().plusMinutes(10)))
		));
		
		Optional<FlowTimer> sch = appflowSchedulerPort.schedule(flow1);
		assertThat(sch.isPresent()).isTrue();
		assertThat(scheduler.schedulerState
				.getFlowTimers()
				.get(sch.get().flow()))
			.isNotNull();
		
		sch = appflowSchedulerPort.schedule(flow1Updated);
		assertThat(sch.isPresent()).isTrue();
		assertThat(scheduler.schedulerState
				.getFlowTimers()
				.get(sch.get().flow()))
			.isNotNull();
		
		assertThat(scheduler.schedulerState.flowTimers.size())
			.isEqualTo(1);
		
		
	}


}
