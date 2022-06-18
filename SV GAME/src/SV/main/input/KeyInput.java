package SV.main.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import SV.main.Game;
import SV.main.Id;
import SV.main.entity.Entity;
import SV.main.entity.Fireball;
import SV.main.states.PlayerState;
import SV.main.tile.Tile;

public class KeyInput implements KeyListener {
	
	private boolean fire;

	public void keyPressed(KeyEvent e) {
		
        int key = e.getKeyCode();
        for(int i = 0;i<Game.handler.entity.size();i++) {
            Entity en = Game.handler.entity.get(i);
            if(en.getId()==Id.player) {
            	
            	if(en.goingDownPipe) return;
            	
            	switch(key) {
                
                case KeyEvent.VK_W:
                	
                	for(int j=0;j<Game.handler.tile.size();j++) {
                        Tile ti = Game.handler.tile.get(j);
                        if(ti.isSolid()) {
                        	if(en.getBoundsBottom().intersects(ti.getBounds())) {
								if(!en.jumping) {
									en.jumping = true;
									en.gravity = 11.0;
									
									Game.jump.play();
									
								}
                        }
                        if(ti.getId()==Id.pipe) {
                            if(en.getBoundsTop().intersects(ti.getBounds())) {
                                if(!en.goingDownPipe) en.goingDownPipe = true;
                            }
                        }
                    }
                }
                	break;
                	
                case KeyEvent.VK_S:                                            
                    for(int j=0;j<Game.handler.tile.size();j++) {
                        Tile ti = Game.handler.tile.get(j);
                        if(ti.getId()==Id.pipe) {
                            if(en.getBoundsBottom().intersects(ti.getBounds())) {
                                if(!en.goingDownPipe) en.goingDownPipe = true;
                            }
                        }
                    }
                    break;
                    
                case KeyEvent.VK_A:                                                 
                	en.setVelX(-5);
                	en.facing = 0;
                	break;
                	
                case KeyEvent.VK_D:                                                 
                	en.setVelX(5);
                	en.facing = 1;
                	break;
                	
                case KeyEvent.VK_SPACE: 
                	if(en.state==PlayerState.FIRE&&!fire) {
                		switch(en.facing) {
                		case 0:
                			Game.handler.addEntity(new Fireball(en.getX()-24, en.getY()+12, 24, 24, Id.fireball, 
                                    Game.handler, en.facing));
                            fire = true;
                            break;
                		case 1:
                			Game.handler.addEntity(new Fireball(en.getX()+en.getWidth(), en.getY()+12, 24, 24, 
                                    Id.fireball, Game.handler, en.facing));
                            fire = true;
                            break;
                		}
                	}
                	break;
                }
            }
        
        }
        
	}

	public void keyReleased(KeyEvent e) {
		
        int key = e.getKeyCode();
        for(int i = 0;i<Game.handler.entity.size();i++) {
            Entity en = Game.handler.entity.get(i);
            if(en.getId()==Id.player) {
            	
            	switch(key) {
                case KeyEvent.VK_W:
                    en.setVelY(0);
                    break;
                    
                case KeyEvent.VK_A:
                    en.setVelX(0);
                    break;
                    
                case KeyEvent.VK_D:
                    en.setVelX(0);
                    break;
                    
                case KeyEvent.VK_SPACE:
                	fire = false;
                    break;                    
            }
            	
            }
		
        }
        
	}
	
	public void keyTyped(KeyEvent e) {
		//not using
	}

}
