import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.PriorityQueue;

import javax.imageio.ImageIO;


public class Watershed {
	
	private BufferedImage image, grayImage;
	private int iHeight, iWidth;
	
	private PriorityQueue<Pixel> pixels;

	double[][] kernelX;
	double[][] kernelY;
	
	public Watershed(){
		
		image = grayImage = null;
		iWidth = iHeight = 0;
		
		PixelComparator comparator = new PixelComparator();
		pixels = new PriorityQueue<Pixel>(10, comparator);
		
		kernelX = new double[][]
				{{-1, 0, 1}, 
                {-2, 0, 2}, 
                {-1, 0, 1}};
		kernelY = new double[][]
				{{-1, -2, -1}, 
                {0,  0,  0}, 
                {1,  2,  1}};
	}
	
	public void setImage(String path){
		
		try {
			
		    image = ImageIO.read(new File(path));
		    iHeight = image.getHeight();
		    iWidth = image.getWidth();
		    
		    BufferedImage grayImage = new BufferedImage(iWidth, iHeight,  
	            BufferedImage.TYPE_BYTE_GRAY);  
	        Graphics g = grayImage.getGraphics();  
	        g.drawImage(image, 0, 0, null);  
	        g.dispose();  
	        
		} catch (IOException e) {
			System.err.println("ERROR LOADING PICTURE...");
		}
	}
	
	private double computeGradient(int x, int y){
		
		double magx, magy, mag; 
				
		magx = magy = mag = 0.0;
		
		for(int a = 0; a < 3; a++){
			for(int b = 0; b < 3; b++){
		    	
		        int xn = x + a - 1;
		        int yn = y + b - 1;

		        magx += (grayImage.getRGB(xn, yn) & 0xFF) * kernelX[a][b];
		        magy += (grayImage.getRGB(xn, yn) & 0xFF) * kernelY[a][b];
			}
		}
		
		mag = Math.sqrt(magx*magx + magy*magy);
		
		return mag;
	}

	public void run(){
		  
		
	}
}

class PixelComparator implements Comparator<Pixel>{

	@Override
	public int compare(Pixel p0, Pixel p1) {

		if(p0.getValue() > p1.getValue()){
			return -1;
		} else if(p0.getValue() > p1.getValue()){
			return 1;
		}
		return 0;
	}
	
}
