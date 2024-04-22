package com.hdekker.image.server.png;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;

import org.apache.commons.imaging.color.ColorConversions;
import org.apache.commons.imaging.color.ColorHsl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hdekker.domain.ImageRetrievalEvent;
import com.hdekker.image.server.http.ImageServerConfig;

import reactor.core.publisher.Mono;

@Service
public class ImagePNGConverter implements ImageRetrivalEventPostProcessor{
	
	public static BufferedImage convertImageToGrayscale(BufferedImage image) {

		BufferedImage grayImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
		
	    // Get image width and height
	    int width = image.getWidth();
	    int height = image.getHeight();

	    // Loop through each pixel and convert to grayscale
	    for (int y = 0; y < height; y++) {
	      for (int x = 0; x < width; x++) {
	        int rgb = image.getRGB(x, y);
	        ColorHsl hsl = ColorConversions.convertRgbToHsl(rgb);
	        //Color color = new Color(rgb);
	        // Calculate average of RGB values for grayscale
	        int grayscale = (int) (hsl.l * 255);
	        		//(int) Math.round((color.getRed() + color.getGreen() + color.getBlue()) / 3.0);
	        // Set new grayscale value for all RGB components (sets the pixel to grayscale)
	        int newRGB = (rgb & 0xff000000) | (grayscale << 16) | (grayscale << 8) | grayscale;
	        
	        grayImage.setRGB(x, y, newRGB);
	      }
	    }
	    
	    return grayImage;
		
	}
	
	public static String insertGrayscaleIntoFileName(String fileName) {
		
		String[] splitOnFormat = fileName.split("\\.");
		String[] strippedFormat = Arrays.copyOf(splitOnFormat, splitOnFormat.length - 1);
		String concat = StringUtils.join(strippedFormat);
		String name = concat + "_grayscale." + splitOnFormat[splitOnFormat.length - 1];
		return name;
	}
	
	@Autowired
	ImageServerConfig imageServerConfig;

	@Override
	public Mono<ImageRetrievalEvent> process(ImageRetrievalEvent evt) {
		
		String grayscaleFilename = insertGrayscaleIntoFileName(evt.getFileName());
		
		File file = new File(imageServerConfig.getLocalDirectory() + "/" + evt.getFileName());
	    BufferedImage image;
		try {
			image = ImageIO.read(file);
		} catch (IOException e) {
			return Mono.error(()->new Exception("Couldn't read image"));
		}
		BufferedImage converted = ImagePNGConverter.convertImageToGrayscale(image);
		
		String url = imageServerConfig.getLocalDirectory() + "/" + grayscaleFilename;
	    File outputFile = new File(url);
	    try {
			ImageIO.write(converted, "png", outputFile);
		} catch (IOException e) {
			return Mono.error(()->new Exception("Couldn't write image to " + url));
		}
		
		return Mono.just(evt);
		
	}

}
