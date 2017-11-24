package edu.hpu.spain.MemorySquares;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JPanel;

/**
 * Renders the images and adjusts location based on keyboard input.
 * @author Brian Spain
 */

public class Memory extends JPanel implements Runnable{

	private static final long serialVersionUID = 1L;
	private int WIDTH = 600, HEIGHT = 600;
	private ArrayList<MemorySquare> squares; //images created by MemoryGenerator
	private HashMap<String, Integer> addresses; //image address generated by MemoryGenerator
	private Point origin; //reference point for rendering
	private boolean[] move = new boolean[4];
	private long maxMemory; //store max memory for status bar
	Runtime run; //for calling current memory later
	
	boolean running;
	
	public Memory(){
		squares = new ArrayList<>();
		addresses = new HashMap<>();
		origin = new Point(0,0);
		run = Runtime.getRuntime();
		maxMemory = run.maxMemory();
	}
	
	public void start(){
		if(running) return;
		running = true;
		new MemoryGenerator(squares, addresses, origin).start(); //creates images
		new Thread(this, "Memory-Thread").start();
	}
	
	public void stop(){
		running = false;
		System.exit(0);
	}
	
	@Override
	public void run() {
		double nspertick = 1000000000.0/30.0;
		double lastTime = System.nanoTime();
		double tick = 0.0;
		while(running){
			double currentTime = System.nanoTime();
			tick += (currentTime - lastTime) / nspertick;
			lastTime = currentTime;
			if(tick >= 1.0){
				tick();
				render();
				tick--;
			}
			try{
				Thread.sleep(5l);
			}catch(InterruptedException e){
				e.printStackTrace();
			}
		}
	}
	
	/***********************************
	 * Adjust view based on user input *
	 ***********************************/
	
	public void tick(){
		if(move[0]) origin.y+=2;
		if(move[1]) origin.y-=2;
		if(move[2]) origin.x-=2;
		if(move[3]) origin.x+=2;
	}
	
	
	
	public void render(){
		this.repaint();
	}
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		
		/**************************
		 * Draw memory status bar *
		 **************************/
		
		double memPerc = ((double)run.freeMemory()/(double)maxMemory);
		int memwidth = (int)(memPerc*480);
		g2d.setColor(Color.BLACK);
		g2d.drawString("Available Memory:", 10, 20);
		g2d.setColor(new Color((int)(255-(255*memPerc)), (int)(255*memPerc), 0, 255));
		g2d.fillRect(10, 30, memwidth, 20);
		g2d.drawRect(10, 30, memwidth, 20);
		
		/*********************************************
		 * Draw images that are within screen domain *
		 *********************************************/
		
		int i = origin.x/100-1;
		while((i-origin.x/100)*100 < WIDTH){
			int j = origin.y/100-1;
			while((j-origin.y/100)*100< HEIGHT){
				g2d.drawImage(squares.get(addresses.get(i+"-"+j)).get(),(i*100)-origin.x, (j*100)-origin.y, null);
				j++;
			}
			i++;
		}
	}
	
	public void keyPressed(KeyEvent e){
		switch(e.getKeyCode()){
		case KeyEvent.VK_UP:
			move[0]=true;
			break;
		case KeyEvent.VK_DOWN:
			move[1]=true;
			break;
		case KeyEvent.VK_RIGHT:
			move[2]=true;
			break;
		case KeyEvent.VK_LEFT:
			move[3]=true;
			break;
			default:
				break;			
		}
	}
	
	public void keyReleased(KeyEvent e){
		switch(e.getKeyCode()){
		case KeyEvent.VK_UP:
			move[0]=false;
			break;
		case KeyEvent.VK_DOWN:
			move[1]=false;
			break;
		case KeyEvent.VK_RIGHT:
			move[2]=false;
			break;
		case KeyEvent.VK_LEFT:
			move[3]=false;
			break;
			default:
				break;			
		}
	}
	
	//Delete all images
	
	public void clearMemories(){
		for(MemorySquare square : squares){
			square.delete();
		}
	}
}