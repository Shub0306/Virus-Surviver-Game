package SV.main.entity.powerUp;

import java.awt.Graphics;
import java.util.Random;

import SV.main.Game;
import SV.main.Handler;
import SV.main.Id;
import SV.main.entity.Entity;
import SV.main.tile.Tile;

public class PowerStar extends Entity{
	
    private Random random;

	public PowerStar(int x, int y, int width, int height, Id id, Handler handler) {
		super(x, y, width, height, id, handler);
		random = new Random();
        
        int dir = random.nextInt(2);
        
        switch(dir) {
            case 0:
                setVelX(-4);
                break;
            case 1:
                setVelX(4);
                break;
        }
            falling = true;
            gravity = 0.17;
	}

	public void render(Graphics g) {
		
			g.drawImage(Game.star.getBufferedImage(), getX(), getY(), getWidth(), getHeight(), null);
		
	}

	public void update() {
		
		x+=velX;
        y+=velY;
        
        for(int i = 0;i<handler.tile.size();i++) {
            Tile ti = handler.tile.get(i);
            
            if(ti.isSolid()) {
                if(getBoundsBottom().intersects(ti.getBounds())) {
                    falling = false;
                    jumping = true;
                    gravity = 8.0;
                }
                
                if(getBoundsLeft().intersects(ti.getBounds())) setVelX(4);
                if(getBoundsRight().intersects(ti.getBounds())) setVelX(-4);
                
            }
        }

        if(jumping) {
            gravity-=0.17;
            setVelY((int)-gravity);                                             
            if(gravity<=0.4) {
                jumping = false;                                               
                falling = true;                                                 
            }
        }
        
        if(falling) {
            gravity+=0.17;
            setVelY((int)gravity);
        }
		
	}

}
