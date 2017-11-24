package edu.hpu.spain.MemorySquares;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Input handler for keyboard and Window listener for close operation.
 * @author Brian Spain
 *
 */

public class Input extends KeyAdapter implements WindowListener{
	
	Memory mem;
	
	public Input(Memory mem){
		this.mem = mem;
	}

	@Override
	public void keyPressed(KeyEvent e){
		mem.keyPressed(e);
	}
	
	@Override
	public void keyReleased(KeyEvent e){
		mem.keyReleased(e);
	}

	@Override
	public void windowOpened(WindowEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void windowClosing(WindowEvent e) {
		mem.clearMemories(); //delete images
		mem.stop(); //stop thread
		
	}

	@Override
	public void windowClosed(WindowEvent e) {
	}

	@Override
	public void windowIconified(WindowEvent e) {
		
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
		
	}

	@Override
	public void windowActivated(WindowEvent e) {
		
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
	
	}
	
}
