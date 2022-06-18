package SV.main.entity;

import java.awt.Graphics;
import java.awt.Rectangle;

import SV.main.Game;
import SV.main.Handler;
import SV.main.Id;
import SV.main.states.BatState;
import SV.main.states.PlayerState;
import SV.main.states.SnailState;

public abstract class Entity {
	
    public int x, y;
    public int width, height;
    public int facing = 0;
    public int hp;
    public int phaseTime;
    public int type;
    
    public boolean jumping = false;
    public boolean falling = true;
    public boolean goingDownPipe = false;
    public boolean attackable = false;
    
    public int velX, velY;
    public int frame = 0;
	public int frameDelay = 0;
    
    public Id id;
    public BatState batState;
    public SnailState snailState;
    
    public PlayerState state;
    
    public double gravity = 0.0;
    
    public Handler handler;
    
    public Entity(int x, int y, int width, int height, Id id, Handler handler) {   
    	
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.id = id;
        this.handler = handler;
    	
    }
    
    public abstract void render(Graphics g);
    
    public abstract void update(); 																			//extend entity and player must have this class to construct entity

    
    public void die() { 

        handler.removeEntity(this);
        if(getId()==Id.player) {
        Game.lives--;
        Game.showDeathScreen = true;
        Game.death.play();
        
        if(Game.lives<=0) Game.gameOver = true;
        
        }
    	
    }

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public Id getId() {
		return id;
	}

	public void setVelX(int velX) {
		this.velX = velX;
	}

	public void setVelY(int velY) {
		this.velY = velY;
	}
	
	public int getType() {
        return type;
    }
	
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }
	
    public Rectangle getBounds() {                                              
        return new Rectangle (getX(), getY(), getWidth(), getHeight());
    }
  
    public Rectangle getBoundsTop() {
        return new Rectangle (getX()+10, getY(), getWidth()-20, 5);
    }    
    
    public Rectangle getBoundsBottom() {
        return new Rectangle (getX()+10, getY()+getHeight()-5, getWidth()-20, 5);
    }        
    
    public Rectangle getBoundsLeft() {
        return new Rectangle (getX(), getY()+10, 5, getHeight()-20);
    } 
    
    public Rectangle getBoundsRight() {
        return new Rectangle (getX()+getWidth()-5, getY()+10, 5, getHeight()-20);
    } 


}
