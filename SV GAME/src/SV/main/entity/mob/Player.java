package SV.main.entity.mob;

import java.awt.Graphics;
import java.util.Random;

import SV.main.Game;
import SV.main.Handler;
import SV.main.Id;
import SV.main.entity.Entity;
import SV.main.entity.Particle;
import SV.main.states.BatState;
import SV.main.states.PlayerState;
import SV.main.states.SnailState;
import SV.main.tile.Tile;
import SV.main.tile.Trail;

public class Player extends Entity{
	
	private int pixelsTravelled = 0;
	private int restoreTime;
	
	private Random random;
	
	private boolean restoring;
	
	private boolean invincible = false;
	private int invicibilityTime = 0;
	private int particleDelay = 0;
	
	public Player(int x, int y, int width, int height, Id id, Handler handler) {
		super(x, y, width, height, id, handler);
		
		state = PlayerState.SMALL;
		
		random = new Random();
		
	}

	public void render(Graphics g) {
		
		if(state==PlayerState.FIRE) {
			if(facing==0) {
				g.drawImage(Game.fireplayer[frame+8].getBufferedImage(), x, y, getWidth(), getHeight(), null);
			} else if(facing==1) {
				g.drawImage(Game.fireplayer[frame].getBufferedImage(), x, y, getWidth(), getHeight(), null);
			}
		} else {
			if(facing==0) {
				g.drawImage(Game.player[frame+8].getBufferedImage(), x, y, getWidth(), getHeight(), null);	
			}
			if(facing==1) {
				g.drawImage(Game.player[frame].getBufferedImage(), x, y, getWidth(), getHeight(), null);	
			}
		}
		
	}

	public void update() {
		
        x+=velX;
        y+=velY;
        
        if(getY()>Game.deathY) die();
        
        if(invincible) {
        	
        	if(facing==0) handler.addTile(new Trail(getX(), getY(), getWidth(), getHeight(), false, Id.trail, handler, 
        			Game.player[frame+8].getBufferedImage()));
            else if(facing==1) handler.addTile(new Trail(getX(), getY(), getWidth(), getHeight(), false, Id.trail, handler, 
                    Game.player[frame].getBufferedImage()));
        	
        	particleDelay++;
        	if(particleDelay>=3) {
        		handler.addEntity(new Particle(getX() + random.nextInt(getWidth()), getY() + random.nextInt(getHeight()), 20, 20, 
                        Id.particle, handler));
        		particleDelay = 0;
        	}
        	
        	if(velX==5) setVelX(8);
            else if(velX==-5) setVelX(-8);
        	
        	invicibilityTime++;
        	if(invicibilityTime>=600) {
        		invincible = false;
                invicibilityTime = 0;
        	}
        } else {
        	if(velX==8) setVelX(5);
            else if(velX==-8) setVelX(-5);
        }
        
        if(restoring) {
        	restoreTime++;
        	
        	if(restoreTime>=90) {
        		restoring = false;
                restoreTime = 0;
        	}
        }
                
        for(int i=0;i<handler.tile.size();i++) {
            Tile ti = handler.tile.get(i);                      
            if(ti.isSolid()&&!goingDownPipe) {
            	if(getBounds().intersects(ti.getBounds()))  {
                    if(ti.getId()==Id.flag) Game.switchLevel();
                }
            	if(getBoundsTop().intersects(ti.getBounds())) {
                	setVelY(0);
                	if(jumping&&!goingDownPipe) {
                		jumping = false;
                		gravity = 0.8;
                		falling = true;
                	}
                	
                	if(ti.getId()==Id.powerUp) {
                        if(getBoundsTop().intersects(ti.getBounds())) 
                            ti.activated = true;                                                            
                        }                	
                }
                
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
                	setVelX(0);
                	x = ti.getX()+getWidth();
                }
                if(getBoundsRight().intersects(ti.getBounds())) {
                	setVelX(0);
                	x = ti.getX()-getWidth();
                }
            }
        }
        
        for(int i = 0;i<handler.entity.size();i++){
            Entity en = handler.entity.get(i);
            
            if(en.getId()==Id.growUp) {
            	switch(en.getType()) {
            	
            	case 0:
            		if(getBounds().intersects(en.getBounds()))  {
                		int tpX = getX();
                        int tpY = getY();
                        width+=(width/3);
                        height+=(height/3);
                        setX(tpX-width);
                        setY(tpY-height);
                        if(state==PlayerState.SMALL) state = PlayerState.BIG;
                        Game.power.play();
                        en.die();	
                	}
            		
            		break;
            		
            	case 1:
            		if(getBounds().intersects(en.getBounds())) {
                        Game.lives++;
                        en.die();
                        Game.power.play();
            		}
            	}
            	            	
            } else if(en.getId()==Id.monster||en.getId()==Id.bat||en.getId()==Id.plant) {
            	if(invincible&&getBounds().intersects(en.getBounds())) {
            		en.die();
            		Game.damage.play();
            	}
            	else {
            		if(getBoundsBottom().intersects(en.getBoundsTop())) {
                		if(en.getId()!=Id.bat) {
                			en.die();
                			Game.damage.play();
                		}
                		else if(en.attackable) {
                            en.hp--;
                            en.falling = true;
                            en.gravity = 3.0;
                            en.batState = BatState.RECOVERING;
                            en.attackable = false;
                            en.phaseTime = 0;
                            
                            jumping = true;
                            falling = false;
                            gravity = 3.5;
                        }
                	} else if(getBounds().intersects(en.getBounds())) {
                		takeDamage();
                	}           		
            	}
            	
            } else if(en.getId()==Id.coin)  {
            	if(getBounds().intersects(en.getBounds())) {
            		Game.coins++;
                    en.die();
            	}
            	
            } else if(en.getId()==Id.snail) {
            	if(invincible&&getBounds().intersects(en.getBounds())) {
            		en.die();
            		Game.damage.play();
            	}
            	else {
            		if(en.snailState==SnailState.WALKING) {
                		if(getBoundsBottom().intersects(en.getBoundsTop())) {
                			en.snailState = SnailState.SHELL;
                			Game.damage.play();
                            jumping = true;
                            falling = false;
                            gravity = 3.5;
                		} else if(getBounds().intersects(en.getBounds())) takeDamage();
                		
                	} else if(en.snailState==SnailState.SHELL) {
                		if(getBoundsBottom().intersects(en.getBoundsTop())) {
                			en.snailState = SnailState.SPINNING;
                			int dir = random.nextInt(2);
                            switch(dir) {
                                case 0:
                                    en.setVelX(-10);
                                    facing = 0;
                                    break;
                                case 1:
                                    en.setVelX(10);
                                    facing = 1;
                                    break;
                            }                    
                                    jumping = true;
                                    falling = false;
                                    gravity = 3.5;
                		}
                		if(getBoundsLeft().intersects(en.getBoundsRight())) {
                            en.setVelX(-10);
                            en.snailState = SnailState.SPINNING;
                            Game.damage.play();
                        }
                        
                        if(getBoundsRight().intersects(en.getBoundsLeft())) {
                            en.setVelX(10);
                            en.snailState = SnailState.SPINNING;
                            Game.power.play();
                        }
                		
                	} else if(en.snailState==SnailState.SPINNING) {
                		if(getBoundsBottom().intersects(en.getBoundsTop())) {
                			en.snailState = SnailState.SHELL;
                            jumping = true;
                            falling = false;
                            gravity = 3.5;
                		} else if(getBounds().intersects(en.getBounds())) takeDamage();
                	}
            	}
            	
            	} else if(getBounds().intersects(en.getBounds())) {
            		if(en.getId()==Id.star) {
            			invincible = true;
            			Game.power.play();
                        en.die();
                        
            		} else if(getBounds().intersects(en.getBounds())) {
            			if(en.getId()==Id.flower) {
                    		if(state==PlayerState.SMALL) {
                    			int tpX = getX();
                                int tpY = getY();
                                width+=(width/3);
                                height+=(height/3);
                                setX(tpX-width);
                                setY(tpY-height);                
                        }
                    		state = PlayerState.FIRE;
                        	en.die();	
                    	}
                    }
            	}
            }
        
        if(jumping&&!goingDownPipe) {
        	gravity-=0.17;
        	setVelY((int)-gravity);
        	if(gravity<=0.5) {
                jumping = false;  
                falling = true;
        	}
        }
        
        if(falling&&!goingDownPipe) {
            gravity+=0.17;
            setVelY((int)gravity);        
        }
        
        if(velX!=0) {
            frameDelay++;
            if(frameDelay>=6) {
                frame++;
                if(frame>=8) {
                	frame = 0;
                }
                frameDelay = 0;
                
            }
        }
        
        if(goingDownPipe) {
        	for(int i = 0;i<Game.handler.tile.size();i++) {
                Tile ti = Game.handler.tile.get(i);
                if(ti.getId()==Id.pipe) {
                	if(getBounds().intersects(ti.getBounds())) {
                		switch(ti.facing) {
                		case 0:                                                
                            setVelY(-5);
                            setVelX(0);
                            pixelsTravelled+=-velY;
                            break;
                        case 2:                                                 
                            setVelY(5);
                            setVelX(0);
                            pixelsTravelled+=velY;                            
                            break;
                		}
                		
                		if(pixelsTravelled>ti.getHeight()) { 
                			goingDownPipe = false;
                            pixelsTravelled = 0;
                            
                		} else goingDownPipe = true;              		
                	}
                }
        	}
        }        
	}
	
	public void takeDamage() {
		
		if(restoring) return;
		
		if(state==PlayerState.SMALL) {
			die();
			return;
		} else if(state==PlayerState.BIG) {
			width-=(width/4);
			height-=(height/4);
			x+=width/4;
			y+=height/4;
			
			state = PlayerState.SMALL;
            Game.damage.play();
            
            restoring = true;
            restoreTime = 0;                    
            return;
			
		} else if(state==PlayerState.FIRE) {
			state = PlayerState.BIG;
            Game.damage.play();   
            
            restoring = true;
            restoreTime = 0;
            return;
		}
		
	}
	
}
