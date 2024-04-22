package com.hdekker.database;

import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.hdekker.TestProfiles;
import com.hdekker.deviceflow.ImageRetrivalEventPersister;
import com.hdekker.deviceflow.ImageRetrivalEventSupplier;
import com.hdekker.domain.AppFlow;
import com.hdekker.domain.ImageRetrievalEvent;

import reactor.core.publisher.Mono;

@Component
@Profile(TestProfiles.NO_DB_CONFIGURATION)
@Primary
public class DummyImageRetrievalEventDatabaseAdapter implements ImageRetrivalEventSupplier, ImageRetrivalEventPersister {

	@Override
	public Mono<ImageRetrievalEvent> persist(ImageRetrievalEvent evt) {
		return Mono.empty();
	}

	@Override
	public List<ImageRetrievalEvent> allEventsForFlow(AppFlow appFlow) {
		// TODO Auto-generated method stub
		return null;
	}

}
