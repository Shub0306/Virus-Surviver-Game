package SV.main.tile;

import java.awt.Graphics;

import SV.main.Game;
import SV.main.Handler;
import SV.main.Id;

public class Info extends Tile{

	public Info(int x, int y, int width, int height, boolean solid, Id id, Handler handler, int type) {
		super(x, y, width, height, solid, id, handler);
		this.type = type;
	}

	public void render(Graphics g) {
		
		switch(getType()) {
		
		case 0:
			g.drawImage(Game.start.getBufferedImage(), x, y, getWidth(), getHeight(), null);
			break;
			
		case 1:
			g.drawImage(Game.goal.getBufferedImage(), x, y, getWidth(), getHeight(), null);
			break;
		}		
	}

	public void update() {
		
	}

}
