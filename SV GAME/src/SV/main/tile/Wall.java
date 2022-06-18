package SV.main.tile;

import java.awt.Graphics;

import SV.main.Game;
import SV.main.Handler;
import SV.main.Id;

public class Wall extends Tile {

	public Wall(int x, int y, int width, int height, boolean solid, Id id, Handler handler) {
		super(x, y, width, height, solid, id, handler);
	}
	
    public void render(Graphics g) { 
    	
    	g.drawImage(Game.grass.getBufferedImage(), x, y, 
                getWidth(), getHeight(), null);
    	
    }

	public void update() {
    }

}
