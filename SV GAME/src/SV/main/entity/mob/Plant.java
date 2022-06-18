package SV.main.entity.mob;

import java.awt.Graphics;

import SV.main.Game;
import SV.main.Handler;
import SV.main.Id;
import SV.main.entity.Entity;

public class Plant extends Entity{
	
	private int wait;
    private int pixelsTravelled;
    
    private boolean moving;
    private boolean insidePipe;

	public Plant(int x, int y, int width, int height, Id id, Handler handler) {
		super(x, y, width, height, id, handler);
		
		moving = false;
        insidePipe = true;
		
	}

	public void render(Graphics g) {
		
		g.drawImage(Game.plant.getBufferedImage(), x, y, getWidth(), getHeight(), null);
		
	}

	public void update() {
		
		y+=velY;
		
		if(!moving) wait++;
        if(wait>=180) {
            if(insidePipe) insidePipe = false;
            else insidePipe = true; 
            
            moving = true;
            wait = 0;
        }
        
        if(moving) {
            if(insidePipe) setVelY(-3);
            else setVelY(3);
            
            pixelsTravelled+=velY;
            
            if(pixelsTravelled>=getHeight()||pixelsTravelled<=-getHeight()) {
                pixelsTravelled = 0;
                moving = false;
                
                setVelY(0);
            }
        }
		
	}

}
