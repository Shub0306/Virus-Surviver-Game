package SV.main.entity;

import java.awt.Graphics;

import SV.main.Game;
import SV.main.Handler;
import SV.main.Id;
import SV.main.tile.Tile;

public class Fireball extends Entity{

	public Fireball(int x, int y, int width, int height, Id id, Handler handler, int facing) {
		super(x, y, width, height, id, handler);
		
		switch(facing) {
        case 0:
            setVelX(-8);
            break;
        case 1:
            setVelX(8);
            break;
		}
		
	}

	public void render(Graphics g) {
		
		g.drawImage(Game.fireball.getBufferedImage(), getX(), getY(), getWidth(), getHeight(), null);
		
	}

	public void update() {
		
		x+=velX;
        y+=velY;
        
        for(int i = 0;i<handler.tile.size();i++) {
            Tile ti = handler.tile.get(i);
            
            if(ti.isSolid()) {
            if(getBoundsLeft().intersects(ti.getBounds())||getBoundsRight().intersects(ti.getBounds())) die();
            
            if(getBoundsBottom().intersects(ti.getBounds())) {
            	jumping = true;
                falling = false;
                gravity = 4.0;
            } else if(!falling&&!jumping) {
            	falling = true;
                gravity = 1.0;
            }
            
          }
            
        }
            	
        
        for(int i = 0;i<handler.entity.size();i++) {
            Entity en = handler.entity.get(i);  
            
            if(en.getId()==Id.monster||en.getId()==Id.plant
                    ||en.getId()==Id.snail) {
                if(getBounds().intersects(en.getBounds())) {
                    die();
                    en.die();
                }
            }  
        }
        
        if(jumping) {
            gravity-=0.31;
            setVelY((int)-gravity);                                             
            if(gravity<=0.5) {
                jumping = false;                                                
                falling = true;                                                 
            }
        }
        
        if(falling) {
            gravity+=0.31;
            setVelY((int)gravity);
        }
		
	}

}
