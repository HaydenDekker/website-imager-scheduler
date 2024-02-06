package com.hdekker.flowschedules;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

import com.hdekker.TestProfiles;
import com.hdekker.domain.ImageRetrievalEvent;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Configuration
public class ImageRetrievalEventPortMocks {
	
	Logger log = LoggerFactory.getLogger(ImageRetrievalEventPortMocks.class);
	
	public Boolean deleterHasRun = false;
	
	public static final ImageRetrievalEvent TestEvent = new ImageRetrievalEvent("hdekker_com.png", "hdekker.com");
	public static final ImageRetrievalEvent IgnoredEvent = new ImageRetrievalEvent("google_com.png", "google.com");
	
	ImageRetrievalEvent imageEvent = TestEvent;
	
	public void setImageEvent(ImageRetrievalEvent imageEvent) {
		this.imageEvent = imageEvent;
	}

	@Bean
	@Primary
	@Profile(TestProfiles.MOCK_FLUX_1s_IMAGE_EVENT_PORT)
	ImageRetrievalEventPort imEvtPort() {
		return (e) -> {
			Flux.interval(Duration.ofSeconds(1))
			.subscribe(l->e.accept(imageEvent));
			return ()->{};
		};
	}
	
	@Bean
	@Primary
	@Profile(value = {TestProfiles.MOCK_DELAYED_500ms_IMAGE_EVENT_PORT})
	public ImageRetrievalEventPort imageRetrievalEventPort() {
	
		return (e) -> {
			Mono.delay(Duration.ofMillis(500))
				.subscribe(c-> e.accept(imageEvent));
			
			return ()->{
				deleterHasRun = true;
				log.info("Deleter Run.");
			};
		};
	}
}
