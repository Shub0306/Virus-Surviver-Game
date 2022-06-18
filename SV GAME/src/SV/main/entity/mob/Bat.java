package SV.main.entity.mob;

import java.awt.Graphics;
import java.util.Random;

import SV.main.Game;
import SV.main.Handler;
import SV.main.Id;
import SV.main.entity.Entity;
import SV.main.states.BatState;
import SV.main.tile.Tile;

public class Bat extends Entity{
	
	public int jumpTime = 0;
	
	public boolean addJumpTime = false;
	
	private Random random;

	public Bat(int x, int y, int width, int height, Id id, Handler handler, int hp) {
		super(x, y, width, height, id, handler);
		this.hp = hp;
        
        batState = BatState.IDLE;
        
        random = new Random();
           
	}
	
public void chooseState() {
		
        int nextPhase = random.nextInt(2);
        if(nextPhase==0) {
           batState = BatState.RUNNING;
           int dir = random.nextInt(2);
           if(dir==0) setVelX(-4);
           else setVelX(4);           
        }else if(nextPhase==1) {
        	batState = BatState.JUMPING;
            jumping = true;
            gravity = 8.0;
        }
        
        phaseTime = 0;
    }

	public void render(Graphics g) {
		
		if(facing==0) {
	        g.drawImage(Game.bat[frame+5].getBufferedImage(), x, y, getWidth(), getHeight(), null);
	        } else if(facing==1) {
	        g.drawImage(Game.bat[frame].getBufferedImage(), x, y, getWidth(), getHeight(), null);
	        }
	}	

	public void update() {
		
		x+=velX;
        y+=velY;
        
        if(hp<=0) die();
        phaseTime++;
        
        if((phaseTime>=360&&batState==BatState.IDLE)
                ||(phaseTime>=600&&batState!=BatState.SPINNING))
        		chooseState();	
        
        if(batState==BatState.RECOVERING&&phaseTime>=180) {
            batState = BatState.SPINNING;
            phaseTime = 0;
        }
        
        if(phaseTime>=360&&batState==BatState.SPINNING) {
            phaseTime = 0;
            batState = BatState.IDLE;
        }
        
        if(batState==BatState.IDLE||batState==BatState.RECOVERING) {
            setVelX(0);
            setVelY(0);
        }
        
        if(batState==BatState.JUMPING||batState==BatState.RUNNING) {
            attackable = true;
        } else attackable = false;
        
        if(batState!=BatState.JUMPING) {
            addJumpTime = false;
            jumpTime = 0;
        }
        
        if(addJumpTime) {
            jumpTime++;
            if(jumpTime>=30) {
                addJumpTime = false;
                jumpTime = 0;
            }
            if(!jumping&&!falling) {
                jumping = true;
                gravity = 8.0;
            }
        }
        
        for(int i=0;i<handler.tile.size();i++) {
            Tile ti = handler.tile.get(i);                      
            if(ti.isSolid()) {
            	if(getBoundsTop().intersects(ti.getBounds())) {
                	setVelY(0);
                	if(jumping) {
                		jumping = false;
                		gravity = 0.8;
                		falling = true;
                	}
                	
                }
                
                if(getBoundsBottom().intersects(ti.getBounds())) {
                	setVelY(0);
                    if(falling) {
                    	falling = false;
                    	addJumpTime = true;
                    }
                } 
                
                if(getBoundsLeft().intersects(ti.getBounds())) {
                	if(batState==BatState.RUNNING)
                		setVelX(4);
                	x = ti.getX()+ti.getWidth();
                }
                if(getBoundsRight().intersects(ti.getBounds())) {
                	if(batState==BatState.RUNNING) 
                		setVelX(-4);
                	x = ti.getX()-ti.getWidth();
                }
            }
        }
        
        for(int i =0;i<handler.entity.size();i++) {
            Entity en = handler.entity.get(i);
            if(en.getId()==Id.player) {
                if(batState==BatState.JUMPING) {
                    if(jumping||falling) {
                        if(getX()>=en.getX()-4&&getX()<=en.getX()+4) setVelX(0);
                        else if(en.getX()<getX()) setVelX(-3);
                        else if(en.getX()>getX()) setVelX(3);
                    }else setVelX(0);
                }else if(batState==BatState.SPINNING) {
                        if(en.getX()<getX()) setVelX(-3);
                        else if(en.getX()>getX()) setVelX(3);                    
                } else setVelX(0); 
            }
        }
        
        if(jumping&&!goingDownPipe) {
            gravity-=0.1;
            setVelY((int)-gravity);                                             //It will cast gravity to integer
            if(gravity<=0.0) {
                jumping = false;                                                //Jump
                falling = true;                                                 //fall
            }
        }
        
        if(falling&&!goingDownPipe) {
            gravity+=0.1;
            setVelY((int)gravity);
        }
        
        if(velX!=0) {
            frameDelay++;
            if(frameDelay>=3) {
                frame++;
                if(frame>=5) {
                	frame = 0;
                }
                frameDelay = 0;
                
            }
        }
        
	}
	
}
