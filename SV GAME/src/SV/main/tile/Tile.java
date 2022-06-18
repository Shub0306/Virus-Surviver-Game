package SV.main.tile;

import java.awt.Graphics;
import java.awt.Rectangle;

import SV.main.Handler;
import SV.main.Id;

public abstract class Tile {
	
    public int x, y;
    public int width, height;
    public int type;
    
    public boolean solid;
    public boolean activated = false;
    
    public int velX, velY;
    public int facing;
    
    public Id id;
    
    public Handler handler;
    
    public Tile(int x, int y, int width, int height, boolean solid, Id id, Handler handler) {   
    	
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.solid = solid;
        this.id = id;
        this.handler = handler;
    	
    }
    
    public abstract void render(Graphics g);
    
    public abstract void update();
    
    public void die() {
    	
        handler.removeTile(this);        
    	
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

	public boolean isSolid() {
		return solid;
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


}
