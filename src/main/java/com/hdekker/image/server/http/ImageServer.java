package com.hdekker.image.server.http;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ImageServer {
	
	@Autowired
	ImageServerConfig imageServerConfig;

	public Optional<byte[]> getPNGImage(String fileName) {
		
		  // Construct the full image path
	    String imagePath = imageServerConfig.getLocalDirectory() + "/" + fileName;

	    // Read the image data
	    try {
			byte[] imageBytes = Files.readAllBytes(Paths.get(imagePath));
			return Optional.of(imageBytes);
		} catch (IOException e) {
			return Optional.empty();
		}
	    
	}
	
}
