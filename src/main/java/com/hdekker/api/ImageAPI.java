package com.hdekker.api;

import java.io.IOException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.hdekker.image.server.http.ImageServer;

@RestController()
public class ImageAPI {
	
	Logger log = LoggerFactory.getLogger(ImageAPI.class);
	
	@Autowired
	ImageServer imageServer;

	  @GetMapping("/image/{imageName}")
	  public ResponseEntity<byte[]> getImage(@PathVariable String imageName) throws IOException {
	    
		log.info("Function: Retrieving image - " + imageName);
		    
		byte[] imageOpt = imageServer.getPNGImage(imageName)
				.orElseThrow(()-> {	
					log.warn("Could not find image " + imageName);
					return new ResponseStatusException(HttpStatus.NOT_FOUND);
				});
		
	    // Set headers for content type and potential caching
	    HttpHeaders headers = new HttpHeaders();
	    headers.setContentType(MediaType.IMAGE_PNG);
	    
	    // Optional: Set cache control headers (e.g., for browser caching)
	    // Return the image data with appropriate headers
	    return new ResponseEntity<>(imageOpt, headers, HttpStatus.OK);
	  
	}
	
}
