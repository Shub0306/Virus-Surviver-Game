package SV.main.gfx.gui;

import java.awt.Color;
import java.awt.Graphics;

import SV.main.Game;


public class Launcher {
	
	public Button[] buttons;
	
	public Launcher() {
	       buttons = new Button[2];
	       
	       buttons[0] = new Button(450, 200, 350, 70, "Start Game");
	       buttons[1] = new Button(475, 350, 300, 70, "Exit Game");

	       
	    }
	
	public void render(Graphics g) {  
		
		g.setColor(Color.white);
        g.fillRect(0, 0, Game.getFrameWidth(), Game.getFrameHeight());
        
        for(int i=0;i<buttons.length;i++) {
            buttons[i].render(g);
		
        }
	}
}
