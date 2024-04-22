package com.hdekker.image.png;

import static org.assertj.core.api.Assertions.assertThat;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.hdekker.TestProfiles;
import com.hdekker.domain.ImageRetrievalEvent;
import com.hdekker.image.server.png.ImagePNGConverter;
import reactor.core.publisher.Mono;

@SpringBootTest
@ActiveProfiles({TestProfiles.NO_DB_CONFIGURATION, TestProfiles.MOCK_IMAGE_RETRIEVAL_PORT, "image_conv"})
public class ImagePNGConverterTest {
	
	@Test
	public void whenColorPNGToGrayScaleRequested_ExpectGrayscalePNGReturned() throws IOException {
		
		File file = new File("src/test/resources/png-examples/Screenshot 2023-04-08 152911.png");
	    BufferedImage image = ImageIO.read(file);
		BufferedImage converted = ImagePNGConverter.convertImageToGrayscale(image);
	    File outputFile = new File("src/test/resources/png-examples/grayscale_image.png");
	    ImageIO.write(converted, "png", outputFile);
	    int type = converted.getType();
	    assertThat(type)
	    	.isEqualTo(BufferedImage.TYPE_BYTE_GRAY);
	    
	}
	
	@Test
	public void whenFileNameProvided_ExpectGrayscaleInserted() {
		
		String testFilename = "happydays.png";
		String converted = ImagePNGConverter.insertGrayscaleIntoFileName(testFilename);
		assertThat(converted)
			.isEqualTo("happydays_grayscale.png");
	}
	
	@Autowired
	ImagePNGConverter imagePNGConverter;
	
	@Test
	public void whenImageRetrievalEventPersisted_ExpectImageIsConvertedToGrayscale() {
		
		ImageRetrievalEvent evt = new ImageRetrievalEvent("image.png", "image.com.au");
		Mono<ImageRetrievalEvent> result = imagePNGConverter.process(evt);
		result.block();
		File grayscaleImage = new File("src/test/resources/png-examples/png-conv-evt-test/image_grayscale.png");
		assertThat(grayscaleImage.canRead())
			.isTrue();
		
	}
	

}
