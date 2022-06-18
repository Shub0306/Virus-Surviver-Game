package SV.main.entity.mob;

import java.awt.Graphics;
import java.util.Random;

import SV.main.Game;
import SV.main.Handler;
import SV.main.Id;
import SV.main.entity.Entity;
import SV.main.tile.Tile;

public class Monster extends Entity{
	
	private Random random = new Random();
	
    public Monster(int x, int y, int width, int height, Id id, Handler handler) {
		super(x, y, width, height, id, handler);
		
		int dir = random.nextInt(2);
        
        switch(dir) {
            case 0:
                setVelX(-3);
                facing = 0;
                break;
            case 1:
                setVelX(3);
                facing = 1;
                break;
        }
		
	}

	public void render(Graphics g) {
		
		if(facing==0) {
			g.drawImage(Game.monster[frame+6].getBufferedImage(), x, y, 
	                getWidth(), getHeight(), null);	
		}
		else if(facing==1) {
			g.drawImage(Game.monster[frame].getBufferedImage(), x, y, 
	                getWidth(), getHeight(), null);	
		}
		
	}

	public void update() {
		x+=velX;
        y+=velY;
        
        for(int i=0;i<handler.tile.size();i++) {
            Tile ti = handler.tile.get(i);                      
            if(ti.isSolid()) {
            	if(getBoundsBottom().intersects(ti.getBounds())) {
                	setVelY(0);
                    if(falling) falling = false;
                } else {
                	if(!falling) {
                		gravity = 0.8;
                		falling = true;
                	}
                }
                if(getBoundsLeft().intersects(ti.getBounds())) {
                	setVelX(3);
                	facing = 1;
                }
                if(getBoundsRight().intersects(ti.getBounds())) {
                	setVelX(-3);
                	facing = 0;
                }
            }
        }
	
		if(falling) {
            gravity+=0.1;
            setVelY((int)gravity);	
            
		}
		
		if(velX!=0) {
            frameDelay++;
            if(frameDelay>=12) {
                frame++;
                if(frame>=6) {
                	frame = 0;
                }
                frameDelay = 0;
                
            }
        }
		
	}

}
