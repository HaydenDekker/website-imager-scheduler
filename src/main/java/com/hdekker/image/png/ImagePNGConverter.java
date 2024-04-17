package com.hdekker.image.png;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.ColorConvertOp;
import java.io.File;
import java.net.URL;

import javax.imageio.ImageIO;

import org.apache.commons.imaging.color.ColorConversions;
import org.apache.commons.imaging.color.ColorHsl;

public class ImagePNGConverter {
	
	public static BufferedImage convertImageToGrayscale(BufferedImage image) {

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
	        
	        image.setRGB(x, y, newRGB);
	      }
	    }
	    
	    return image;
		
	}

}
