package com.hdekker.deviceflow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hdekker.flowschedules.ImageRetrievalEventPort;

import jakarta.annotation.PreDestroy;

@Service
public class ImageRetrivalEventPersisterPort {

	@Autowired
	ImageRetrievalEventPort imageRetrievalEventPort;
	
	@Autowired
	ImageRetrivalEventPersister persister;
	
	Runnable deleter;
	
	public ImageRetrivalEventPersisterPort(ImageRetrivalEventPersister persister,
			ImageRetrievalEventPort imageRetrievalEventPort){
		
		deleter = imageRetrievalEventPort.listenForEvents(persister::persist);
		
	}
	
	@PreDestroy
	public void stop() {
		deleter.run();
	}
	
}
