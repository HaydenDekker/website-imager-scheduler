package com.hdekker.database;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.hdekker.RuntimeProfiles;
import com.hdekker.deviceflow.ImageRetrivalEventPersister;
import com.hdekker.deviceflow.ImageRetrivalEventSupplier;
import com.hdekker.domain.AppFlow;
import com.hdekker.domain.ImageRetrievalEvent;

import reactor.core.publisher.Mono;

@Service
@Profile(RuntimeProfiles.POSTGRESS)
public class ImageRetrivalEventDatabaseAdapter implements ImageRetrivalEventSupplier, ImageRetrivalEventPersister {

	@Autowired
	ImageRetrivalEventRepository imageRetrivalEventRepository;
	
	@Override
	public Mono<ImageRetrievalEvent> persist(ImageRetrievalEvent evt) {
		return Mono.create(s->{
			s.success(imageRetrivalEventRepository.save(evt));
		});
				
	}

	@Override
	public List<ImageRetrievalEvent> allEventsForFlow(AppFlow appFlow) {
		return appFlow.getSiteOrder()
					.stream()
					.flatMap(wdc->imageRetrivalEventRepository.findAllByWebsiteName(wdc.getWebsite())
							.stream())
					.collect(Collectors.toList());
	}

}
