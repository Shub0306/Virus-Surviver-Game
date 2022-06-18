package SV.main.input;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import SV.main.Game;
import SV.main.gfx.gui.Button;

public class MouseInput implements MouseListener, MouseMotionListener { 
	
	public int x, y;

	public void mouseDragged(MouseEvent e) {
		
	}

	public void mouseMoved(MouseEvent e) {
		
		 x = e.getX();                                                           //mouse is x and y coordinates will change whenever we move our mouse
	     y = e.getY();
		
	}

	public void mouseClicked(MouseEvent e) {
		
	}

	public void mousePressed(MouseEvent e) {
		
		for(int i=0;i<Game.launcher.buttons.length;i++) {
	        Button button = Game.launcher.buttons[i];
	        
	        if(x>=button.getX()&&y>=button.getY()
	                &&x<=button.getX()+button.getWidth()
	                &&y<=button.getY()+button.getHeight()) {                        /* it calls button.triggerEvent method if our mouse is clicked and 
	                                                                                   it's inside of our button (kind of creating rectangle 
	                                                                                   or of where mouse has to be in order for this code to get called) */ 
	            button.triggerEvent();
	        }
	    }
		
	}

	public void mouseReleased(MouseEvent e) {
		
	}

	public void mouseEntered(MouseEvent e) {
		
	}

	public void mouseExited(MouseEvent e) {
		
	}

}
