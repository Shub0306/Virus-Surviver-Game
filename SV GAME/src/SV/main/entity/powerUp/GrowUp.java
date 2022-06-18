package SV.main.entity.powerUp;

import java.awt.Graphics;
import java.util.Random;

import SV.main.Game;
import SV.main.Handler;
import SV.main.Id;
import SV.main.entity.Entity;
import SV.main.tile.Tile;

public class GrowUp extends Entity{

	private Random random = new Random();
	
	public GrowUp(int x, int y, int width, int height, Id id, Handler handler, int type) {
		super(x, y, width, height, id, handler);
		this.type = type;
		
		int dir = random.nextInt(2);
		
		switch(dir) {
		case 0:
            setVelX(-3);
            break;
        case 1:
            setVelX(3);
            break;
		}
		
	}

	public void render(Graphics g) {
		
		switch(getType()) {
        case 0:
            g.drawImage(Game.growUp.getBufferedImage(), x, y, getWidth(), getHeight(), null);   
            break;
            
        case 1:
            g.drawImage(Game.lifeUp.getBufferedImage(), x, y, getWidth(), getHeight(), null);   
            break;                
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
                	if(!falling&&!jumping) {
                		gravity = 0.8;
                		falling = true;
                	}
                }
                if(getBoundsLeft().intersects(ti.getBounds())) {
                	setVelX(3);
                }
                if(getBoundsRight().intersects(ti.getBounds())) {
                	setVelX(-3);
                }
            }
        }
	
		if(falling) {
            gravity+=0.1;
            setVelY((int)gravity);	
            
		}
		
	}

}
