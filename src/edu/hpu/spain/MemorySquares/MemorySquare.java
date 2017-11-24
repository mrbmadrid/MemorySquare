package edu.hpu.spain.MemorySquares;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.ref.SoftReference;

import javax.imageio.ImageIO;

/**
 * @descrip Creates weak reference to a randomly generated 100 x 100 pixel png image.
 * @author Brian Spain
 *
 */

public class MemorySquare {

	private SoftReference<BufferedImage> bi;
	private String name;
	
	public MemorySquare(){
		
		BufferedImage square = new BufferedImage(100, 100, 2);
		Graphics2D g2d = (Graphics2D)square.getGraphics();
		
		populate(g2d); //Draw on the image
		
		/***********************************************************
		 * Generate a random name for the image and safe the image.*
		 ***********************************************************/
		
		name = "" + (char)(Math.random()*25+65) + (int)(Math.random()*Integer.MAX_VALUE);
		File outputfile = new File("res/"+name + ".png");
	    try {
			ImageIO.write(square, "png", outputfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	    
		bi = new SoftReference<>(square); //Create a soft reference to the image
	}
	
	/*******************************************
	 * Draws random color pixels on the image. *
	 * @param g2d instance of Graphics2D       *
	 *******************************************/
	
	private void populate(Graphics2D g2d){
		int r = (int)(Math.random()*255);
		int g = (int)(Math.random()*255);
		int b = (int)(Math.random()*255);
		for(int i = 0; i < 100; i+=5){
			for(int j = 0; j < 100; j +=5){
				if(Math.random() < 0.1){
					g2d.setColor(new Color((r+=5)%255, (g+=10)%255, (b+=20)%255, (int)(Math.random()*255)));
					g2d.drawRect(i, j, 1, 1);
				}
			}
		}
	}
	
	/********************************************
	 * Gets the weak reference, if it is null   *
	 * then the image is reloaded from the file *
	 ********************************************/
	
	public BufferedImage get(){
		BufferedImage square = bi.get();
	    if(square == null) {
	        System.out.println("Reloading " + name + ".");
	        try {
				square = ImageIO.read(new File("res/"+name + ".png"));
				bi = new SoftReference<>(square);
			} catch (IOException e) {
				e.printStackTrace();
			}
	    } 
	    return square;
	}
	
	/*******************************************************
	 * @return the name of the file the image was saved to *
	 *******************************************************/
	
	public String getName(){
		return name;
	}
	
	/*******************************************************
	 * Delete the file (so we don't keep thousands of tiny *
	 * png files...                                        *
	 *******************************************************/
	
	public void delete(){
		File file = new File("res/"+name + ".png");
		file.delete();
	}
	
	
}
