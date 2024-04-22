package com.hdekker.deviceflow;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hdekker.flowschedules.ImageRetrievalEventPort;
import com.hdekker.image.server.png.ImageRetrivalEventPostProcessor;

import jakarta.annotation.PreDestroy;

/***
 *  To capture the metadata associated with a new image
 *  and perform any post processing
 * 
 */
@Service
public class ImageRetrivalEventPersisterPort {

	@Autowired
	ImageRetrievalEventPort imageRetrievalEventPort;
	
	@Autowired
	ImageRetrivalEventPersister persister;
	
	@Autowired
	ImageRetrivalEventPostProcessor imageRetrivalEventPostProcessor;
	
	Runnable deleter;
	
	public ImageRetrivalEventPersisterPort(ImageRetrivalEventPersister persister,
			ImageRetrievalEventPort imageRetrievalEventPort){
		
		deleter = imageRetrievalEventPort.listenForEvents((e)->{
			
			persister.persist(e)
				.subscribe(evt->{
					imageRetrivalEventPostProcessor.process(evt);
				});
			
			
		});
		
		
	}
	
	@PreDestroy
	public void stop() {
		deleter.run();
	}
	
}
