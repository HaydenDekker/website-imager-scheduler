package com.hdekker.deviceflow;

import java.time.Duration;
import java.util.function.Consumer;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import com.hdekker.images.ImageRetrievalEventPort;

import reactor.core.publisher.Mono;

@Component
@Profile(value = {"device-flow"})
@Primary
public class TestImageRetrievalEventPort implements ImageRetrievalEventPort {

	@Override
	public Runnable listenForEvents(Consumer<ImageRetrievalEvent> e) {
		
		Mono.delay(Duration.ofMillis(500))
			.subscribe(c-> e.accept(new ImageRetrievalEvent()));
		
		return ()->{};
	}

}
