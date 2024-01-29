package com.hdekker.images;

import java.util.function.Consumer;

import org.springframework.stereotype.Component;

@Component
public class WebsiteImagerAdapter implements ImageRetrievalPort, ImageRetrievalEventPort{

	@Override
	public void onEvent(FlowScheduleEvent evt) {
		
	}

	@Override
	public Runnable listenForEvents(Consumer<ImageRetrievalEvent> e) {
		// TODO Auto-generated method stub
		return null;
	}

}
