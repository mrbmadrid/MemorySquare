package edu.hpu.spain.MemorySquares;
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JPanel;
/**
 * Populates the images for the memory. Created in a separate thread so that
 * the images can be created without slowing down the rendering thread.
 * @author Brian Spain
 */

public class MemoryGenerator extends JPanel implements Runnable{
	
	private static final long serialVersionUID = 1L;
	private ArrayList<MemorySquare> squares; //where the images go
	private HashMap<String, Integer> addresses; //addresses to the images
	private Point origin; //reference to render origin
	private int HEIGHT = 1200, WIDTH = 1200;
	boolean running;
	int index = 0;
	
	public MemoryGenerator(ArrayList<MemorySquare> squares, HashMap<String, Integer> addresses, Point origin){
		this.squares = squares;
		this.addresses = addresses;
		this.origin = origin;
		fill();
	}
	
	public void start(){
		if(running) return;
		running = true;
		new Thread(this, "MemGen-Thread").start();
	}
	
	@Override
	public void run() {
		double nspertick = 1000000000.0/60.0;
		double lastTime = System.nanoTime();
		double tick = 0.0;
		while(running){
			double currentTime = System.nanoTime();
			tick += (currentTime - lastTime) / nspertick;
			lastTime = currentTime;
			if(tick >= 1.0){
				fill();
				tick--;
			}
			try{
				Thread.sleep(5l);
			}catch(InterruptedException e){
				
			}
		}
	}
	
	/***************************************************
	 * Creates images all around the viewing area, the *
	 * strategy is to keep at least three image width  *
	 * buffer on all sides.                            *
	 ***************************************************/
	
	public void fill(){
		int i = origin.x/100-6;
		while((i-origin.x/100)*100 < WIDTH){
			int j = origin.y/100-6;
			while((j-origin.y/100)*100< HEIGHT){
				if(!addresses.containsKey(i+"-"+j)){
					squares.add(new MemorySquare());
					addresses.put(i+"-"+j, index++);
				}
				j++;
			}
			i++;
		}
	}

}
