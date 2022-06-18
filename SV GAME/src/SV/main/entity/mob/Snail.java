package SV.main.entity.mob;

import java.awt.Graphics;
import java.util.Random;

import SV.main.Game;
import SV.main.Handler;
import SV.main.Id;
import SV.main.entity.Entity;
import SV.main.states.SnailState;
import SV.main.tile.Tile;

public class Snail extends Entity{
	
	private Random random = new Random();
	
	private int shellCount;

	public Snail(int x, int y, int width, int height, Id id, Handler handler) {
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
        
        snailState = SnailState.WALKING;
        
	}

	public void render(Graphics g) {
		
		if(snailState==SnailState.WALKING) {
			if(facing==0) {
				g.drawImage(Game.snail[frame+5].getBufferedImage(), x, y, getWidth(), getHeight(), null);
	        } else if(facing==1) {
	        g.drawImage(Game.snail[frame].getBufferedImage(), x, y, getWidth(), getHeight(), null);
	        }
		}
		
		else if(snailState==SnailState.SPINNING) {
			if(facing==0) {
		        g.drawImage(Game.snail[7].getBufferedImage(), x, y, getWidth(), getHeight(), null);
		        } else if(facing==1) {
		        g.drawImage(Game.snail[2].getBufferedImage(), x, y, getWidth(), getHeight(), null);
		        }
			}
		
		else if(snailState==SnailState.SHELL) {
			if(facing==0) {
		        g.drawImage(Game.snail[5].getBufferedImage(), x, y, getWidth(), getHeight(), null);
		        } else if(facing==1) {
		        g.drawImage(Game.snail[5].getBufferedImage(), x, y, getWidth(), getHeight(), null);
		        }
		}
		
	}

	public void update() {
		
		x+=velX;
        y+=velY;
        
        if(snailState==SnailState.SHELL) {
        	setVelX(0);
        	shellCount++;
        	
        	if(shellCount>=300) {
                shellCount = 0;
                snailState = SnailState.WALKING;
        	}
        	
        	if(snailState==SnailState.WALKING||snailState==SnailState.SPINNING) {
        		shellCount = 0;
        		if(velX==0) {
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
        	}
        	
        }
        
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
                	if(snailState==SnailState.SPINNING) setVelX(10);
                	else setVelX(2);
                	facing = 1;
                }
                if(getBoundsRight().intersects(ti.getBounds())) {
                	if(snailState==SnailState.SPINNING) setVelX(-10);
                	else setVelX(-2);
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
            if(frameDelay>=10) {
                frame++;
                if(frame>=5) {
                	frame = 0;
                }
                frameDelay = 0;
                
            }
        }
		
	}		
}
