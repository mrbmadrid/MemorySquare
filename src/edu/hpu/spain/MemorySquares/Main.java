package edu.hpu.spain.MemorySquares;
import javax.swing.JFrame;

/**
 * @description Main class for MemorySquares, a program that uses Java memory
 * management tools.
 * @author Brian Spain
 *
 */

public class Main {
	public static void main(String args[]){
		JFrame frame = new JFrame("Memory Squares");
		Memory mem = new Memory();
		Input input = new Input(mem);
		frame.setSize(500, 500);
		frame.setFocusable(true);
		frame.setResizable(false);
		frame.add(mem);
		frame.addKeyListener(input);
		
		/*
		 * Set window listener so I can make the program delete all of the
		 * created images before exiting.
		 */
		
		frame.addWindowListener(input);
		mem.start();
		frame.setVisible(true);
		
	}
}
