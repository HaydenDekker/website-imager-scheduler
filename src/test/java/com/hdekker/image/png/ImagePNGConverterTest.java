package com.hdekker.image.png;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.imageio.ImageIO;

import org.junit.jupiter.api.Test;

public class ImagePNGConverterTest {
	
	@Test
	public void whenColorPNGToGrayScaleRequested_ExpectGrayscalePNGReturned() throws IOException {
		
		File file = new File("src/test/resources/png-examples/Screenshot 2023-04-08 152911.png");
	    BufferedImage image = ImageIO.read(file);
		BufferedImage converted = ImagePNGConverter.convertImageToGrayscale(image);
	    File outputFile = new File("src/test/resources/png-examples/grayscale_image.png");
	    ImageIO.write(converted, "png", outputFile);
		
	}
	
	@Test
	public void whenImageRetrievalEventReceived_ExpectConfigurationForPNGConversionCanBeFound() {
		
		
	}

}
