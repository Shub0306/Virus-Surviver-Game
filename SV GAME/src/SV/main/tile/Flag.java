package SV.main.tile;

import java.awt.Graphics;

import SV.main.Game;
import SV.main.Handler;
import SV.main.Id;

public class Flag extends Tile{

	public Flag(int x, int y, int width, int height, boolean solid, Id id, Handler handler) {
		super(x, y, width, height, solid, id, handler);
	}

	public void render(Graphics g) {
		
		g.drawImage(Game.flag[1].getBufferedImage(), getX(), getY(), getWidth(), 64, null);
        
        g.drawImage(Game.flag[2].getBufferedImage(), getX(), getY()+64, getWidth(), 64, null);
        g.drawImage(Game.flag[2].getBufferedImage(), getX(), getY()+128, getWidth(), 64, null); 
        g.drawImage(Game.flag[2].getBufferedImage(), getX(), getY()+192, getWidth(), 64, null);
        g.drawImage(Game.flag[2].getBufferedImage(), getX(), getY()+256, getWidth(), 64, null);          
        
        g.drawImage(Game.flag[0].getBufferedImage(), getX(), getY()+320, getWidth(), 64, null);
		
	}

	public void update() {
		
	}

}
