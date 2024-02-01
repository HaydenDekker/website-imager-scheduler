package com.hdekker.images;

import java.time.OffsetTime;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.hdekker.appflow.AppFlowSupplier;
import com.hdekker.domain.AppFlow;
import com.hdekker.flowschedules.FlowTimerTest;

@Component
@Profile(value = {"image-port"})
@Primary
public class TestAppFlowSupplierAdapter implements AppFlowSupplier{

	@Override
	public AppFlow getFlow(Integer id) {
		return FlowTimerTest.testAppFlow(
				OffsetTime.now().plusSeconds(1));
	}

	
	
}
