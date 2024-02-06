package com.hdekker.database;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.hdekker.TestProfiles;
import com.hdekker.deviceflow.ImageRetrivalEventPersister;
import com.hdekker.deviceflow.ImageRetrivalEventSupplier;
import com.hdekker.domain.AppFlow;
import com.hdekker.domain.ImageRetrievalEvent;

@Component
@Profile(TestProfiles.MOCK_IMAGE_EVENT_DB_ADAPTER)
@Primary
public class MockImageEventDatabaseAdapter implements ImageRetrivalEventPersister, ImageRetrivalEventSupplier {

	Map<String, ImageRetrievalEvent> imageRetrievalEvent = new HashMap<>();
	
	@Override
	public List<ImageRetrievalEvent> allEventsForFlow(AppFlow appFlow) {
		return new ArrayList<>(imageRetrievalEvent.values());
	}

	@Override
	public ImageRetrievalEvent persist(ImageRetrievalEvent evt) {
		imageRetrievalEvent.put(evt.getFileName(), evt);
		return evt;
	}

}
