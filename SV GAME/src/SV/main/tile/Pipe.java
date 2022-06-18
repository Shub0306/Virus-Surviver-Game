package SV.main.tile;

import java.awt.Graphics;

import SV.main.Game;
import SV.main.Handler;
import SV.main.Id;
import SV.main.entity.mob.Plant;

public class Pipe extends Tile{

	public Pipe(int x, int y, int width, int height, boolean solid, Id id, Handler handler, int facing, boolean plant) {
		super(x, y, width, height, solid, id, handler);
		this.facing = facing;
		
		if(plant) handler.addEntity(new Plant(getX(), getY()-64, getWidth(), 64, Id.plant, handler));
		
	}

	public void render(Graphics g) {
		
		g.drawImage(Game.pipe.getBufferedImage(), x, y-64, getWidth(), getHeight()+64, null);
		
	}

	public void update() {
		
	}

}
